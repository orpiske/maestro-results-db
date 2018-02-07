package net.orpiske.maestro.results.main.actions.report;

import net.orpiske.maestro.results.dao.ReportsDao;
import net.orpiske.maestro.results.dto.Sut;
import net.orpiske.maestro.results.dto.TestResultRecord;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProtocolReportCreator {
    private final String outputDir;

    public ProtocolReportCreator(final String outputDir) {
        this.outputDir = outputDir;
    }

    private String baseNameFormatter(final Sut sut, boolean durable, int limitDestinations, int messageSize,
                             int connectionCount) {
        return "report-" + sut.getSutName() + "-" + sut.getSutVersion() + (durable ? "-" : "-non-") + "durable-ld" +
                limitDestinations + "-s" + messageSize + "-c" + connectionCount;
    }

    public ReportInfo createProtocolReport(final Sut sut, boolean durable, int limitDestinations, int messageSize,
                              int connectionCount) throws Exception
    {
        ReportsDao reportsDao = new ReportsDao();

        List<TestResultRecord> testResultRecords = reportsDao.protocolReports(sut.getSutName(), sut.getSutVersion(),
                durable, limitDestinations, messageSize, connectionCount);

        if (testResultRecords == null || testResultRecords.size() == 0) {
            System.err.println("Not enough records for " + sut.getSutName() + " " + sut.getSutVersion());

            return null;
        }


        Map<String, Object> context = new HashMap<String, Object>();

        context.put("testResultRecords", testResultRecords);
        context.put("sut", sut);
        context.put("durable", durable);
        context.put("limitDestinations", limitDestinations);
        context.put("messageSize", messageSize);
        context.put("connectionCount", connectionCount);


        // Directory creating
        String sutDir = baseNameFormatter(sut, durable, limitDestinations, messageSize, connectionCount);
        File baseReportDir = new File(outputDir, sutDir);

        baseReportDir.mkdirs();

        // Data plotting
        ProtocolReportDataPlotter rdp = new ProtocolReportDataPlotter(baseReportDir);

        rdp.buildChart("", "", "Messages p/ second", testResultRecords,
                "performance-by-protocol.png");


        // Index HTML generation
        ProtocolReportRenderer protocolReportRenderer = new ProtocolReportRenderer(ReportTemplates.DEFAULT, context);
        File indexFile = new File(baseReportDir, "index.html");
        FileUtils.writeStringToFile(indexFile, protocolReportRenderer.render(), Charsets.UTF_8);

        return new ReportInfo(sut, sutDir, durable, limitDestinations, messageSize, connectionCount);

    }
}