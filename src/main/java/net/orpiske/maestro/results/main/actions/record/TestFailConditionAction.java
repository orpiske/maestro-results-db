package net.orpiske.maestro.results.main.actions.record;

import net.orpiske.maestro.results.dao.TestFailConditionDao;
import net.orpiske.maestro.results.dto.TestFailCondition;
import net.orpiske.maestro.results.main.Action;
import org.apache.commons.cli.*;

import java.util.List;

import static net.orpiske.maestro.results.main.actions.record.utils.PrintUtils.printCreatedRecord;

public class TestFailConditionAction extends Action {
    private CommandLine cmdLine;
    private Options options;

    public TestFailConditionAction(String[] args) {
        processCommand(args);
    }

    @Override
    protected void processCommand(String[] args) {
        CommandLineParser parser = new PosixParser();

        options = new Options();

        options.addOption("h", "help", false, "prints the help");
        options.addOption("a", "action", true, "action (one of: insert, delete, update, view)");
        options.addOption("i", "test-parameter-id", true, "test parameter id");
        options.addOption("n", "test-fail-condition-name", true, "test fail condition name");
        options.addOption("v", "test-fail-condition-value", true, "test fail condition value");

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
        TestFailConditionDao dao = new TestFailConditionDao();
        TestFailCondition tc = new TestFailCondition();

        int parameterId = Integer.parseInt(cmdLine.getOptionValue("test-parameter-id"));
        tc.setTestParameterId(parameterId);

        final String failConditionName = cmdLine.getOptionValue("test-fail-condition-name");
        tc.setTestFailConditionName(failConditionName);

        final String failConditionValue = cmdLine.getOptionValue("test-fail-condition-value");
        tc.setTestFailConditionValue(failConditionValue);

        dao.insert(tc);
        return 0;
    }

    private int view() {
        TestFailConditionDao dao = new TestFailConditionDao();
        List<TestFailCondition> failConditions = dao.fetch();

        failConditions.stream().forEach(item -> System.out.println("Fail condition: " + item));
        return 0;
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
                printCreatedRecord("test fail condition");
                break;
            }
            case "view": {
                return view();
            }
        }

        return 2;
    }
}
