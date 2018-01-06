package net.orpiske.maestro.results.dao;

import net.orpiske.maestro.results.dto.TestMsgProperty;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import java.util.List;

public class TestMsgPropertyDao extends AbstractDao {
    public void insert(TestMsgProperty dto) {
        runEmptyInsert("insert into test_msg_property(test_id, test_msg_property_name, test_msg_property_value) "
                + "values(:testId, :testMsgPropertyName, :testMsgPropertyValue)", dto);
    }

    public List<TestMsgProperty> fetchById(int id) {
        return jdbcTemplate.query("select * from test_msg_property where test_fail_conditions_id = ?",
                new Object[] { id },
                new BeanPropertyRowMapper<>(TestMsgProperty.class));
    }

    public List<TestMsgProperty> fetch() {
        return jdbcTemplate.query("select * from test_msg_property",
                new BeanPropertyRowMapper<>(TestMsgProperty.class));
    }
}
