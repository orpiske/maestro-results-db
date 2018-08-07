package org.maestro.results.dao;

import org.maestro.results.dto.TestParameters;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import java.util.List;

public class TestParametersDao extends AbstractDao {
    public List<TestParameters> fetch(int testId) {
        return jdbcTemplate.query("select * from test_parameters where test_id = ?",
                new Object[] { testId },
                new BeanPropertyRowMapper<>(TestParameters.class));
    }

    public TestParameters fetch(int testId, int testNumber) {
        return jdbcTemplate.queryForObject("select * from test_parameters where test_id = ? and test_number = ?",
                new Object[] { testId, testNumber },
                new BeanPropertyRowMapper<>(TestParameters.class));
    }
}
