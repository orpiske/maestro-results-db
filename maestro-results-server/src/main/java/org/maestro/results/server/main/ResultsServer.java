package org.maestro.results.server.main;

import org.maestro.common.ConfigurationWrapper;
import org.maestro.common.Constants;
import org.maestro.common.LogConfigurator;
import org.maestro.reports.server.main.ReportsTool;


public class ResultsServer extends ReportsTool {
    static {
        LogConfigurator.defaultForDaemons();
    }

    public ResultsServer(String[] args) {
        super(args);
    }

    protected int run() {
        return run(new ResultsToolLauncher(getDataDir(), isOffline(), getMaestroUrl(), getHost()));
    }

    /**
     * Running this as a debug is something like:
     * java -m mqtt://maestro-broker:1883
     *      -d /storage/data
     */
    public static void main(String[] args) {
        try {
            ConfigurationWrapper.initConfiguration(Constants.MAESTRO_CONFIG_DIR, "maestro-results-server.properties");
        } catch (Exception e) {
            System.err.println("Unable to initialize configuration file: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }

        ResultsServer reportsTool = new ResultsServer(args);

        System.exit(reportsTool.run());
    }

}
