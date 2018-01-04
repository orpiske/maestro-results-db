package net.orpiske.maestro.results.main.actions.record;

import net.orpiske.maestro.results.dao.TestFailConditionDao;
import net.orpiske.maestro.results.dao.TestParameterDao;
import net.orpiske.maestro.results.dto.TestFailCondition;
import net.orpiske.maestro.results.dto.TestParameter;
import net.orpiske.maestro.results.main.Action;
import org.apache.commons.cli.*;

import java.util.List;

public class TestParameterAction extends Action {
    private CommandLine cmdLine;
    private Options options;

    public TestParameterAction(String[] args) {
        processCommand(args);
    }

    @Override
    protected void processCommand(String[] args) {
        CommandLineParser parser = new PosixParser();

        options = new Options();

        options.addOption("h", "help", false, "prints the help");
        options.addOption("a", "action", true, "action (one of: insert, delete, update, view)");
        options.addOption("i", "id", true, "test parameter id");
        options.addOption("t", "rate", true, "test target rate");
        options.addOption("s", "sender-count", true, "test sender count");
        options.addOption("r", "receiver-count", true, "test receiver count");

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
        TestParameterDao dao = new TestParameterDao();
        TestParameter dto = new TestParameter();

        int parameterId = Integer.parseInt(cmdLine.getOptionValue("id"));
        dto.setTestParameterId(parameterId);

        int rate = Integer.parseInt(cmdLine.getOptionValue("rate"));
        dto.setTestTargetRate(rate);

        int senderCount = Integer.parseInt(cmdLine.getOptionValue("sender-count"));
        dto.setTestSenderCount(senderCount);

        int receiverCount = Integer.parseInt(cmdLine.getOptionValue("receiver-count"));
        dto.setTestReceiverCount(receiverCount);

        dao.insert(dto);
        return 0;
    }

    private int view() {
        TestParameterDao dao = new TestParameterDao();
        List<TestParameter> parameters = dao.fetch();

        parameters.stream().forEach(item -> System.out.println("Test parameters: " + item));
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
                return add();
            }
            case "view": {
                return view();
            }
        }

        return 2;
    }
}
