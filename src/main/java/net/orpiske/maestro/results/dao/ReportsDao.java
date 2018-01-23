package net.orpiske.maestro.results.dao;

import net.orpiske.maestro.results.dto.TestResultRecord;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import java.util.List;

public class ReportsDao extends AbstractDao {
    public List<TestResultRecord> protocolReports(boolean durable, int limitDestinations, int messageSize,
                                                  int connectionCount) {
        return jdbcTemplate.query("select tr.sut_name,tr.sut_version,tr.test_result,tr.error,tr.connection_count,tp.limit_destinations," +
                        "tp.message_size,tp.api_name,tp.api_version,tp.messaging_protocol,tp.durable,tr.test_rate_min,tr.test_rate_max," +
                        "tr.test_rate_geometric_mean,tr.test_rate_standard_deviation,tr.test_rate_skip_count," +
                        "tr.test_date,tr.test_report_link," +
                        "tp.variable_size as variable_size_flag,tr.env_resource_role,tr.env_resource_name as test_host " +
                        "from test_results tr, test_properties tp " +
                        "where tr.test_id = tp.test_id and tr.test_number = tp.test_number " +
                        "and durable = ? " +
                        "and limit_destinations = ? " +
                        "and message_size = ? " +
                        "and connection_count = ? " +
                        "and tr.test_valid = true " +
                        "order by tr.test_rate_geometric_mean desc",
                new Object[] { durable, limitDestinations, messageSize, connectionCount },
                new BeanPropertyRowMapper<>(TestResultRecord.class));
    }
}
