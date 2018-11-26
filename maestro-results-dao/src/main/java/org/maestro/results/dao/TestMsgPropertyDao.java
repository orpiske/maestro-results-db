package org.maestro.results.dao;

import org.maestro.results.dto.TestMsgProperty;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import java.util.List;

import org.maestro.reports.dao.exceptions.DataNotFoundException;
import org.maestro.reports.dao.AbstractDao;

public class TestMsgPropertyDao extends AbstractDao {
    public void insert(TestMsgProperty dto) {
        runEmptyInsert("insert into test_msg_property(test_id, test_number, test_msg_property_resource_name, " +
                        "test_msg_property_name, test_msg_property_value) values(:testId, :testNumber, " +
                        ":testMsgPropertyResourceName, :testMsgPropertyName, :testMsgPropertyValue)", dto);
    }


    public List<TestMsgProperty> fetch() throws DataNotFoundException {
        return runQueryMany("select * from test_msg_property",
                new BeanPropertyRowMapper<>(TestMsgProperty.class));
    }
}
