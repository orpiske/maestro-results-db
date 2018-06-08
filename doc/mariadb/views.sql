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
        er.connection_count,
        t.test_date,
        t.test_name,
        t.test_tags,
        t.test_report_link
    FROM
        test t,
        env_results er,
        env_resource res,
        sut
    WHERE
        t.test_id = er.test_id
            AND er.env_resource_id = res.env_resource_id
            AND t.sut_id = sut.sut_id;

CREATE OR REPLACE VIEW `test_parameters` AS
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


CREATE OR REPLACE VIEW `test_properties` AS
select
    test_id,
    test_number,
    MAX(IF(test_msg_property_name = "apiName", test_msg_property_value, NULL)) AS api_name,
    MAX(IF(test_msg_property_name = "apiVersion", test_msg_property_value, NULL)) AS api_version,
    MAX(IF(test_msg_property_name = "durable", IF(test_msg_property_value = "true", true, false), NULL)) AS durable,
    MAX(IF(test_msg_property_name = "limitDestinations", CAST(test_msg_property_value AS INTEGER), NULL)) AS limit_destinations,
    MAX(IF(test_msg_property_name = "messageSize", CAST(test_msg_property_value AS INTEGER), NULL)) AS message_size,
    MAX(IF(test_msg_property_name = "protocol", test_msg_property_value, NULL)) AS messaging_protocol,
    MAX(IF(test_msg_property_name = "variableSize", IF(test_msg_property_value = "1", true, false), NULL)) AS variable_size
  from test_msg_property
  group by test_id,test_number;