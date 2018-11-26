package org.maestro.results.dao;

import org.maestro.results.dto.TestFailCondition;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import java.util.List;

import org.maestro.reports.dao.exceptions.DataNotFoundException;
import org.maestro.reports.dao.AbstractDao;

public class TestFailConditionDao extends AbstractDao {
    public void insert(TestFailCondition dto) {
        runEmptyInsert(
                "insert into test_fail_condition " +
                        "(test_id, test_number, test_fail_condition_resource_name, test_fail_condition_name, test_fail_condition_value) " +
                        "values(:testId, :testNumber, :testFailConditionResourceName, :testFailConditionName, :testFailConditionValue)", dto);
    }


    public List<TestFailCondition> fetch() throws DataNotFoundException {
        return runQueryMany("select * from test_fail_condition",
                new BeanPropertyRowMapper<>(TestFailCondition.class));
    }
}
