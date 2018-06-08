package org.maestro.results.main.actions.record;

import org.maestro.results.dao.EnvResourceDao;
import org.maestro.results.dto.EnvResource;
import org.maestro.results.main.Action;
import org.apache.commons.cli.*;

import java.util.List;

import static org.maestro.results.main.actions.record.utils.PrintUtils.printCreatedKey;

public class EnvironmentAction extends Action {
    private CommandLine cmdLine;
    private Options options;

    public EnvironmentAction(String[] args) {
        processCommand(args);
    }

    @Override
    protected void processCommand(String[] args) {
        CommandLineParser parser = new DefaultParser();

        options = new Options();

        options.addOption("h", "help", false, "prints the help");
        options.addOption("a", "action", true, "action (one of: insert, view)");
        options.addOption("i", "id", true, "env resource id");
        options.addOption(null, "resource-name", true, "env resource name");
        options.addOption(null, "os-name", true, "env resource os name");
        options.addOption(null, "os-arch", true, "env resource os arch");
        options.addOption(null, "os-version", true, "env resource os version");
        options.addOption(null, "os-other", true, "other os version");
        options.addOption(null, "hw-name", true, "env hardware name");
        options.addOption(null, "hw-model", true, "env hardware model");
        options.addOption(null, "hw-cpu", true, "env hardware cpu");
        options.addOption(null, "hw-ram", true, "env hardware ram");
        options.addOption(null, "hw-disk-type", true, "env hardware disk type [hd, ssd]");
        options.addOption(null, "hw-other", true, "other hardware information");
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
        EnvResourceDao dao = new EnvResourceDao();
        EnvResource dto = new EnvResource();

        dto.setEnvResourceName(cmdLine.getOptionValue("resource-name"));
        dto.setEnvResourceOsName(cmdLine.getOptionValue("os-name"));
        dto.setEnvResourceOsArch(cmdLine.getOptionValue("os-arch"));
        dto.setEnvResourceOsVersion(cmdLine.getOptionValue("os-version"));
        dto.setEnvResourceOsOther(cmdLine.getOptionValue("os-other"));
        dto.setEnvResourceHwName(cmdLine.getOptionValue("hw-name"));
        dto.setEnvResourceHwModel(cmdLine.getOptionValue("hw-model"));
        dto.setEnvResourceHwCpu(cmdLine.getOptionValue("hw-cpu"));
        dto.setEnvResourceHwRam(Integer.parseInt(cmdLine.getOptionValue("hw-ram")));
        dto.setEnvResourceHwDiskType(cmdLine.getOptionValue("hw-disk-type"));
        dto.setEnvResourceHwOther(cmdLine.getOptionValue("hw-other"));

        return dao.insert(dto);
    }

    private int view() {
        EnvResourceDao dao = new EnvResourceDao();
        List<EnvResource> resources = dao.fetch();

        resources.stream().forEach(item -> System.out.println("Environment resources: " + item));
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
                printCreatedKey("environment resource", add());
                break;
            }
            case "view": {
                return view();
            }
        }

        return 2;
    }
}
