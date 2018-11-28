package org.maestro.results.main.actions.data;

import org.maestro.results.main.Action;
import org.apache.commons.cli.*;

public class DataMove extends Action {
    private CommandLine cmdLine;
    private Options options;

    private String from;
    private String to;
    private String withDownloadPath;
    private String withTmpPath;
    private boolean dynamicNaming;
    public String testName;
    private int initialId = 0;
    private int finalId = 0;

    public DataMove(final String[] args) {
        processCommand(args);
    }

    @Override
    protected void processCommand(String[] args) {
        CommandLineParser parser = new DefaultParser();

        options = new Options();

        options.addOption("h", "help", false, "prints the help");
        options.addOption("f", "from", true, "from URL");
        options.addOption("t", "to", true, "to url");
        options.addOption(null, "test-name", true, "Update records only for [test-name]");
        options.addOption(null, "with-download-path", true, "download path for the report to be appended to the report URL");
        options.addOption(null, "with-tmp-path", true, "temp path for the downloaded reports");
        options.addOption(null, "with-test-id-range", true, "test id range (in the form initial:final)");
        options.addOption(null, "dynamic-naming", false, "build the directory tree automagically");

        try {
            cmdLine = parser.parse(options, args);
        } catch (ParseException e) {
            System.err.println(e.getMessage());
            help(options, -1);
        }

        if (cmdLine.hasOption("help")) {
            help(options, 0);
        }

        from = cmdLine.getOptionValue("from");
        if (from == null) {
            System.err.println("The 'from' URL is a required parameter");
            help(options, -1);
        }

        to = cmdLine.getOptionValue("to");
        if (to == null) {
            System.err.println("The 'to' URL is a required parameter");
            help(options, -1);
        }

        withDownloadPath = cmdLine.getOptionValue("with-download-path");
        withTmpPath = cmdLine.getOptionValue("with-tmp-path");
        dynamicNaming = cmdLine.hasOption("dynamic-naming");
        testName = cmdLine.getOptionValue("test-name");
        String idRange = cmdLine.getOptionValue("with-test-id-range");
        if (idRange != null) {
            String tmpInitialId = idRange.split(":")[0];
            String tmpFinalId = idRange.split(":")[1];

            initialId = Integer.parseInt(tmpInitialId);
            finalId = Integer.parseInt(tmpFinalId);
        }
    }

    @Override
    public int run() {
        try {
            DataMover mover = new DataMover();

            mover.setDownloadPath(withDownloadPath);
            mover.setTmpPath(withTmpPath);
            mover.setDynamicNaming(dynamicNaming);

            if (testName == null) {
                mover.move(from, to);
            }
            else {
                if (finalId != initialId && finalId > initialId) {
                    mover.move(from, to, initialId, finalId, testName);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            return 1;
        }

        return 0;
    }
}
