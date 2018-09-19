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
        return jdbcTemplate.query("select *,lat_percentile_90 as lat_percentile90,lat_percentile_95 as lat_percentile95,lat_percentile_99 as lat_percentile99 from test_results where test_id = ?",
                new Object[]{testId},
                new BeanPropertyRowMapper<>(TestResult.class));
    }

    public List<TestResult> fetch(int testId, int testNumber) {
        return jdbcTemplate.query("select *,lat_percentile_90 as lat_percentile90," +
                        "lat_percentile_95 as lat_percentile95,lat_percentile_99 as lat_percentile99 " +
                        "from test_results where test_id = ? and test_number = ?",
                new Object[]{testId, testNumber },
                new BeanPropertyRowMapper<>(TestResult.class));
    }

    public List<TestResult> fetch(int testId, final String role) {
        return jdbcTemplate.query("select *,lat_percentile_90 as lat_percentile90," +
                "lat_percentile_95 as lat_percentile95,lat_percentile_99 as lat_percentile99 " +
                "from test_results where test_id = ? and env_resource_role = ?",
                new Object[]{testId, role},
                new BeanPropertyRowMapper<>(TestResult.class));
    }

    public List<TestResult> fetchOrdered(int testId, final String role) {
        return jdbcTemplate.query("select *,lat_percentile_90 as lat_percentile90," +
                        "lat_percentile_95 as lat_percentile95,lat_percentile_99 as lat_percentile99 " +
                        "from test_results where test_id = ? and env_resource_role = ? " +
                        "order by test_id,test_number",
                new Object[]{testId, role},
                new BeanPropertyRowMapper<>(TestResult.class));
    }

    public List<TestResult> fetchForCompare(int t0, int n0, int t1, int n1) {
        return jdbcTemplate.query("select *,lat_percentile_90 as lat_percentile90," +
                        "lat_percentile_95 as lat_percentile95,lat_percentile_99 as lat_percentile99 " +
                        "from test_results where (test_id = ? and test_number = ?) or (test_id = ? and test_number = ?)" +
                        "order by test_id,test_number",
                new Object[]{t0, n0, t1, n1},

                new BeanPropertyRowMapper<>(TestResult.class));
    }

    public List<TestResult> fetchForCompare(int t0, int n0, int t1, int n1, final String role) {
        return jdbcTemplate.query("select *,lat_percentile_90 as lat_percentile90," +
                        "lat_percentile_95 as lat_percentile95,lat_percentile_99 as lat_percentile99 " +
                        "from test_results where ((test_id = ? and test_number = ?) or (test_id = ? and test_number = ?)) " +
                        "and env_resource_role = ? order by test_id,test_number",
                new Object[]{t0, n0, t1, n1, role},

                new BeanPropertyRowMapper<>(TestResult.class));
    }

 }
