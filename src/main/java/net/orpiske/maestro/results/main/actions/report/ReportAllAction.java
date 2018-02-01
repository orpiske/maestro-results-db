package net.orpiske.maestro.results.main.actions.report;

import net.orpiske.maestro.results.dao.ReportsDao;
import net.orpiske.maestro.results.dao.SutDao;
import net.orpiske.maestro.results.dto.Sut;
import net.orpiske.maestro.results.dto.TestResultRecord;
import net.orpiske.maestro.results.main.Action;
import org.apache.commons.cli.*;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportAllAction extends Action {
    private CommandLine cmdLine;
    private Options options;
    private String output;

    public ReportAllAction(final String[] args) {
        processCommand(args);
    }

    @Override
    protected void processCommand(final String[] args) {
        CommandLineParser parser = new PosixParser();

        options = new Options();

        options.addOption("h", "help", false, "prints the help");
        options.addOption("o", "output", true, "output directory");

        try {
            cmdLine = parser.parse(options, args);
        } catch (ParseException e) {
            System.err.println(e.getMessage());
            help(options, -1);
        }

        if (cmdLine.hasOption("help")) {
            help(options, 0);
        }

        output = cmdLine.getOptionValue('o');
        if (output == null) {
            System.err.println("Output directory is mandatory. Use option '-o' to inform it");
            help(options, -1);
        }
    }

    @Override
    public int run() {

        createReport();

        return 0;
    }

    private void createReport() {
        SutDao sutDao = new SutDao();

        List<Sut> sutList = sutDao.fetch();
        sutList.forEach(this::createReportForSut);

    }

    private void createReportForSut(final Sut sut) {
        boolean durableFlags[] = { true, false };
        int limitDestinations[] = { 1, 10, 100};
        int messageSizes[] = { 1, 10, 100};
        int connectionCounts[] = { 1, 10, 100};

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

    private String baseNameFormatter(final Sut sut, boolean durable, int limitDestinations, int messageSize,
                                     int connectionCount) {
        return "report-" + sut.getSutName() + "-" + sut.getSutVersion() + (durable ? "-non-" : "-" ) + "durable-ld" +
                limitDestinations + "-s" + messageSize + "-c" + connectionCount;
    }


    private void createReportForSutByParams(final Sut sut, boolean durable, int limitDestinations, int messageSize,
                                            int connectionCount) {
        ReportsDao reportsDao = new ReportsDao();

        List<TestResultRecord> testResultRecords = reportsDao.protocolReports(sut.getSutName(), sut.getSutVersion(),
                durable, limitDestinations, messageSize, connectionCount);

        if (testResultRecords == null || testResultRecords.size() == 0) {
            System.err.println("Not enough records for " + sut.getSutName() + " " + sut.getSutVersion());

            return;
        }


        Map<String, Object> context = new HashMap<>();

        context.put("testResultRecords", testResultRecords);
        context.put("sut", sut);
        context.put("durable", durable);
        context.put("limitDestinations", limitDestinations);
        context.put("messageSize", messageSize);
        context.put("connectionCount", connectionCount);

        try {
            // Directory creating
            String sutDir = baseNameFormatter(sut, durable, limitDestinations, messageSize, connectionCount);
            File baseReportDir = new File(output, sutDir);

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
