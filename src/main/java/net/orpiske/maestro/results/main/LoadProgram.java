package net.orpiske.maestro.results.main;

import net.orpiske.maestro.results.main.actions.load.ReportAction;

import static java.util.Arrays.copyOfRange;

public class LoadProgram implements Program {

    /**
     * Prints the help for the action and exit
     * @param code the exit code
     */
    private void help(int code) {
        System.out.println("Usage: maestro-cli <action>\n");

        System.out.println("Actions:");
        System.out.println("   report");

        System.out.println("----------");
        System.out.println("   help");

        System.exit(code);
    }

    @Override
    public int run(String[] args) {
        if (args.length == 0) {
            System.err.println("The action is missing!");
            help(1);
        }

        String first = args[0];
        String[] newArgs = copyOfRange(args, 1, args.length);

        if (first.equals("help")) {
            help(1);
        }

        Action action;
        switch (first) {
            case "report": {
                action = new ReportAction(newArgs);
                break;
            }
            default: {
                help(1);
                return 0;
            }
        }

        return action.run();
    }
}
