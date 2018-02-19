package net.orpiske.maestro.results.main.actions.report;

import net.orpiske.maestro.results.dao.ReportsDao;
import net.orpiske.maestro.results.dto.Sut;
import net.orpiske.maestro.results.dto.TestResultRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContendedReportCreator extends AbstractReportCreator {
    private static final Logger logger  = LoggerFactory.getLogger(ContendedReportCreator.class);
    private final ReportsDao reportsDao = new ReportsDao();

    public ContendedReportCreator(final String outputDir) {
        super(outputDir);
    }


    public ReportInfo create(final Sut sut, final String protocol, boolean durable, int messageSize) throws Exception {
        List<TestResultRecord> testResultRecordsSender = reportsDao.contentedScalabilityReport(sut.getSutName(),
                sut.getSutVersion(), protocol, "sender", durable, messageSize);

        if (testResultRecordsSender == null || testResultRecordsSender.size() == 0) {
            logger.debug("Not enough sender records for {} {}", sut.getSutName(), sut.getSutVersion());

            return null;
        }


        List<TestResultRecord> testResultRecordsReceiver = reportsDao.contentedScalabilityReport(sut.getSutName(),
                sut.getSutVersion(), protocol, "receiver", durable, messageSize);

        if (testResultRecordsReceiver == null || testResultRecordsReceiver.size() == 0) {
            logger.debug("Not enough receiver records for {} {}", sut.getSutName(), sut.getSutVersion());

            return null;
        }


        Map<String, Object> context = new HashMap<>();

        context.put("testResultRecordsSender", testResultRecordsSender);
        context.put("testResultRecordsReceiver", testResultRecordsReceiver);
        context.put("sut", sut);
        context.put("durable", durable);
        context.put("limitDestinations", 1);
        context.put("messageSize", messageSize);


        ReportInfo reportInfo = new ContendedReportInfo(sut, protocol, durable, 1, messageSize, 0);

        // Directory creating
        File baseReportDir = createReportBaseDir(reportInfo);

        // Data plotting
        ContendedReportDataPlotter rdp = new ContendedReportDataPlotter(baseReportDir);

        rdp.buildChart("", "", "Messages p/ second", testResultRecordsSender,
                "contended-performance-sender.png");

        rdp.buildChart("", "", "Messages p/ second", testResultRecordsReceiver,
                "contended-performance-receiver.png");

        generateIndex("contended-results.html", baseReportDir, context);


        return reportInfo;
    }
}
