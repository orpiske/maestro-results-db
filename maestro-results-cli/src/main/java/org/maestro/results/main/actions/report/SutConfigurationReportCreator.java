package org.maestro.results.main.actions.report;

import org.maestro.results.dao.ReportsDao;
import org.maestro.results.dto.Sut;
import org.maestro.results.dto.TestReportRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SutConfigurationReportCreator extends AbstractReportCreator {
    private static final Logger logger = LoggerFactory.getLogger(SutConfigurationReportCreator.class);
    private final ReportsDao reportsDao = new ReportsDao();

    public SutConfigurationReportCreator(final String outputDir, final String testName) {
        super(outputDir, testName);
    }


    public ReportInfo create(final Sut sut, final String protocol, final String configuration, boolean durable, int limitDestinations,
                             int messageSize, int connectionCount) throws Exception
    {
        List<TestReportRecord> testReportRecordsSender = reportsDao.sutConfigurationsReport(sut.getSutName(),
                sut.getSutVersion(), protocol, configuration, "sender", durable, limitDestinations, messageSize,
                connectionCount, getTestName());
        validateResultSet(sut, "sender", testReportRecordsSender);

        List<TestReportRecord> testReportRecordsReceiver = reportsDao.sutConfigurationsReport(sut.getSutName(),
                sut.getSutVersion(), protocol, configuration, "receiver", durable, limitDestinations, messageSize,
                connectionCount, getTestName());
        validateResultSet(sut, "receiver", testReportRecordsReceiver);


        Map<String, Object> context = new HashMap<>();

        context.put("testReportRecordsSender", testReportRecordsSender);
        context.put("testReportRecordsReceiver", testReportRecordsReceiver);
        context.put("sut", sut);
        context.put("durable", durable);
        context.put("limitDestinations", limitDestinations);
        context.put("messageSize", messageSize);
        context.put("connectionCount", connectionCount);
        context.put("configuration", configuration);


        ReportInfo reportInfo = new SutConfigurationReportInfo(sut, protocol, configuration, durable, limitDestinations,
                messageSize, connectionCount);

        // Directory creating
        File baseReportDir = createReportBaseDir(reportInfo);


        // Data plotting
        SutConfigurationReportDataPlotter rdp = new SutConfigurationReportDataPlotter(baseReportDir);

        rdp.buildChart("", "", "Messages p/ second", testReportRecordsSender,
                "performance-by-configuration-sender.png");

        rdp.buildChart("", "", "Messages p/ second", testReportRecordsReceiver,
                "performance-by-configuration-receiver.png");

        generateIndex("configuration-results.html", baseReportDir, context);


        return reportInfo;
    }
}