package net.orpiske.maestro.results.main.actions.report;

import net.orpiske.maestro.results.dao.ReportsDao;
import net.orpiske.maestro.results.dto.Sut;
import net.orpiske.maestro.results.dto.TestResultRecord;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DestinationScalabilityReportCreator extends AbstractReportCreator {
    ReportsDao reportsDao = new ReportsDao();

    public DestinationScalabilityReportCreator(String outputDir) {
        super(outputDir);
    }


    public ReportInfo create(final Sut sut, final String protocol, boolean durable, int messageSize) throws Exception
    {
        List<TestResultRecord> testResultRecordsSender = reportsDao.destinationScalabilityReport(sut.getSutName(), sut.getSutVersion(),
                protocol, "sender", durable, messageSize);

        if (testResultRecordsSender == null || testResultRecordsSender.size() == 0) {
            System.err.println("Not enough records for " + sut.getSutName() + " " + sut.getSutVersion());

            return null;
        }

        List<TestResultRecord> testResultRecordsReceiver = reportsDao.destinationScalabilityReport(sut.getSutName(), sut.getSutVersion(),
                protocol, "receiver", durable, messageSize);

        if (testResultRecordsReceiver == null || testResultRecordsReceiver.size() == 0) {
            System.err.println("Not enough records for " + sut.getSutName() + " " + sut.getSutVersion());

            return null;
        }


        ReportInfo reportInfo = new DestinationScalabilityReportInfo(sut, protocol, durable, 1, messageSize);

        // Directory creating
        File baseReportDir = createReportBaseDir(reportInfo);

        // Data plotting
        DestinationScalabilityReportDataPlotter rdp = new DestinationScalabilityReportDataPlotter(baseReportDir);

        rdp.buildChart("", "", "Messages p/ second", testResultRecordsSender,
                "ds-performance-sender.png");

        rdp.buildChart("", "", "Messages p/ second", testResultRecordsReceiver,
                "ds-performance-receiver.png");


        Map<String, Object> context = new HashMap<String, Object>();

        context.put("testResultRecordsSender", testResultRecordsSender);
        context.put("testResultRecordsReceiver", testResultRecordsReceiver);
        context.put("sut", sut);
        context.put("durable", durable);
        context.put("messageSize", messageSize);

        generateIndex("ds-results.html", baseReportDir, context);

        return reportInfo;
    }
}
