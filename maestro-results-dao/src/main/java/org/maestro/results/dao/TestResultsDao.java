package org.maestro.results.dao;

import org.maestro.results.dto.TestResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import org.maestro.reports.dao.exceptions.DataNotFoundException;
import org.maestro.reports.dao.AbstractDao;

import java.util.List;

public class TestResultsDao extends AbstractDao {
    private static final Logger logger = LoggerFactory.getLogger(TestResultsDao.class);

    public List<TestResult> fetch() throws DataNotFoundException {
        return runQueryMany("select * from test_results where test_valid = true",
                new BeanPropertyRowMapper<>(TestResult.class));
    }

    public List<TestResult> fetch(int testId) throws DataNotFoundException {
        return runQueryMany("select *,lat_percentile_90 as lat_percentile90," +
                        "lat_percentile_95 as lat_percentile95,lat_percentile_99 as lat_percentile99 " +
                        "from test_results where test_id = ?",
                new BeanPropertyRowMapper<>(TestResult.class),
                testId);
    }

    public List<TestResult> fetch(int testId, int testNumber) throws DataNotFoundException {
        return runQueryMany("select *,lat_percentile_90 as lat_percentile90," +
                        "lat_percentile_95 as lat_percentile95,lat_percentile_99 as lat_percentile99 " +
                        "from test_results where test_id = ? and test_number = ?",
                new BeanPropertyRowMapper<>(TestResult.class),
                testId, testNumber);
    }

    public List<TestResult> fetch(int testId, final String role) throws DataNotFoundException {
        return runQueryMany("select *,lat_percentile_90 as lat_percentile90," +
                "lat_percentile_95 as lat_percentile95,lat_percentile_99 as lat_percentile99 " +
                "from test_results where test_id = ? and env_resource_role = ?",
                new BeanPropertyRowMapper<>(TestResult.class),
                testId, role);
    }

    public List<TestResult> fetchOrdered(int testId, final String role) throws DataNotFoundException {
        return runQueryMany("select *,lat_percentile_90 as lat_percentile90," +
                        "lat_percentile_95 as lat_percentile95,lat_percentile_99 as lat_percentile99 " +
                        "from test_results where test_id = ? and env_resource_role = ? " +
                        "order by test_id,test_number",
                new BeanPropertyRowMapper<>(TestResult.class),
                testId, role);
    }

    public List<TestResult> fetchForCompare(int t0, int n0, int t1, int n1) throws DataNotFoundException {
        return runQueryMany("select *,lat_percentile_90 as lat_percentile90," +
                        "lat_percentile_95 as lat_percentile95,lat_percentile_99 as lat_percentile99 " +
                        "from test_results where (test_id = ? and test_number = ?) or (test_id = ? and test_number = ?)" +
                        "order by test_id,test_number",
                new BeanPropertyRowMapper<>(TestResult.class),
                t0, n0, t1, n1);
    }

    public List<TestResult> fetchForCompare(int t0, int n0, int t1, int n1, final String role) throws DataNotFoundException {
        return runQueryMany("select *,lat_percentile_90 as lat_percentile90," +
                        "lat_percentile_95 as lat_percentile95,lat_percentile_99 as lat_percentile99 " +
                        "from test_results where ((test_id = ? and test_number = ?) or (test_id = ? and test_number = ?)) " +
                        "and env_resource_role = ? order by test_id,test_number",
                new BeanPropertyRowMapper<>(TestResult.class),
                t0, n0, t1, n1, role);
    }

 }
