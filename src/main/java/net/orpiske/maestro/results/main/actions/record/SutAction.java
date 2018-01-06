package net.orpiske.maestro.results.main.actions.record;

import net.orpiske.maestro.results.dao.SutDao;
import net.orpiske.maestro.results.dao.TestMsgPropertyDao;
import net.orpiske.maestro.results.dto.Sut;
import net.orpiske.maestro.results.dto.TestMsgProperty;
import net.orpiske.maestro.results.main.Action;
import org.apache.commons.cli.*;

import java.util.List;

import static net.orpiske.maestro.results.main.actions.record.utils.PrintUtils.printCreatedKey;

public class SutAction extends Action {
    private CommandLine cmdLine;
    private Options options;

    public SutAction(String[] args) {
        processCommand(args);
    }

    @Override
    protected void processCommand(String[] args) {
        CommandLineParser parser = new PosixParser();

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

        dao.insert(dto);
        return 0;
    }

    private int view() {
        SutDao dao = new SutDao();
        List<Sut> failConditions = dao.fetch();

        failConditions.stream().forEach(item -> System.out.println("SUT: " + item));
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
