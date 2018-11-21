package org.maestro.results.server.main;

import org.apache.commons.configuration.ConfigurationException;
import org.maestro.common.ConfigurationWrapper;
import org.maestro.common.Constants;
import org.maestro.common.LogConfigurator;

import java.io.FileNotFoundException;

public class ResultsServer {
    static {
        LogConfigurator.defaultForDaemons();
    }

    public static void main(String[] args) {

        try {
            ConfigurationWrapper.initConfiguration(Constants.MAESTRO_CONFIG_DIR, "maestro-results-server.properties");
        } catch (FileNotFoundException e) {
            System.out.println("The server configuration file was not found");
            System.exit(1);
        } catch (ConfigurationException e) {
            System.out.println("The server configuration file is invalid");
            System.exit(2);
        }

        ExtendedReportsServer reportsServer = new ExtendedReportsServer();

        reportsServer.start();
    }

}
