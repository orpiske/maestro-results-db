package net.orpiske.maestro.results.main.actions.record;

import net.orpiske.maestro.results.dao.TestDao;
import net.orpiske.maestro.results.dto.Test;
import net.orpiske.maestro.results.main.Action;
import org.apache.commons.cli.*;

import java.util.List;

import static net.orpiske.maestro.results.main.actions.record.utils.PrintUtils.printCreatedKey;

public class TestAction extends Action {
    private CommandLine cmdLine;
    private Options options;

    public TestAction(String[] args) {
        processCommand(args);
    }

    @Override
    protected void processCommand(String[] args) {
        CommandLineParser parser = new PosixParser();

        options = new Options();

        options.addOption("h", "help", false, "prints the help");
        options.addOption("a", "action", true, "action (one of: insert, delete, update, view)");
        options.addOption("i", "id", true, "test id");
        options.addOption("n", "test-name", true, "test name");
        options.addOption("r", "test-result", true, "test result (one of: success, failed, error)");
        options.addOption("p", "test-parameter-id", true, "test parameter id");
        options.addOption("s", "sut-id", true, "sut id");
        options.addOption("l", "test-report-link", true, "test report link");
        options.addOption("d", "test-data-storage-info", true, "test data storage info");
        options.addOption("t", "test-tags", true, "test tags");
        options.addOption("d", "test-duration", true, "test duration (in seconds)");
        options.addOption("R", "test-target-rate", true, "test target rate");

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
        TestDao dao = new TestDao();
        Test dto = new Test();

        dto.setTestName(cmdLine.getOptionValue("test-name"));
        dto.setTestResult(cmdLine.getOptionValue("test-result"));
        dto.setTestParameterId(Integer.parseInt(cmdLine.getOptionValue("test-parameter-id")));
        dto.setSutId(Integer.parseInt(cmdLine.getOptionValue("sut-id")));
        dto.setTestReportLink(cmdLine.getOptionValue("test-report-link"));
        dto.setTestDataStorageInfo(cmdLine.getOptionValue("test-data-storage-info"));
        dto.setTestTags(cmdLine.getOptionValue("test-tags"));
        dto.setTestDuration(Integer.parseInt(cmdLine.getOptionValue("test-duration")));
        dto.setTestTargetRate(Integer.parseInt(cmdLine.getOptionValue("test-target-rate")));

        dao.insert(dto);
        return 0;
    }

    private int view() {
        TestDao dao = new TestDao();
        List<Test> failConditions = dao.fetch();

        failConditions.stream().forEach(item -> System.out.println("Test: " + item));
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
                printCreatedKey("test", add());
                break;
            }
            case "view": {
                return view();
            }
        }

        return 2;
    }
}
