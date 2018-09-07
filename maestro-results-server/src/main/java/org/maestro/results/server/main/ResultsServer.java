package org.maestro.results.server.main;

import io.javalin.Javalin;
import org.apache.commons.configuration.AbstractConfiguration;
import org.apache.commons.configuration.ConfigurationException;
import org.maestro.common.ConfigurationWrapper;
import org.maestro.common.Constants;
import org.maestro.common.LogConfigurator;
import org.maestro.results.server.controller.env.resources.AllEnvResourcesController;
import org.maestro.results.server.controller.env.results.AllEnvResultsController;
import org.maestro.results.server.controller.test.AllTestsControlller;
import org.maestro.results.server.controller.test.SingleTestControlller;
import org.maestro.results.server.controller.test.SingleTestIterationController;
import org.maestro.results.server.controller.test.TestResourcesController;
import org.maestro.results.server.controller.test.results.*;
import org.maestro.results.server.controllers.sut.AllSutsController;
import org.maestro.results.server.controllers.sut.SutController;
import org.maestro.results.server.controllers.sut.TestSutControlller;

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
        app.get("/api/test", new AllTestsControlller());
        app.get("/api/test/:id", new SingleTestControlller());
        app.get("/api/test/:id/sut", new TestSutControlller());
        app.get("/api/test/:id/resources", new TestResourcesController());
        app.get("/api/test/:id/number/:number", new SingleTestIterationController());
        app.get("/api/results/", new AllTestsResultsController());
        app.get("/api/results/test/:id", new SingleTestResultsController());
        app.get("/api/results/latency/test/:id", new LatencyDistributionByTestController());
        app.get("/api/results/statistics/test/:id", new TestResultsStatisticsController());
        app.get("/api/results/rate/:role/test/:id", new RateDistributionByTestController());
    }

}
