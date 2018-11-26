package org.maestro.results.dao;

import org.maestro.results.dto.TestProperties;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import java.util.List;

import org.maestro.reports.dao.exceptions.DataNotFoundException;
import org.maestro.reports.dao.AbstractDao;

public class TestPropertiesDao extends AbstractDao {
    public List<TestProperties> fetch(int testId) throws DataNotFoundException {
        return runQueryMany("select * from test_properties where test_id = ?",
                new BeanPropertyRowMapper<>(TestProperties.class),
                testId);
    }

    public TestProperties fetch(int testId, int testNumber) throws DataNotFoundException {
        return runQuery("select * from test_properties where test_id = ? and test_number = ?",
                new BeanPropertyRowMapper<>(TestProperties.class),
                testId, testNumber);
    }
}
