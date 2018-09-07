package org.maestro.results.dao;

import org.maestro.results.dto.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import java.util.List;

public class TestDao extends AbstractDao {
    private static final Logger logger = LoggerFactory.getLogger(TestDao.class);

    public int insert(Test dto) {
        return runInsert(
                "insert into test(test_name, test_number, test_result, sut_id, " +
                        "test_report_link, test_data_storage_info, test_tags, test_date, test_duration, test_target_rate, " +
                        "maestro_version) " +
                        "values(:testName, :testNumber, :testResult, :sutId, :testReportLink," +
                        ":testDataStorageInfo, :testTags, now(), :testDuration, :testTargetRate, :maestroVersion)", dto);
    }

    public void update(Test dto) {
        runUpdate(
                "update test set test_name=:testName, test_number=:testNumber, test_result=:testResult, sut_id=:sutId, " +
                        "test_report_link=:testReportLink, test_data_storage_info=:testDataStorageInfo, test_tags=:testTags, " +
                        "test_date=:testDate, test_duration=:testDuration, test_target_rate=:testTargetRate " +
                        "where test_id=:testId and test_number=:testNumber", dto);
    }

    public int insertNewExecution(Test dto) {
        return runInsert(
                "insert into test(test_id, test_name, test_number, test_result, sut_id, " +
                        "test_report_link, test_data_storage_info, test_tags, test_date, test_duration, test_target_rate, " +
                        "maestro_version) " +
                        "values(:testId, :testName, :testNumber, :testResult, :sutId, :testReportLink," +
                        ":testDataStorageInfo, :testTags, now(), :testDuration, :testTargetRate, :maestroVersion)", dto);
    }


    public int countRecords(int id) {
        try {
            return jdbcTemplate.queryForObject("select count(test_id) from test where test_id = ?",
                    new Object[]{id},
                    Integer.class);
        }
        catch (EmptyResultDataAccessException e) {
            logger.debug("No records with ID {} were found", id);

            return 0;
        }
    }

    public int countRecordsWithExecution(int id, int testNumber) {
        try {
            return jdbcTemplate.queryForObject("select count(test_id) from test where test_id = ? and test_number = ?",
                    new Object[]{id, testNumber},
                    Integer.class);
        }
        catch (EmptyResultDataAccessException e) {
            logger.debug("No records with ID {} were found", id);

            return 0;
        }
    }

    public List<Test> fetch() {
        return jdbcTemplate.query("select * from test",
                new BeanPropertyRowMapper<>(Test.class));
    }

    public List<Test> fetch(int testId) {
        return jdbcTemplate.query("select * from test where test_id = ?",
                new Object[]{testId},
                new BeanPropertyRowMapper<>(Test.class));
    }

    public Test fetch(int testId, int testNumber) {
        return jdbcTemplate.queryForObject("select * from test where test_id = ? and test_number = ?",
                new Object[]{testId, testNumber},
                new BeanPropertyRowMapper<>(Test.class));
    }


    public int updateDurationAndRate(int id, int number, final Integer duration, final String durationType, final Integer rate) {
        return jdbcTemplate.update("update test set test_duration = ?, test_duration_type = ?, test_target_rate = ? where " +
                "test_id = ? and test_number = ?",
                new Object[] { duration, durationType, rate, id, number});
    }
 }
