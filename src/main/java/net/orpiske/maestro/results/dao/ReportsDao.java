package net.orpiske.maestro.results.dao;

import net.orpiske.maestro.results.common.ReportConfig;
import net.orpiske.maestro.results.dto.TestResultRecord;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowCallbackHandler;

import java.util.List;

public class ReportsDao extends AbstractDao {
    /**
     * Returns the performance report for different protocols with the same product and multiple configurations
     * @param sutName
     * @param sutVersion
     * @param durable
     * @param limitDestinations
     * @param messageSize
     * @param connectionCount
     * @return
     */
    public List<TestResultRecord> protocolReports(final String sutName, final String sutVersion,
                                                  boolean durable, int limitDestinations, int messageSize,
                                                  int connectionCount, final String testName) {
        return jdbcTemplate.query("select tr.sut_name,tr.sut_version,tr.sut_tags,tr.test_tags,tr.test_result,tr.error,tr.connection_count,tp.limit_destinations," +
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
                        "and tr.test_name = ? " +
                        "group by sut_name, sut_version, messaging_protocol,env_resource_role, sut_tags " +
                        "order by tr.test_rate_geometric_mean desc",
                new Object[] { durable, limitDestinations, messageSize, connectionCount, sutName, sutVersion, testName },
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
                                                             boolean durable, int messageSize, final String testName) {
        String connectionCounts = ReportConfig.getJoinedString(testName, "report.connectionCounts");
        String limitDestination = ReportConfig.getString(testName, "report.contentedScalability.limitDestination");

        return jdbcTemplate.query("select tr.sut_name,tr.sut_version,tr.sut_tags,tr.test_tags,tr.test_result,tr.error,tr.connection_count,tp.limit_destinations," +
                "tp.message_size,tp.api_name,tp.api_version,tp.messaging_protocol,tp.durable,tr.test_rate_min,tr.test_rate_max," +
                "tr.test_rate_geometric_mean,tr.test_rate_standard_deviation,tr.test_rate_skip_count," +
                "tr.test_date,tr.test_report_link," +
                "tp.variable_size as variable_size_flag,tr.env_resource_role,tr.env_resource_name as test_host " +
                "from test_results tr, test_properties tp " +
                "where tr.test_id = tp.test_id and tr.test_number = tp.test_number " +
                "and tr.test_valid = true " +
                "and tp.limit_destinations = ? " +
                "and tr.connection_count in (" + connectionCounts + ") " +
                "and tr.sut_name = ? " +
                "and tr.sut_version = ? " +
                "and tp.durable = ? " +
                "and tp.message_size = ? " +
                "and tp.messaging_protocol = ? " +
                "and tr.env_resource_role = ? " +
                "and tr.test_name = ? " +
                "order by tr.connection_count,tr.test_rate_geometric_mean asc",
                new Object[] {limitDestination, sutName, sutVersion, durable, messageSize, messagingProtocol, role, testName},
                new BeanPropertyRowMapper<>(TestResultRecord.class)
                );
    }


    /**
     * Destination scalability report
     * @param sutName
     * @param sutVersion
     * @param messagingProtocol
     * @param role
     * @param durable
     * @param messageSize
     * @return
     */
    public List<TestResultRecord> destinationScalabilityReport(final String sutName, final String sutVersion,
                                                               final String messagingProtocol, final String role,
                                                               boolean durable, int messageSize, final String testName) {
        String destinationString = ReportConfig.getJoinedString(testName, "report.limitDestinations");
        String connectionCount = ReportConfig.getString(testName, "report.destinationScalability.connectionCount");

        return jdbcTemplate.query("select tr.sut_name,tr.sut_version,tr.sut_tags,tr.test_tags,tr.test_result,tr.error,tr.connection_count,tp.limit_destinations, " +
                "tp.message_size,tp.api_name,tp.api_version,tp.messaging_protocol,tp.durable,tr.test_rate_min,tr.test_rate_max, " +
                "tr.test_rate_geometric_mean,tr.test_rate_standard_deviation,tr.test_rate_skip_count, " +
                "tr.test_date,tr.test_report_link, " +
                "tp.variable_size as variable_size_flag,tr.env_resource_role,tr.env_resource_name as test_host " +
                "from test_results tr, test_properties tp " +
                "where tr.test_id = tp.test_id and tr.test_number = tp.test_number " +
                "and tr.test_valid = true " +
                // I do this because it does not use a NamedJdbcParameter (Maybe there's a better way?)
                "and tp.limit_destinations in (" + destinationString + ") " +
                "and tr.connection_count = ? " +
                "and tr.sut_name = ? " +
                "and tr.sut_version = ? " +
                "and tp.durable = ? " +
                "and tp.message_size = ? " +
                "and tp.messaging_protocol = ? " +
                "and tr.env_resource_role = ? " +
                "and tr.test_name = ? " +
                "order by tp.limit_destinations,tr.test_rate_geometric_mean asc",
                new Object[] {connectionCount, sutName, sutVersion, durable, messageSize, messagingProtocol, role, testName},
                new BeanPropertyRowMapper<>(TestResultRecord.class)
        );
    }


    /**
     * Gets the results for different SUT configurations (aka test tags) of the same product and version
     * @param sutName
     * @param sutVersion
     * @param sutTags
     * @param durable
     * @param limitDestinations
     * @param messageSize
     * @param connectionCount
     * @return
     */
    public List<TestResultRecord> sutConfigurationsReport(final String sutName, final String sutVersion, final String protocol,
                                                          final String sutTags, final String role, boolean durable, int limitDestinations,
                                                          int messageSize, int connectionCount, final String testName) {
        return jdbcTemplate.query("select tr.sut_name,tr.sut_version,tr.sut_tags,tr.test_tags,tr.test_result,tr.error,tr.connection_count,tp.limit_destinations," +
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
                        "and tr.sut_tags = ? " +
                        "and tr.env_resource_role = ? " +
                        "and tp.messaging_protocol = ? " +
                        "and tr.test_name = ? " +
                        "group by sut_name,sut_version,messaging_protocol,sut_tags,test_tags " +
                        "order by tr.test_rate_geometric_mean desc",
                new Object[] { durable, limitDestinations, messageSize, connectionCount, sutName, sutVersion, sutTags,
                        role, protocol, testName },
                new BeanPropertyRowMapper<>(TestResultRecord.class));
    }


    public void reportDeltas(final String sutName, final String sutVersion, final String protocol, int messageSize,
                        final String testName, final RowCallbackHandler callbackHandler) {
        jdbcTemplate.query("select " +
                "lhs.test_id as lhs_test_id, " +
                "lhs.sut_id as lhs_sut_id, " +
                "lhs.sut_tags as lhs_sut_tags, " +
                "lhs.test_tags as lhs_test_tags ," +
                "lhs.sut_name as lhs_sut_name, " +
                "lhs.sut_version as lhs_sut_version, " +
                "lhs.test_valid as lhs_test_valid, " +
                "lhs.env_resource_role as lhs_env_resource_role, " +
                "lhs.connection_count as lhs_connection_count, " +
                "lhp.limit_destinations as lhs_limit_destinations, " +
                "lhp.message_size as lhs_message_size, " +
                "lhp.messaging_protocol as lhs_messaging_protocol, " +
                "lhp.variable_size as lhs_variable_size, " +
                "lhs.test_rate_geometric_mean as lhs_test_rate_geometric_mean, " +
                "lhs.test_date as lhs_test_date, " +
                "rhs.sut_tags as rhs_sut_tags, " +
                "rhs.test_tags as rhs_test_tags, " +
                "rhs.sut_name as rhs_sut_name, " +
                "rhs.sut_version as rhs_sut_version, " +
                "rhs.test_valid as rhs_test_valid, " +
                "rhs.env_resource_role as rhs_env_resource_role, " +
                "rhp.api_name as rhs_api_name, " +
                "rhp.api_version as rhs_api_version, " +
                "rhs.connection_count as rhs_connection_count, " +
                "rhp.limit_destinations as rhs_limit_destinations, " +
                "rhp.message_size as rhs_message_size, " +
                "rhp.messaging_protocol as rhs_messaging_protocol, " +
                "rhp.variable_size as rhs_variable_size, " +
                "rhs.test_rate_geometric_mean as rhs_test_rate_geometric_mean, " +
                "(lhs.test_rate_geometric_mean - rhs.test_rate_geometric_mean) as diff " +
                "from test_results lhs inner join (test_results rhs, test_properties rhp, test_properties lhp) " +
                "on (lhs.sut_tags = rhs.sut_tags and lhs.env_resource_role = rhs.env_resource_role and rhs.test_id = rhp.test_id and lhp.test_id = lhs.test_id) " +
                "and lhs.sut_name = ? " +
                "and lhs.sut_version = ? " +
                "and lhp.messaging_protocol = ? " +
                "and lhp.message_size = ? " +
                "and lhs.test_valid = true " +
                "and rhs.test_valid = true " +
                "and lhs.test_name = ? " +
                "and rhs.test_name = ? " +
                "order by lhs_test_id", new Object[] { sutName, sutVersion, protocol, messageSize, testName, testName },
                callbackHandler);
    }
}
