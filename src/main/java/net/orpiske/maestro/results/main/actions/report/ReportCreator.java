package net.orpiske.maestro.results.main.actions.report;

import net.orpiske.maestro.results.dao.ReportsDao;
import net.orpiske.maestro.results.dao.SutDao;
import net.orpiske.maestro.results.dto.Sut;
import net.orpiske.maestro.results.dto.TestResultRecord;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportCreator {
    private String outputDir;

    public ReportCreator(final String outputDir) {
        this.outputDir = outputDir;
    }

    void createReport() {
        SutDao sutDao = new SutDao();

        List<Sut> sutList = sutDao.fetch();
        sutList.forEach(this::createReportForSut);

    }

    void createReportForSut(final Sut sut) {
        boolean durableFlags[] = {true, false};
        int limitDestinations[] = {1, 10, 100};
        int messageSizes[] = {1, 10, 100};
        int connectionCounts[] = {1, 10, 100};

        for (boolean durable : durableFlags) {
            for (int messageSize : messageSizes) {
                for (int connectionCount : connectionCounts) {
                    for (int limitDestination : limitDestinations) {
                        if (limitDestination > connectionCount) {
                            continue;
                        }

                        createReportForSutByParams(sut, durable, limitDestination, messageSize, connectionCount);
                    }
                }
            }
        }
    }

    String baseNameFormatter(final Sut sut, boolean durable, int limitDestinations, int messageSize,
                             int connectionCount) {
        return "report-" + sut.getSutName() + "-" + sut.getSutVersion() + (durable ? "-" : "-non-") + "durable-ld" +
                limitDestinations + "-s" + messageSize + "-c" + connectionCount;
    }

    void createReportForSutByParams(final Sut sut, boolean durable, int limitDestinations, int messageSize,
                                    int connectionCount) {
        ReportsDao reportsDao = new ReportsDao();

        List<TestResultRecord> testResultRecords = reportsDao.protocolReports(sut.getSutName(), sut.getSutVersion(),
                durable, limitDestinations, messageSize, connectionCount);

        if (testResultRecords == null || testResultRecords.size() == 0) {
            System.err.println("Not enough records for " + sut.getSutName() + " " + sut.getSutVersion());

            return;
        }


        Map<String, Object> context = new HashMap<String, Object>();

        context.put("testResultRecords", testResultRecords);
        context.put("sut", sut);
        context.put("durable", durable);
        context.put("limitDestinations", limitDestinations);
        context.put("messageSize", messageSize);
        context.put("connectionCount", connectionCount);

        try {
            // Directory creating
            String sutDir = baseNameFormatter(sut, durable, limitDestinations, messageSize, connectionCount);
            File baseReportDir = new File(outputDir, sutDir);

            baseReportDir.mkdirs();

            // Data plotting
            ReportDataPlotter rdp = new ReportDataPlotter(baseReportDir);

            rdp.buildChart("", "Configuration", "Messages p/ second", testResultRecords,
                    "performance-by-protocol.png");


            // Index HTML generation
            ResultsReportRenderer resultsReportRenderer = new ResultsReportRenderer(context);
            File indexFile = new File(baseReportDir, "index.html");
            FileUtils.writeStringToFile(indexFile, resultsReportRenderer.render(), Charsets.UTF_8);

            resultsReportRenderer.copyResources(baseReportDir);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}