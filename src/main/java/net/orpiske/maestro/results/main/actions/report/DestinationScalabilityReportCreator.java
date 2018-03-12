package net.orpiske.maestro.results.main.actions.report;

import net.orpiske.maestro.results.common.ReportConfig;
import net.orpiske.maestro.results.dao.ReportsDao;
import net.orpiske.maestro.results.dto.Sut;
import net.orpiske.maestro.results.dto.TestResultRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DestinationScalabilityReportCreator extends AbstractReportCreator {
    private static final Logger logger = LoggerFactory.getLogger(DestinationScalabilityReportCreator.class);

    private final ReportsDao reportsDao = new ReportsDao();

    public DestinationScalabilityReportCreator(final String outputDir, final String testName) {
        super(outputDir, testName);
    }


    public ReportInfo create(final Sut sut, final String protocol, boolean durable, int messageSize) throws Exception
    {
        List<TestResultRecord> testResultRecordsSender = reportsDao.destinationScalabilityReport(sut.getSutName(), sut.getSutVersion(),
                protocol, "sender", durable, messageSize, getTestName());
        validateResultSet(sut, "sender", testResultRecordsSender);


        List<TestResultRecord> testResultRecordsReceiver = reportsDao.destinationScalabilityReport(sut.getSutName(), sut.getSutVersion(),
                protocol, "receiver", durable, messageSize, getTestName());
        validateResultSet(sut, "receiver", testResultRecordsReceiver);

        Integer connectionCount = ReportConfig.getInteger(getTestName(),
                "report.destinationScalability.connectionCount");

        ReportInfo reportInfo = new DestinationScalabilityReportInfo(sut, protocol, durable, messageSize,
                connectionCount);

        // Directory creating
        File baseReportDir = createReportBaseDir(reportInfo);

        // Data plotting
        DestinationScalabilityReportDataPlotter rdp = new DestinationScalabilityReportDataPlotter(baseReportDir);

        rdp.buildChart("", "", "Messages p/ second", testResultRecordsSender,
                "ds-performance-sender.png");

        rdp.buildChart("", "", "Messages p/ second", testResultRecordsReceiver,
                "ds-performance-receiver.png");


        Map<String, Object> context = new HashMap<>();

        context.put("testResultRecordsSender", testResultRecordsSender);
        context.put("testResultRecordsReceiver", testResultRecordsReceiver);
        context.put("sut", sut);
        context.put("durable", durable);
        context.put("messageSize", messageSize);
        context.put("connections", connectionCount);

        generateIndex("ds-results.html", baseReportDir, context);

        return reportInfo;
    }
}
