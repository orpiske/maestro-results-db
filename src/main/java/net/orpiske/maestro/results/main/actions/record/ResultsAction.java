package net.orpiske.maestro.results.main.actions.record;

import net.orpiske.maestro.results.dao.EnvResultsDao;
import net.orpiske.maestro.results.dto.EnvResults;
import net.orpiske.maestro.results.main.Action;
import org.apache.commons.cli.*;

import java.util.List;

import static net.orpiske.maestro.results.main.actions.record.utils.PrintUtils.printCreatedKey;

public class ResultsAction extends Action {
    private CommandLine cmdLine;
    private Options options;

    public ResultsAction(String[] args) {
        processCommand(args);
    }

    @Override
    protected void processCommand(String[] args) {
        CommandLineParser parser = new DefaultParser();

        options = new Options();

        options.addOption("h", "help", false, "prints the help");
        options.addOption("a", "action", true, "action (one of: insert, delete, update, view)");
        options.addOption("i", "id", true, "env results id");
        options.addOption(null, "env-resource-id", true, "env resource id");
        options.addOption(null, "test-id", true, "test id");
        options.addOption(null, "env-name", true, "sut name");
        options.addOption(null, "env-resource-role", true, "env resource role (one of: sender, receiver, inspector or other)" );
        options.addOption(null, "test-rate-min", true, "minimum rate achieved during the test");
        options.addOption(null, "test-rate-max", true, "maximum rate achieved during the test");
        options.addOption(null, "test-rate-error-count", true, "test rate error count");
        options.addOption(null, "test-rate-samples", true, "test rate samples");
        options.addOption(null, "test-rate-geometric-mean", true, "test rate geometric mean");
        options.addOption(null, "test-rate-standard-deviation", true, "test rate standard deviation");
        options.addOption(null, "test-rate-skip-count", true, "test rate skip count");
        options.addOption(null, "error", true, "test error flag (true of false)");

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
        EnvResultsDao dao = new EnvResultsDao();
        EnvResults dto = new EnvResults();

        dto.setEnvName(cmdLine.getOptionValue("env-name"));
        dto.setEnvResourceId(Integer.parseInt(cmdLine.getOptionValue("env-resource-id")));
        dto.setTestId(Integer.parseInt(cmdLine.getOptionValue("test-id")));
        dto.setEnvResourceRole(cmdLine.getOptionValue("env-resource-role"));
        dto.setTestRateMin(Integer.parseInt(cmdLine.getOptionValue("test-rate-min")));
        dto.setTestRateMax(Integer.parseInt(cmdLine.getOptionValue("test-rate-max")));
        dto.setTestRateErrorCount(Integer.parseInt(cmdLine.getOptionValue("test-rate-error-count")));
        dto.setTestRateSamples(Integer.parseInt(cmdLine.getOptionValue("test-rate-samples")));
        dto.setTestRateGeometricMean(Double.parseDouble(cmdLine.getOptionValue("test-rate-geometric-mean")));
        dto.setTestRateStandardDeviation(Double.parseDouble(cmdLine.getOptionValue("test-rate-standard-deviation")));
        dto.setTestRateSkipCount(Integer.parseInt(cmdLine.getOptionValue("test-rate-skip-count")));

        String errorFlag = cmdLine.getOptionValue("error");
        if (errorFlag == null || errorFlag.toLowerCase().equals("false")) {
            dto.setError(false);
        }
        else {
            if (errorFlag.toLowerCase().equals("true")) {
                dto.setError(true);
            }
            else {
                throw new RuntimeException("Invalid flag: " + errorFlag);
            }
        }

        dao.insert(dto);
        return 0;
    }

    private int view() {
        EnvResultsDao dao = new EnvResultsDao();
        List<EnvResults> failConditions = dao.fetch();

        failConditions.stream().forEach(item -> System.out.println("Env results: " + item));
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
                printCreatedKey("results", add());
                break;
            }
            case "view": {
                return view();
            }
        }

        return 2;
    }
}
