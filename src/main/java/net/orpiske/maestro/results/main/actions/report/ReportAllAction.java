package net.orpiske.maestro.results.main.actions.report;

import net.orpiske.maestro.results.main.Action;
import org.apache.commons.cli.*;

public class ReportAllAction extends Action {
    private CommandLine cmdLine;
    private Options options;
    private String output;

    public ReportAllAction(final String[] args) {
        processCommand(args);
    }

    @Override
    protected void processCommand(final String[] args) {
        CommandLineParser parser = new DefaultParser();

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
        Report reportRunner = new Report(output);

        reportRunner.createReport();

        return 0;
    }
}
