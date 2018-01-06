package net.orpiske.maestro.results.dao;

import net.orpiske.maestro.results.dto.TestParameter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import java.util.List;

public class TestParameterDao extends AbstractDao {

    public int insert(TestParameter testParameter) {
        return runInsert("insert into test_parameter(test_target_rate, test_sender_count, test_receiver_count) " +
                "values(:testTargetRate, :testSenderCount, :testReceiverCount)", testParameter);
    }

    public List<TestParameter> fetchById(int id) {
        return jdbcTemplate.query("select * from test_parameter where test_parameter_id = ?",
                new Object[] { id },
                new BeanPropertyRowMapper<>(TestParameter.class));
    }

    public List<TestParameter> fetch() {
        return jdbcTemplate.query("select * from test_parameter",
                new BeanPropertyRowMapper<>(TestParameter.class));
    }
}
