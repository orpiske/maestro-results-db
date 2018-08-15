package org.maestro.results.server.main;

import io.javalin.Javalin;
import org.apache.commons.configuration.AbstractConfiguration;
import org.apache.commons.configuration.ConfigurationException;
import org.maestro.common.ConfigurationWrapper;
import org.maestro.common.Constants;
import org.maestro.common.LogConfigurator;
import org.maestro.results.server.controller.env.resources.AllEnvResourcesController;
import org.maestro.results.server.controller.env.results.AllEnvResultsController;
import org.maestro.results.server.controllers.sut.AllSutsController;
import org.maestro.results.server.controllers.sut.SutController;

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

        AbstractConfiguration config = ConfigurationWrapper.getConfig();

        final int port = config.getInteger("maestro.results.server", 7000);

        Javalin app = Javalin.create()
                .port(port)
                .enableStaticFiles("/site")
                .enableCorsForAllOrigins()
                .disableStartupBanner()
                .start();

        app.get("/api/live", ctx -> ctx.result("Hello World"));
        app.get("/api/sut/", new AllSutsController());
        app.get("/api/sut/:id", new SutController());
        app.get("/api/env/resource", new AllEnvResourcesController());
        app.get("/api/env/results", new AllEnvResultsController());
    }

}
