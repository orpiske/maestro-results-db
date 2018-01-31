package net.orpiske.maestro.results.main.actions.report;

import net.orpiske.maestro.results.dao.ReportsDao;
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
        ReportsDao reportsDao = new ReportsDao();

        List<TestResultRecord> testResultRecords = reportsDao.protocolReports("A-MQ", "7.0.3",
                true, 1, 100, 1);


        for (TestResultRecord testResultRecord : testResultRecords) {
            System.out.println("Record = " + testResultRecord);
        }

        Map<String, Object> context = new HashMap<>();

        context.put("testResultRecords", testResultRecords);

        ResultsReportRenderer resultsReportRenderer = new ResultsReportRenderer(context);

        try {
            File outDir = new File(output);
            File outFile = new File(outDir, "index.html");

            outDir.mkdirs();
            FileUtils.writeStringToFile(outFile, resultsReportRenderer.render(), Charsets.UTF_8);

            resultsReportRenderer.copyResources(outDir);

            ReportDataPlotter rdp = new ReportDataPlotter();

            rdp.buildChart("", "Protocol", "Messages p/ second", testResultRecords, "report-7.0.3.png");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
