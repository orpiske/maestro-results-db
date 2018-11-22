package org.maestro.results.server.main;


import io.javalin.Javalin;
import org.apache.commons.configuration.AbstractConfiguration;
import org.maestro.common.ConfigurationWrapper;
import org.maestro.reports.server.DefaultReportsServer;
import org.maestro.results.server.controller.env.resources.AllEnvResourcesController;
import org.maestro.results.server.controller.env.results.AllEnvResultsController;
import org.maestro.results.server.controller.filters.TestSutPropertiesController;
import org.maestro.results.server.controller.test.*;
import org.maestro.results.server.controller.test.results.*;
import org.maestro.results.server.controllers.compare.TestIterationComparatorController;
import org.maestro.results.server.controllers.compare.TestPercentilesComparatorController;
import org.maestro.results.server.controllers.compare.TestRateComparatorController;
import org.maestro.results.server.controllers.sut.AllSutsController;
import org.maestro.results.server.controllers.sut.SutController;
import org.maestro.results.server.controllers.sut.TestSutController;

public class ExtendedReportsServer extends DefaultReportsServer {
    private static final AbstractConfiguration config = ConfigurationWrapper.getConfig();
    private final Javalin app;

    public ExtendedReportsServer() {
        super();

        app = getServerInstance()
                .enableStaticFiles("/site-extra")
                .enableCorsForAllOrigins()
                .disableStartupBanner();

        registerUris();
    }

    protected void registerUris() {
        app.get("/api/sut/", new AllSutsController());
        app.get("/api/sut/:id", new SutController());
        app.get("/api/env/resource", new AllEnvResourcesController());
        app.get("/api/env/results", new AllEnvResultsController());
        app.get("/api/test", new AllTestsControlller());
        app.get("/api/test/:id", new SingleTestControlller());
        app.get("/api/test/:id/sut", new TestSutController());
        app.get("/api/test/:id/resources", new TestResourcesController());
        app.get("/api/test/:id/properties", new TestPropertiesController());
        app.get("/api/test/:id/number/:number", new SingleTestIterationController());
        app.get("/api/test/:id/number/:number/properties", new SingleTestPropertiesController());
        app.get("/api/results/", new AllTestsResultsController());
        app.get("/api/results/test/:id", new SingleTestResultsController());
        app.get("/api/results/latency/test/:id", new LatencyDistributionByTestController());
        app.get("/api/results/statistics/test/:id", new TestResultsStatisticsController());
        app.get("/api/results/rate/:role/test/:id", new RateDistributionByTestController());
        app.get("/api/results/test/sut/properties", new TestSutPropertiesController());
        app.get("/api/compare/results/full/:t0/:n0/:t1/:n1", new TestIterationComparatorController());
        app.get("/api/compare/results/percentiles/:t0/:n0/:t1/:n1", new TestPercentilesComparatorController());
        app.get("/api/compare/results/rate/:role/:t0/:n0/:t1/:n1", new TestRateComparatorController());
    }

    @Override
    public void start() {
        super.start();

        app.start();
    }
}
