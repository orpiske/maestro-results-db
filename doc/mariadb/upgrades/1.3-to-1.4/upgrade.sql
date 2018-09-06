-- Add to test
alter table test add column test_duration_type varchar(8) default 'time';

-- Add to test history
alter table test_history add column test_duration_type varchar(8) default 'time'; -- BEFORE test_update_date ;

-- Ensures that test_update_date remains the last column in the history table, so that the trigger continues to work
alter table test_history modify `test_update_date` datetime not null after test_duration_type;


update test set test_duration_type = 'count' where test_duration not in (300, 600, 900, 12600);

create or replace view `test_result_statistics` as
	select test_id,
        sum(case when test_result = "success" then 1 else 0 end) as success,
        sum(case when test_result = "success" then 0 else 1 end) as failures
    from test_results
    where test_valid = true
    group by test_id;

commit;





