package org.maestro.results.dao;

import org.maestro.results.dto.ProtocolFailureRecord;
import org.maestro.results.dto.TestResultMetricRecord;
import org.maestro.results.dto.ValidTestRecord;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import java.util.List;

public class SystemHealthReportsDao extends AbstractDao {

    private Integer protocolTestsConsistencies(final String reportLinkValue, final String dbTestFlagValue) {
        return jdbcTemplate.queryForObject("select count(*) from test_msg_property tmp " +
                        "where test_id in (select test_id from test where test_report_link like concat('%', ?, ',%') and test_valid = true) " +
                        "and test_msg_property_value = ? " +
                        "and test_msg_property_name = 'protocol' ",
                new Object[] { reportLinkValue, dbTestFlagValue },
                Integer.class
        );
    }

    public Integer amqpConsistencies() {
        return protocolTestsInconsistencies("single-qpid-jms", "AMQP");
    }


    public Integer openWireConsistencies() {
        return protocolTestsInconsistencies("single-activemq", "OPENWIRE");
    }

    public Integer coreConsistencies() {
        return protocolTestsInconsistencies("single-artemis", "ARTEMIS");
    }

    // single-qpid-jms
    private Integer protocolTestsInconsistencies(final String reportLinkValue, final String dbTestFlagValue) {
        return jdbcTemplate.queryForObject("select count(*) from test_msg_property tmp " +
                        "where test_id in (select test_id from test where test_report_link like concat('%', ?, ',%') and test_valid = true) " +
                        "and test_msg_property_value != ? " +
                        "and test_msg_property_name = 'protocol' ",
                new Object[] { reportLinkValue, dbTestFlagValue },
                Integer.class
        );
    }

    public Integer amqpInconsistencies() {
        return protocolTestsInconsistencies("single-qpid-jms", "AMQP");
    }


    public Integer openWireInconsistencies() {
        return protocolTestsInconsistencies("single-activemq", "OPENWIRE");
    }

    public Integer coreInconsistencies() {
        return protocolTestsInconsistencies("single-artemis", "ARTEMIS");
    }

    private Integer durableNonDurableTestsInconsistencies(final String reportLinkValue, final String dbTestFlagValue) {
        return jdbcTemplate.queryForObject("select count(*) from test_msg_property tmp " +
                "where test_id in (select test_id from test where test_report_link like concat('%DURABLE=', ?, ',%') and test_valid = true) " +
                "and test_msg_property_value = ? " +
                "and test_msg_property_name = 'durable' ",
                new Object[] { reportLinkValue, dbTestFlagValue },
                Integer.class
        );
    }

    /**
     * Returns the count of tests that have a report link with durable flag on but a db record w/
     * the durable property set to false. This is required because, sometimes, the backend fails
     * to download the correct file
     */
    public Integer durableTestsInconsistentCount() {
        return durableNonDurableTestsInconsistencies("true", "false");
    }


    /**
     * Consistent durable test count
     */
    public Integer durableTestsConsistentCount() {
        return durableNonDurableTestsInconsistencies("true", "true");
    }


    /**
     * Returns the count of tests that have a report link with non-durable flag on but a db record w/
     * the durable property set to true. This is required because, sometimes, the backend fails
     * to download the correct file
     */
    public Integer nonDurableTestsInconsistentCount() {
        return durableNonDurableTestsInconsistencies("false", "true");
    }


    /**
     * Consistent non-durable test count
     */
    public Integer nonDurableTestsConsistentCount() {
        return durableNonDurableTestsInconsistencies("false", "false");
    }


    private Integer inconsistentMessageSizeInternal(final String reportLinkValue, final String dbTestFlagValue) {
        return jdbcTemplate.queryForObject("select count(*) from test_msg_property tmp, test t " +
                        "where test_report_link like concat('%MESSAGE_SIZE=', ?, ',%') " +
                        "and test_msg_property_value != ?" +
                        "and test_msg_property_name = 'messageSize' " +
                        "and tmp.test_id = t.test_id " +
                        "and t.test_valid = true",
                new Object[] { reportLinkValue, dbTestFlagValue },
                Integer.class
        );
    }


    public Integer inconsistentMessageSize(final String reportLinkValue) {
        return inconsistentMessageSizeInternal(reportLinkValue, reportLinkValue);
    }


    private Integer consistentMessageSizeInternal(final String reportLinkValue, final String dbTestFlagValue) {
        return jdbcTemplate.queryForObject("select count(*) from test_msg_property tmp, test t " +
                        "where test_report_link like concat('%MESSAGE_SIZE=', ?, ',%') " +
                        "and test_msg_property_value = ?" +
                        "and test_msg_property_name = 'messageSize' " +
                        "and tmp.test_id = t.test_id " +
                        "and t.test_valid = true",
                new Object[] { reportLinkValue, dbTestFlagValue },
                Integer.class
        );
    }


    public Integer consistentMessageSize(final String reportLinkValue) {
        return consistentMessageSizeInternal(reportLinkValue, reportLinkValue);
    }


    public List<ProtocolFailureRecord> testFailuresByProtocol() {
        return jdbcTemplate.query("select tp.messaging_protocol,tr.test_result,count(tr.test_result) as test_records " +
                        "from test_results tr, test_properties tp " +
                        "where tr.test_id = tp.test_id " +
                        "and tr.test_number = tp.test_number " +
                        "and tr.test_valid = true " +
                        "and durable in ('false', 'true') " +
                        "and limit_destinations in (1, 10, 100) " +
                        "and message_size in (100, 1024, 1024) " +
                        "and connection_count in (1, 10, 100) " +
                        "group by tp.messaging_protocol,tr.test_result " +
                        "order by messaging_protocol",
                new BeanPropertyRowMapper<>(ProtocolFailureRecord.class)
        );
    }

    public List<ValidTestRecord> validTestRecords() {
        return jdbcTemplate.query("select s.sut_name,s.sut_version, count(*) as total, " +
                        "sum(t.test_valid=true) as valid_count,sum(t.test_valid=false) as invalid_count " +
                        "from test t, sut s where t.sut_id = s.sut_id group by s.sut_name,sut_version",
                new BeanPropertyRowMapper<>(ValidTestRecord.class)
        );
    }

    public List<TestResultMetricRecord> testResultMetrics() {
        return jdbcTemplate.query("select s.sut_name,s.sut_version, count(*) as total, " +
                        "sum(t.test_result='success') as passed_count,sum(t.test_result='failed') as failed_count " +
                        "from test t, sut s where t.sut_id = s.sut_id " +
                        "group by s.sut_name,sut_version",
                new BeanPropertyRowMapper<>(TestResultMetricRecord.class)
        );
    }
}
