package net.orpiske.maestro.results.main.actions.report;

import net.orpiske.maestro.results.dao.ReportsDao;
import net.orpiske.maestro.results.dao.SutDao;
import net.orpiske.maestro.results.dto.Sut;
import net.orpiske.maestro.results.dto.TestResultRecord;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ReportCreator {
    class ReportInfo {
        private Sut sut;
        private String linkName;
        private boolean durable;
        private int limitDestinations;
        private int messageSize;
        private int connectionCount;

        public ReportInfo(Sut sut, String linkName, boolean durable, int limitDestinations, int messageSize, int connectionCount) {
            this.sut = sut;
            this.linkName = linkName;
            this.durable = durable;
            this.limitDestinations = limitDestinations;
            this.messageSize = messageSize;
            this.connectionCount = connectionCount;
        }

        public Sut getSut() {
            return sut;
        }

        public String getLinkName() {
            return linkName;
        }

        public boolean isDurable() {
            return durable;
        }

        public int getLimitDestinations() {
            return limitDestinations;
        }

        public int getMessageSize() {
            return messageSize;
        }

        public int getConnectionCount() {
            return connectionCount;
        }
    }

    private String outputDir;

    private List<ReportInfo> reportInfoList = new LinkedList<>();

    public ReportCreator(final String outputDir) {
        this.outputDir = outputDir;
    }

    void createReport() {
        SutDao sutDao = new SutDao();

        List<Sut> sutList = sutDao.fetchDistinct();
        sutList.forEach(this::createReportForSut);

        System.out.println("Number of reports created: " + reportInfoList.size());

        Map<String, Object> context = new HashMap<String, Object>();
        context.put("reportInfoList", reportInfoList);

        IndexRenderer indexRenderer = new IndexRenderer(ReportTemplates.DEFAULT, context);

        File indexFile = new File(outputDir, "index.html");
        try {
            FileUtils.writeStringToFile(indexFile, indexRenderer.render(), Charsets.UTF_8);

            indexRenderer.copyResources(indexFile.getParentFile());
        } catch (Exception e) {
            e.printStackTrace();
        }


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
            ResultsReportRenderer resultsReportRenderer = new ResultsReportRenderer(ReportTemplates.DEFAULT, context);
            File indexFile = new File(baseReportDir, "index.html");
            FileUtils.writeStringToFile(indexFile, resultsReportRenderer.render(), Charsets.UTF_8);

            ReportInfo reportInfo = new ReportInfo(sut, sutDir, durable, limitDestinations, messageSize, connectionCount);

            reportInfoList.add(reportInfo);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}