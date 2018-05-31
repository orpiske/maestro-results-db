package net.orpiske.maestro.results.main.actions.data;

import net.orpiske.maestro.results.main.Action;
import org.apache.commons.cli.*;

public class DataMove extends Action {
    private CommandLine cmdLine;
    private Options options;

    public DataMove(final String[] args) {
        processCommand(args);
    }

    @Override
    protected void processCommand(String[] args) {
        CommandLineParser parser = new DefaultParser();

        options = new Options();

        options.addOption("h", "help", false, "prints the help");
        options.addOption(null, "sut-id", true, "sut id");

        try {
            cmdLine = parser.parse(options, args);
        } catch (ParseException e) {
            System.err.println(e.getMessage());
            help(options, -1);
        }

        if (cmdLine.hasOption("help")) {
            help(options, 0);
        }
    }

    @Override
    public int run() {
        return 0;
    }
}
