package org.maestro.results.main.actions.record;

import org.maestro.reports.dao.exceptions.DataNotFoundException;
import org.maestro.results.dao.SutDao;
import org.maestro.results.dto.Sut;
import org.maestro.results.main.Action;
import org.apache.commons.cli.*;

import java.util.List;

import static org.maestro.results.main.actions.record.utils.PrintUtils.printCreatedKey;

public class SutAction extends Action {
    private CommandLine cmdLine;
    private Options options;

    public SutAction(String[] args) {
        processCommand(args);
    }

    @Override
    protected void processCommand(String[] args) {
        CommandLineParser parser = new DefaultParser();

        options = new Options();

        options.addOption("h", "help", false, "prints the help");
        options.addOption("a", "action", true, "action (one of: insert, delete, update, view)");
        options.addOption("i", "id", true, "sut id");
        options.addOption("n", "sut-name", true, "sut name");
        options.addOption("v", "sut-version", true, "sut version");
        options.addOption("j", "sut-jvm-info", true, "sut jvm information");
        options.addOption("o", "sut-other", true, "other sut information");
        options.addOption("t", "sut-tags", true, "sut tags (comma separated list)");

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
        SutDao dao = new SutDao();
        Sut dto = new Sut();

        dto.setSutName(cmdLine.getOptionValue("sut-name"));
        dto.setSutVersion(cmdLine.getOptionValue("sut-version"));
        dto.setSutJvmInfo(cmdLine.getOptionValue("sut-jvm-info"));
        dto.setSutOther(cmdLine.getOptionValue("sut-other"));
        dto.setSutTags(cmdLine.getOptionValue("sut-tags"));

        return dao.insert(dto);
    }

    private int view() {
        SutDao dao = new SutDao();
        try {
            List<Sut> failConditions = dao.fetch();

            failConditions.stream().forEach(item -> System.out.println("SUT: " + item));
            return 0;
        } catch (DataNotFoundException e) {
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
                printCreatedKey("SUT", add());
                break;
            }
            case "view": {
                return view();
            }
        }

        return 2;
    }
}
