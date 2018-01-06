package net.orpiske.maestro.results.dao;

import net.orpiske.maestro.results.dto.TestFailCondition;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import java.util.List;

public class TestFailConditionDao extends AbstractDao {
    public int insert(TestFailCondition dto) {
        return runInsert(
                "insert into test_fail_condition " +
                        "(test_parameter_id, test_fail_condition_name, test_fail_condition_value) " +
                        "values(:testParameterId, :testFailConditionName, :testFailConditionValue)", dto);
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
