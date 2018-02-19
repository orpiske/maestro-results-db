package net.orpiske.maestro.results.main.actions.report;

import net.orpiske.maestro.results.dao.SystemHealthReportsDao;
import net.orpiske.maestro.results.dto.ProtocolFailureRecord;
import net.orpiske.maestro.results.dto.TestResultMetricRecord;
import net.orpiske.maestro.results.dto.ValidTestRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SystemHealthReportCreator extends AbstractReportCreator {
    private static final Logger logger = LoggerFactory.getLogger(SystemHealthReportCreator.class);
    private final SystemHealthReportsDao reportsDao = new SystemHealthReportsDao();
    private Map<String, Object> context = new HashMap<>();

    public SystemHealthReportCreator(final String outputDir) {
        super(outputDir);
    }


    private void collectInconsistencies() {
        Integer durableInconsistencies = reportsDao.durableTestsInconsistentCount();
        Integer nonDurableInconsistencies = reportsDao.nonDurableTestsInconsistentCount();

        Integer inconsistentSize100 = reportsDao.inconsistentMessageSize("100");
        Integer inconsistentSize1024 = reportsDao.inconsistentMessageSize("1024");
        Integer inconsistentSize10240 = reportsDao.inconsistentMessageSize("10240");

        context.put("durableInconsistencies", durableInconsistencies);
        context.put("nonDurableInconsistencies", nonDurableInconsistencies);
        context.put("inconsistentSize100", inconsistentSize100);
        context.put("inconsistentSize1024", inconsistentSize1024);
        context.put("inconsistentSize10240", inconsistentSize10240);

    }


    private void collectConsistencies() {
        Integer durableConsistentCount = reportsDao.durableTestsConsistentCount();
        Integer nonDurableConsistentCount = reportsDao.nonDurableTestsConsistentCount();

        Integer consistentSize100 = reportsDao.consistentMessageSize("100");
        Integer consistentSize1024 = reportsDao.consistentMessageSize("1024");
        Integer consistentSize10240 = reportsDao.consistentMessageSize("10240");

        context.put("durableConsistentCount", durableConsistentCount);
        context.put("nonDurableConsistentCount", nonDurableConsistentCount);

        context.put("consistentSize100", consistentSize100);
        context.put("consistentSize1024", consistentSize1024);
        context.put("consistentSize10240", consistentSize10240);
    }

    private void collectTestMetrics() {
        List<ProtocolFailureRecord> protocolFailureRecords = reportsDao.testFailuresByProtocol();
        context.put("protocolFailureRecords", protocolFailureRecords);

        List<ValidTestRecord> validTestRecords = reportsDao.validTestRecords();
        context.put("validTestRecords", validTestRecords);

        List<TestResultMetricRecord> testResultMetricRecords = reportsDao.testResultMetrics();
        context.put("testResultMetricRecords", testResultMetricRecords);
    }

    public void create() throws Exception
    {
        collectInconsistencies();
        collectConsistencies();
        collectTestMetrics();

        File baseReportDir = createReportBaseDir("system-health");

        generateIndex("system-health.html", baseReportDir, context);
    }
}