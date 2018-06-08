package org.maestro.results.main.actions.report;

import org.maestro.results.main.Action;
import org.apache.commons.cli.*;

public class ReportAllAction extends Action {
    private CommandLine cmdLine;
    private Options options;
    private String output;
    private String testName;

    public ReportAllAction(final String[] args) {
        processCommand(args);
    }

    @Override
    protected void processCommand(final String[] args) {
        CommandLineParser parser = new DefaultParser();

        options = new Options();

        options.addOption("h", "help", false, "prints the help");
        options.addOption("o", "output", true, "output directory");
        options.addOption("t", "test-name", true, "test name [Release, Baseline - defaults to Baseline]");

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

        testName = cmdLine.getOptionValue('t');
        if (testName == null) {
            testName = "Baseline";
        }
    }

    @Override
    public int run() {
        Report reportRunner = new Report(output, testName);

        reportRunner.createReport();

        return 0;
    }
}
