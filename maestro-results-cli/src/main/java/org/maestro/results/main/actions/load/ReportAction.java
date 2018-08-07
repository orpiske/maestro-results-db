package org.maestro.results.main.actions.load;

import org.maestro.results.dto.Test;
import org.maestro.results.main.Action;
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
        CommandLineParser parser = new DefaultParser();

        options = new Options();

        options.addOption("h", "help", false, "prints the help");
        options.addOption(null, "sut-id", true, "sut id");
        options.addOption(null, "env-name", true, "env-name");
        options.addOption(null, "test-name", true, "test name");
        options.addOption(null, "test-result", true, "test result");
        options.addOption(null, "test-report-link", true, "test report link");
        options.addOption(null, "test-tags", true, "comma-separated test tag lists");
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
        System.out.println("Recursively loading files from " + directory);

        Test test = new Test();

        test.setTestName(cmdLine.getOptionValue("test-name"));
        String testResult = cmdLine.getOptionValue("test-result");
        if (testResult != null) {
            test.setTestResult(testResult);
        }

        String testTags = cmdLine.getOptionValue("test-tags");
        if (testTags != null) {
            test.setTestTags(testTags);
        }

        String testReportLink = cmdLine.getOptionValue("test-report-link");
        if (testReportLink != null) {
            test.setTestReportLink(testReportLink);
        }

        String testDataStorageInfo = cmdLine.getOptionValue("test-data-storage-info");
        if (testDataStorageInfo != null) {
            test.setTestDataStorageInfo(testDataStorageInfo);
        }

        test.setSutId(Integer.parseInt(cmdLine.getOptionValue("sut-id")));

        String envName = cmdLine.getOptionValue("env-name");
        ReportLoader rl = new ReportLoader(test, envName);

        rl.loadFiles(directory);
        return 0;
    }
}
