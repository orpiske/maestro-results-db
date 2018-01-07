package net.orpiske.maestro.results.dao;

import net.orpiske.maestro.results.dto.TestFailCondition;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import java.util.List;

public class TestFailConditionDao extends AbstractDao {
    public void insert(TestFailCondition dto) {
        runEmptyInsert(
                "insert into test_fail_condition " +
                        "(test_id, test_fail_condition_resource_name, test_fail_condition_name, test_fail_condition_value) " +
                        "values(:testId, :testFailConditionResourceName, :testFailConditionName, :testFailConditionValue)", dto);
    }

    public List<TestFailCondition> fetchById(int testFailConditionId) {
        return jdbcTemplate.query("select * from test_fail_condition where test_fail_conditions_id = ?",
                new Object[] { testFailConditionId },
                new BeanPropertyRowMapper<>(TestFailCondition.class));
    }

    public List<TestFailCondition> fetch() {
        return jdbcTemplate.query("select * from test_fail_condition",
                new BeanPropertyRowMapper<>(TestFailCondition.class));
    }
}
