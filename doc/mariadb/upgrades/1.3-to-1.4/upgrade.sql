-- Add test duration information to test
alter table test add column test_duration_type varchar(8) default 'time';

-- Add to test history
alter table test_history add column test_duration_type varchar(8) default 'time'; -- BEFORE test_update_date ;

-- Ensures that test_update_date remains the last column in the history table, so that the trigger continues to work
alter table test_history modify `test_update_date` datetime not null after test_duration_type;

update test set test_duration_type = 'count' where test_duration not in (300, 600, 900, 12600);


-- Move connection count information to test
alter table test add column connection_count int;

-- Add to test history
alter table test_history add column connection_count int; 

-- Move to the correct position
alter table test_history modify `test_update_date` datetime not null after connection_count;

-- Copy the results
update test t inner join env_results er on t.test_id = er.test_id set t.connection_count = er.connection_count;

-- Remove the connection count from the env results
alter table env_results drop column connection_count;

-- Ensure that the latency information is correctly set for tests
alter table env_results modify column lat_percentile_90 DOUBLE default 0;
alter table env_results modify column lat_percentile_95 DOUBLE default 0;
alter table env_results modify column lat_percentile_99 DOUBLE default 0;

update env_results set lat_percentile_90 = 0 where lat_percentile_90 is null and env_resource_role = 'receiver';
update env_results set lat_percentile_95 = 0 where lat_percentile_95 is null and env_resource_role = 'receiver';
update env_results set lat_percentile_99 = 0 where lat_percentile_99 is null and env_resource_role = 'receiver';

update env_results set lat_percentile_90 = 0,lat_percentile_95 = 0,lat_percentile_99 = 0 where env_resource_role = 'sender';

-- Recreate the unified test properties view
CREATE OR REPLACE VIEW `test_properties` AS
select
    tmp.test_id,
    tmp.test_number,
    MAX(IF(tmp.test_msg_property_name = "apiName", tmp.test_msg_property_value, NULL)) AS api_name,
    MAX(IF(tmp.test_msg_property_name = "apiVersion", tmp.test_msg_property_value, NULL)) AS api_version,
    MAX(IF(tmp.test_msg_property_name = "durable", IF(tmp.test_msg_property_value = "true", true, false), NULL)) AS durable,
    MAX(IF(tmp.test_msg_property_name = "limitDestinations", CAST(tmp.test_msg_property_value AS INTEGER), NULL)) AS limit_destinations,
    MAX(IF(tmp.test_msg_property_name = "messageSize", CAST(tmp.test_msg_property_value AS INTEGER), NULL)) AS message_size,
    MAX(IF(tmp.test_msg_property_name = "protocol", tmp.test_msg_property_value, NULL)) AS messaging_protocol,
    MAX(IF(tmp.test_msg_property_name = "variableSize", IF(tmp.test_msg_property_value = "1", true, false), NULL)) AS variable_size,
    MAX(IF(tfc.test_fail_condition_name = "fcl", CAST(tfc.test_fail_condition_value AS INTEGER), NULL)) AS max_acceptable_latency
  from test_msg_property tmp, test_fail_condition tfc
  where tmp.test_id = tfc.test_id and tmp.test_number = tfc.test_number
  group by test_id,test_number;

-- Recreate views modified for this release
CREATE OR REPLACE VIEW `test_results` AS
	SELECT
        t.test_id,
        t.test_number,
        sut.sut_id,
        sut.sut_tags,
        sut.sut_name,
        sut.sut_version,
        t.test_result,
        er.error,
        t.test_valid,
        res.env_resource_name,
        er.env_resource_role,
        er.test_rate_min,
        er.test_rate_max,
        er.test_rate_geometric_mean,
        er.test_rate_standard_deviation,
        er.test_rate_skip_count,
        er.lat_percentile_90,
        er.lat_percentile_95,
        er.lat_percentile_99,
        t.test_date,
        t.test_name,
        t.test_tags,
        t.test_report_link,
        t.connection_count,
        t.test_target_rate,
        (t.test_target_rate * t.connection_count) as test_combined_target_rate,
        tp.api_name,
        tp.api_version,
        tp.durable,
        tp.limit_destinations,
        tp.message_size,
        tp.messaging_protocol,
        tp.max_acceptable_latency
    FROM
        test t,
        env_results er,
        env_resource res,
        sut,
        test_properties tp
    WHERE
        t.test_id = er.test_id
            AND t.test_number = er.test_number
            AND er.env_resource_id = res.env_resource_id
            AND t.sut_id = sut.sut_id
            AND t.test_id = tp.test_id
            AND t.test_number = tp.test_number;
            
CREATE OR REPLACE VIEW `test_sut_properties_link` AS
	SELECT
        t.test_id,
        t.test_number,
        sut.sut_id,
        sut.sut_tags,
        sut.sut_name,
        sut.sut_version,
        t.test_result,        
        t.connection_count,
        t.test_name,
        t.test_tags,
        t.test_target_rate,
        tp.api_name,
        tp.api_version,
        tp.durable,
        tp.limit_destinations,
        tp.message_size,
        tp.messaging_protocol,
        tp.max_acceptable_latency,
        t.test_date
    FROM
        test t,
        sut,
        test_properties tp
    WHERE t.sut_id = sut.sut_id
            AND t.test_id = tp.test_id
            AND t.test_number = tp.test_number;

-- Remove the deprecated view
drop view test_parameters;


-- Create the new statistics view 
create or replace view `test_result_statistics` as
	select test_id,
        sum(case when test_result = "success" then 1 else 0 end) as success,
        sum(case when test_result = "success" then 0 else 1 end) as failures
    from test_results
    where test_valid = true
    group by test_id;

-- Ensure that updated data is committed
commit;