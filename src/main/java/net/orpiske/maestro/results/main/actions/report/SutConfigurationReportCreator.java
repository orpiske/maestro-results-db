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

public class SutConfigurationReportCreator extends AbstractReportCreator {
    private static final Logger logger = LoggerFactory.getLogger(SutConfigurationReportCreator.class);
    private final ReportsDao reportsDao = new ReportsDao();

    public SutConfigurationReportCreator(final String outputDir) {
        super(outputDir);
    }


    public ReportInfo create(final Sut sut, final String protocol, final String configuration, boolean durable, int limitDestinations,
                             int messageSize, int connectionCount) throws Exception
    {
        List<TestResultRecord> testResultRecordsSender = reportsDao.sutConfigurationsReport(sut.getSutName(),
                sut.getSutVersion(), protocol, configuration, "sender", durable, limitDestinations, messageSize,
                connectionCount);

        if (testResultRecordsSender == null || testResultRecordsSender.size() == 0) {
            logger.debug("Not enough sender records for {} {}", sut.getSutName(), sut.getSutVersion());

            return null;
        }

        List<TestResultRecord> testResultRecordsReceiver = reportsDao.sutConfigurationsReport(sut.getSutName(),
                sut.getSutVersion(), protocol, configuration, "receiver", durable, limitDestinations, messageSize,
                connectionCount);

        if (testResultRecordsReceiver == null || testResultRecordsReceiver.size() == 0) {
            logger.debug("Not enough receiver records for {} {}", sut.getSutName(), sut.getSutVersion());

            return null;
        }


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