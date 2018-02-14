package net.orpiske.maestro.results.dao;

import net.orpiske.maestro.results.dto.TestResultRecord;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import java.util.List;

public class ReportsDao extends AbstractDao {
    public List<TestResultRecord> protocolReports(final String sutName, final String sutVersion,
                                                  boolean durable, int limitDestinations, int messageSize,
                                                  int connectionCount) {
        return jdbcTemplate.query("select tr.sut_name,tr.sut_version,tr.sut_tags,tr.test_result,tr.error,tr.connection_count,tp.limit_destinations," +
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
                        "and tr.sut_name = ? " +
                        "and tr.sut_version = ? " +
                        "group by sut_name, sut_version, messaging_protocol,env_resource_role, sut_tags " +
                        "order by tr.test_rate_geometric_mean desc",
                new Object[] { durable, limitDestinations, messageSize, connectionCount, sutName, sutVersion },
                new BeanPropertyRowMapper<>(TestResultRecord.class));
    }


    /**
     * Returns connections/edges scalability eg throughput of 1 destination contended by 1,10,100 pairs (ie producers/consumers)
     * @param sutName
     * @param sutVersion
     * @param messagingProtocol
     * @param durable
     * @param messageSize
     * @return
     */
    public List<TestResultRecord> contentedScalabilityReport(final String sutName, final String sutVersion,
                                                             final String messagingProtocol, final String role,
                                                             boolean durable, int messageSize
                                                             ) {
        return jdbcTemplate.query("select tr.sut_name,tr.sut_version,tr.sut_tags,tr.test_result,tr.error,tr.connection_count,tp.limit_destinations," +
                "tp.message_size,tp.api_name,tp.api_version,tp.messaging_protocol,tp.durable,tr.test_rate_min,tr.test_rate_max," +
                "tr.test_rate_geometric_mean,tr.test_rate_standard_deviation,tr.test_rate_skip_count," +
                "tr.test_date,tr.test_report_link," +
                "tp.variable_size as variable_size_flag,tr.env_resource_role,tr.env_resource_name as test_host " +
                "from test_results tr, test_properties tp " +
                "where tr.test_id = tp.test_id and tr.test_number = tp.test_number " +
                "and tr.test_valid = true " +
                "and tp.limit_destinations = 1 " +
                "and tr.connection_count in (1, 10, 100) " +
                "and tr.sut_name = ? " +
                "and tr.sut_version = ? " +
                "and tp.durable = ? " +
                "and tp.message_size = ? " +
                "and tp.messaging_protocol = ? " +
                "and tr.env_resource_role = ? " +
                "order by tr.connection_count,tr.test_rate_geometric_mean asc",
                new Object[] {sutName, sutVersion, durable, messageSize,  messagingProtocol, role},
                new BeanPropertyRowMapper<>(TestResultRecord.class)
                );
    }


    public List<TestResultRecord> destinationScalabilityReport(final String sutName, final String sutVersion,
                                                               final String messagingProtocol, final String role,
                                                               boolean durable, int messageSize) {
        return jdbcTemplate.query("select tr.sut_name,tr.sut_version,tr.sut_tags,tr.test_result,tr.error,tr.connection_count,tp.limit_destinations, " +
                "tp.message_size,tp.api_name,tp.api_version,tp.messaging_protocol,tp.durable,tr.test_rate_min,tr.test_rate_max, " +
                "tr.test_rate_geometric_mean,tr.test_rate_standard_deviation,tr.test_rate_skip_count, " +
                "tr.test_date,tr.test_report_link, " +
                "tp.variable_size as variable_size_flag,tr.env_resource_role,tr.env_resource_name as test_host " +
                "from test_results tr, test_properties tp " +
                "where tr.test_id = tp.test_id and tr.test_number = tp.test_number " +
                "and tr.test_valid = true " +
                "and tp.limit_destinations in (1, 10, 100) " +
                "and tr.connection_count = 100 " +
                "and tr.sut_name = ? " +
                "and tr.sut_version = ? " +
                "and tp.durable = ? " +
                "and tp.message_size = ? " +
                "and tp.messaging_protocol = ? " +
                "and tr.env_resource_role = ? " +
                "order by tp.limit_destinations,tr.test_rate_geometric_mean asc",
                new Object[] {sutName, sutVersion, durable, messageSize,  messagingProtocol, role},
                new BeanPropertyRowMapper<>(TestResultRecord.class)
        );
    }
}
