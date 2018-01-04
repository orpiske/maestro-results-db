package net.orpiske.maestro.results.dao;

import net.orpiske.maestro.results.dto.TestFailCondition;
import net.orpiske.maestro.results.dto.TestMsgProperty;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import java.util.List;

public class TestMsgPropertyDao extends AbstractDao {
    public int insert(TestMsgProperty dto) {
        return runUpdate(
                "insert into test_msg_property(test_parameter_id, test_msg_property_name, test_msg_property_value) values(?, ?, ?)",
                dto.getTestParameterId(),
                dto.getTestMsgPropertyName(),
                dto.getTestMsgPropertyValue());
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
