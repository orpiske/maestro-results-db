package org.maestro.results.dao;

import org.maestro.results.dto.TestProperties;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import java.util.List;

public class TestPropertiesDao extends AbstractDao {
    public List<TestProperties> fetch(int testId) {
        return jdbcTemplate.query("select * from test_properties where test_id = ?",
                new Object[] { testId },
                new BeanPropertyRowMapper<>(TestProperties.class));
    }

    public TestProperties fetch(int testId, int testNumber) {
        return jdbcTemplate.queryForObject("select * from test_properties where test_id = ? and test_number = ?",
                new Object[] { testId, testNumber },
                new BeanPropertyRowMapper<>(TestProperties.class));
    }
}
