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

public class ContendedReportCreator extends AbstractReportCreator {
    private static final Logger logger  = LoggerFactory.getLogger(ContendedReportCreator.class);
    private final ReportsDao reportsDao = new ReportsDao();

    public ContendedReportCreator(final String outputDir, final String testName) {
        super(outputDir, testName);
    }


    public ReportInfo create(final Sut sut, final String protocol, boolean durable, int messageSize) throws Exception {
        List<TestReportRecord> testReportRecordsSender = reportsDao.contentedScalabilityReport(sut.getSutName(),
                sut.getSutVersion(), protocol, "sender", durable, messageSize, getTestName());
        validateResultSet(sut, "sender", testReportRecordsSender);

        List<TestReportRecord> testReportRecordsReceiver = reportsDao.contentedScalabilityReport(sut.getSutName(),
                sut.getSutVersion(), protocol, "receiver", durable, messageSize, getTestName());
        validateResultSet(sut, "receiver", testReportRecordsReceiver);



        Map<String, Object> context = new HashMap<>();

        context.put("testReportRecordsSender", testReportRecordsSender);
        context.put("testReportRecordsReceiver", testReportRecordsReceiver);
        context.put("sut", sut);
        context.put("durable", durable);
        context.put("limitDestinations", 1);
        context.put("messageSize", messageSize);


        ReportInfo reportInfo = new ContendedReportInfo(sut, protocol, durable, 1, messageSize, 0);

        // Directory creating
        File baseReportDir = createReportBaseDir(reportInfo);

        // Data plotting
        ContendedReportDataPlotter rdp = new ContendedReportDataPlotter(baseReportDir);

        rdp.buildChart("", "", "Messages p/ second", testReportRecordsSender,
                "contended-performance-sender.png");

        rdp.buildChart("", "", "Messages p/ second", testReportRecordsReceiver,
                "contended-performance-receiver.png");

        generateIndex("contended-results.html", baseReportDir, context);


        return reportInfo;
    }
}
