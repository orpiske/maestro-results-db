package org.maestro.results.dao;

import org.maestro.results.dto.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.BeanPropertyRowMapper;


import org.maestro.reports.dao.exceptions.DataNotFoundException;
import org.maestro.reports.dao.AbstractDao;

import java.util.List;

public class TestDao extends AbstractDao {
    private static final Logger logger = LoggerFactory.getLogger(TestDao.class);

    public int insert(final Test dto) {
        return runInsert(
                "insert into test(test_id, test_name, test_number, test_result, sut_id, " +
                        "test_report_link, test_data_storage_info, test_tags, test_date, test_duration, test_target_rate, " +
                        "maestro_version) " +
                        "values(:testId, :testName, :testNumber, :testResult, :sutId, :testReportLink," +
                        ":testDataStorageInfo, :testTags, now(), :testDuration, :testTargetRate, :maestroVersion)", dto);
    }

    public void update(final Test dto) {
        runUpdate(
                "update test set test_name = ?, test_number = ?, test_result = ?, sut_id = ?, " +
                        "test_report_link = ?, test_data_storage_info = ?, test_tags = ?, " +
                        "test_date = ?, test_duration = ?, test_target_rate = ? " +
                        "where test_id = ? and test_number = ?", dto.getTestName(), dto.getTestNumber(),
                dto.getTestResult(), dto.getSutId(), dto.getTestReportLink(), dto.getTestDataStorageInfo(),
                dto.getTestTags(), dto.getTestDate(), dto.getTestDuration(), dto.getTestTargetRate(), dto.getTestId(),
                dto.getTestNumber());
    }


    public List<Test> fetch() throws DataNotFoundException {
        return runQueryMany("select * from test", new BeanPropertyRowMapper<>(Test.class));
    }

    public List<Test> fetch(final String testName) throws DataNotFoundException {
        return runQueryMany("select * from test where test_name = ?", new BeanPropertyRowMapper<>(Test.class),
                testName);
    }

    public List<Test> fetch(int initialId, int finalId, final String testName) throws DataNotFoundException {
        return runQueryMany("select * from test where test_name = ? and test_id > ? and test_id < ?",
                new BeanPropertyRowMapper<>(Test.class),
                testName, initialId, finalId);
    }

    public List<Test> fetch(int testId) throws DataNotFoundException {
        return runQueryMany("select * from test where test_id = ?",
                new BeanPropertyRowMapper<>(Test.class),
                testId);
    }

    public Test fetch(int testId, int testNumber) throws DataNotFoundException {
        return runQuery("select * from test where test_id = ? and test_number = ?",
                new BeanPropertyRowMapper<>(Test.class),
                testId, testNumber);
    }


    public int updateDurationAndRate(int id, int number, final Integer duration, final String durationType,
                                     final Integer rate, final Integer connectionCount) {
        return runUpdate("update test set test_duration = ?, test_duration_type = ?, test_target_rate = ?, " +
                        "connection_count = ? where test_id = ? and test_number = ?",
                duration, durationType, rate, connectionCount, id, number);
    }
 }
