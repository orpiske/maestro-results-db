package org.maestro.results.main.actions.record;

import org.maestro.reports.dao.exceptions.DataNotFoundException;
import org.maestro.results.dao.TestMsgPropertyDao;
import org.maestro.results.dto.TestMsgProperty;
import org.maestro.results.main.Action;
import org.apache.commons.cli.*;

import java.util.List;

import static org.maestro.results.main.actions.record.utils.PrintUtils.printCreatedRecord;


public class TestMsgPropertyAction extends Action {
    private CommandLine cmdLine;
    private Options options;

    public TestMsgPropertyAction(String[] args) {
        processCommand(args);
    }

    @Override
    protected void processCommand(String[] args) {
        CommandLineParser parser = new DefaultParser();

        options = new Options();

        options.addOption("h", "help", false, "prints the help");
        options.addOption("a", "action", true, "action (one of: insert, delete, update, view)");
        options.addOption("i", "test-id", true, "test parameter id");
        options.addOption("n", "test-msg-property-name", true, "test message property name");
        options.addOption("v", "test-msg-property-value", true, "test message property value");

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

    private int add() {
        TestMsgPropertyDao dao = new TestMsgPropertyDao();
        TestMsgProperty tc = new TestMsgProperty();

        int parameterId = Integer.parseInt(cmdLine.getOptionValue("test-id"));
        tc.setTestId(parameterId);

        final String failConditionName = cmdLine.getOptionValue("test-msg-property-name");
        tc.setTestMsgPropertyName(failConditionName);

        final String failConditionValue = cmdLine.getOptionValue("test-msg-property-value");
        tc.setTestMsgPropertyValue(failConditionValue);

        dao.insert(tc);
        return 0;
    }

    private int view() {
        try {
            TestMsgPropertyDao dao = new TestMsgPropertyDao();
            List<TestMsgProperty> failConditions = dao.fetch();

            failConditions.stream().forEach(item -> System.out.println("Test message property: " + item));
            return 0;
        }
        catch (DataNotFoundException e) {
            System.err.println("Data not found");
            return 1;
        }
    }

    @Override
    public int run() {
        if (!cmdLine.hasOption("action")) {
            System.err.println("An action is required");

            return 1;
        }

        final String action = cmdLine.getOptionValue("action");

        switch (action) {
            case "insert": {
                add();
                printCreatedRecord("test message property");
                break;
            }
            case "view": {
                return view();
            }
        }

        return 2;
    }
}
