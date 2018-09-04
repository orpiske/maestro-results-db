package org.maestro.results.dao;

import org.maestro.results.dto.TestResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import java.util.List;

public class TestResultsDao extends AbstractDao {
    private static final Logger logger = LoggerFactory.getLogger(TestResultsDao.class);

    public List<TestResult> fetch() {
        return jdbcTemplate.query("select * from test_results",
                new BeanPropertyRowMapper<>(TestResult.class));
    }

    public List<TestResult> fetch(int testId) {
        return jdbcTemplate.query("select * from test_results where test_id = ?",
                new Object[]{testId},
                new BeanPropertyRowMapper<>(TestResult.class));
    }
 }
