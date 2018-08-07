package org.maestro.results.main.actions.xunit;

import org.apache.commons.cli.*;
import org.maestro.results.main.Action;

import java.io.File;

public class ExportAction extends Action {
    private CommandLine cmdLine;
    private Options options;
    private String output;
    private int testId;

    public ExportAction(final String[] args) {
        processCommand(args);
    }

    @Override
    protected void processCommand(final String[] args) {
        CommandLineParser parser = new DefaultParser();

        options = new Options();

        options.addOption("h", "help", false, "prints the help");
        options.addOption("o", "output", true, "output directory");
        options.addOption("t", "test-id", true, "test name [Release, Baseline - defaults to Baseline]");

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

        String strTestId = cmdLine.getOptionValue('t');
        if (strTestId == null) {
            System.err.println("Test ID is mandatory. Use option '-t' to inform it");
            help(options, -1);
        }

        testId = Integer.parseInt(strTestId);
    }

    @Override
    public int run() {
        File outputFile = new File(output, "maestro.xunit.xml");

        XUnitGenerator xUnitGenerator = new XUnitGenerator();

        xUnitGenerator.generate(outputFile, testId);

        return 0;
    }
}
