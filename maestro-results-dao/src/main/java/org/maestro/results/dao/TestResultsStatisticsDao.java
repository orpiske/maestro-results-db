package org.maestro.results.dao;

import org.maestro.results.dto.TestResult;
import org.maestro.results.dto.TestResultStatistics;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import java.util.List;

public class TestResultsStatisticsDao extends AbstractDao {
    public List<TestResultStatistics> fetch() {
        return jdbcTemplate.query("select * from test_results_statistics",
                new BeanPropertyRowMapper<>(TestResultStatistics.class));
    }

    public TestResultStatistics successFailureCount(int testId) {
        return jdbcTemplate.queryForObject("select * from test_results_statistics where test_id = ?",
            new Object[] { testId },
            new BeanPropertyRowMapper<>(TestResultStatistics.class));
    }
}
