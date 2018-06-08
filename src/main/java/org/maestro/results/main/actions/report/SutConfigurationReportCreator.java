package org.maestro.results.main.actions.report;

import org.maestro.results.dao.ReportsDao;
import org.maestro.results.dto.Sut;
import org.maestro.results.dto.TestResultRecord;
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
        List<TestResultRecord> testResultRecordsSender = reportsDao.sutConfigurationsReport(sut.getSutName(),
                sut.getSutVersion(), protocol, configuration, "sender", durable, limitDestinations, messageSize,
                connectionCount, getTestName());
        validateResultSet(sut, "sender", testResultRecordsSender);

        List<TestResultRecord> testResultRecordsReceiver = reportsDao.sutConfigurationsReport(sut.getSutName(),
                sut.getSutVersion(), protocol, configuration, "receiver", durable, limitDestinations, messageSize,
                connectionCount, getTestName());
        validateResultSet(sut, "receiver", testResultRecordsReceiver);


        Map<String, Object> context = new HashMap<>();

        context.put("testResultRecordsSender", testResultRecordsSender);
        context.put("testResultRecordsReceiver", testResultRecordsReceiver);
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

        rdp.buildChart("", "", "Messages p/ second", testResultRecordsSender,
                "performance-by-configuration-sender.png");

        rdp.buildChart("", "", "Messages p/ second", testResultRecordsReceiver,
                "performance-by-configuration-receiver.png");

        generateIndex("configuration-results.html", baseReportDir, context);


        return reportInfo;
    }
}