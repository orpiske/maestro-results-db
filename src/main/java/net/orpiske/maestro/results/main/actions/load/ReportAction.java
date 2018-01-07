package net.orpiske.maestro.results.main.actions.load;

import net.orpiske.maestro.results.dto.Test;
import net.orpiske.maestro.results.main.Action;
import org.apache.commons.cli.*;

import java.io.File;

public class ReportAction extends Action {
    private CommandLine cmdLine;
    private Options options;

    public ReportAction(String[] args) {
        processCommand(args);
    }

    @Override
    protected void processCommand(String[] args) {
        CommandLineParser parser = new PosixParser();

        options = new Options();

        options.addOption("h", "help", false, "prints the help");
        options.addOption(null, "sut-id", true, "sut id");
        options.addOption(null, "env-name", true, "env-name");
        options.addOption(null, "test-name", true, "test name");
        options.addOption(null, "test-result", true, "test result");
        options.addOption(null, "path", true, "initial path" );

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
        File directory = new File(cmdLine.getOptionValue("path"));
        System.out.println("Loading files from " + directory);

        Test test = new Test();

        test.setTestName(cmdLine.getOptionValue("test-name"));
        String testResult = cmdLine.getOptionValue("test-result");
        if (testResult != null) {
            test.setTestResult(testResult);
        }

        test.setSutId(Integer.parseInt(cmdLine.getOptionValue("sut-id")));

        String envName = cmdLine.getOptionValue("env-name");
        ReportLoader rl = new ReportLoader(test, envName);

        rl.loadFiles(directory);
        return 0;
    }
}
