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

public class ContendedReportCreator {
    private String outputDir;

    public ContendedReportCreator(String outputDir) {
        this.outputDir = outputDir;
    }

    private String baseNameFormatter(final Sut sut, boolean durable, int messageSize) {
        return "report-contended-" + sut.getSutName() + "-" + sut.getSutVersion() + (durable ? "-" : "-non-") +
                "durable-" + "s" + messageSize;
    }

    public ReportInfo create(final Sut sut, final String protocol, boolean durable, int messageSize) throws Exception {
        ReportsDao reportsDao = new ReportsDao();

        List<TestResultRecord> testResultRecords = reportsDao.contentedScalabilityReport(sut.getSutName(),
                sut.getSutVersion(), protocol, durable, messageSize);

        if (testResultRecords == null || testResultRecords.size() == 0) {
            System.err.println("Not enough records for " + sut.getSutName() + " " + sut.getSutVersion());

            return null;
        }

        testResultRecords.forEach(System.out::println);


        Map<String, Object> context = new HashMap<String, Object>();

        context.put("testResultRecords", testResultRecords);
        context.put("sut", sut);
        context.put("durable", durable);
        context.put("limitDestinations", 1);
        context.put("messageSize", messageSize);


        // Directory creating
        String sutDir = baseNameFormatter(sut, durable, messageSize);
        File baseReportDir = new File(outputDir, sutDir);

        baseReportDir.mkdirs();

        // Data plotting
        ContendedReportDataPlotter rdp = new ContendedReportDataPlotter(baseReportDir);

        rdp.buildChart("", "", "Messages p/ second", testResultRecords,
                "contended-performance.png");

        // Index HTML generation
        ContendedReportRenderer reportRenderer = new ContendedReportRenderer(ReportTemplates.DEFAULT, context);
        File indexFile = new File(baseReportDir, "index.html");
        FileUtils.writeStringToFile(indexFile, reportRenderer.render(), Charsets.UTF_8);

        return new ReportInfo(sut, sutDir, durable, 1, messageSize, 0);
    }
}
