package org.maestro.results.main.actions.report;

import org.maestro.results.common.ReportConfig;
import org.maestro.results.dao.ReportsDao;
import org.maestro.results.dto.Sut;
import org.maestro.results.dto.TestReportRecord;
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
        List<TestReportRecord> testReportRecordsSender = reportsDao.destinationScalabilityReport(sut.getSutName(), sut.getSutVersion(),
                protocol, "sender", durable, messageSize, getTestName());
        validateResultSet(sut, "sender", testReportRecordsSender);


        List<TestReportRecord> testReportRecordsReceiver = reportsDao.destinationScalabilityReport(sut.getSutName(), sut.getSutVersion(),
                protocol, "receiver", durable, messageSize, getTestName());
        validateResultSet(sut, "receiver", testReportRecordsReceiver);

        Integer connectionCount = ReportConfig.getInteger(getTestName(),
                "report.destinationScalability.connectionCount");

        ReportInfo reportInfo = new DestinationScalabilityReportInfo(sut, protocol, durable, messageSize,
                connectionCount);

        // Directory creating
        File baseReportDir = createReportBaseDir(reportInfo);

        // Data plotting
        DestinationScalabilityReportDataPlotter rdp = new DestinationScalabilityReportDataPlotter(baseReportDir);

        rdp.buildChart("", "", "Messages p/ second", testReportRecordsSender,
                "ds-performance-sender.png");

        rdp.buildChart("", "", "Messages p/ second", testReportRecordsReceiver,
                "ds-performance-receiver.png");


        Map<String, Object> context = new HashMap<>();

        context.put("testReportRecordsSender", testReportRecordsSender);
        context.put("testReportRecordsReceiver", testReportRecordsReceiver);
        context.put("sut", sut);
        context.put("durable", durable);
        context.put("messageSize", messageSize);
        context.put("connections", connectionCount);

        generateIndex("ds-results.html", baseReportDir, context);

        return reportInfo;
    }
}
