package net.orpiske.maestro.results.dao;

import net.orpiske.maestro.results.dto.Test;
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
                        "test_report_link, test_data_storage_info, test_tags, test_date, test_duration, test_target_rate) " +
                        "values(:testName, :testNumber, :testResult, :sutId, :testReportLink," +
                        ":testDataStorageInfo, :testTags, now(), :testDuration, :testTargetRate)", dto);
    }

    public int insertNewExecution(Test dto) {
        return runInsert(
                "insert into test(test_id, test_name, test_number, test_result, sut_id, " +
                        "test_report_link, test_data_storage_info, test_tags, test_date, test_duration, test_target_rate) " +
                        "values(:testId, :testName, :testNumber, :testResult, :sutId, :testReportLink," +
                        ":testDataStorageInfo, :testTags, now(), :testDuration, :testTargetRate)", dto);
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


    public int updateDurationAndRate(int id, int number, final Integer duration, final Integer rate) {
        return jdbcTemplate.update("update test set test_duration = ?, test_target_rate = ? where " +
                "test_id = ? and test_number = ?",
                new Object[] { duration, rate, id, number});
    }
 }
