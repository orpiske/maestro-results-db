CREATE VIEW `test_result_statistics` AS
	select test_id,
        sum(case when test_result = "success" then 1 else 0 end) as success,
        sum(case when test_result = "success" then 0 else 1 end) as failures
    from test_results
    where test_valid = true
    group by test_id;