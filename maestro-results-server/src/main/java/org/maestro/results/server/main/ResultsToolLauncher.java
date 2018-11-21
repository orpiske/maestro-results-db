package org.maestro.results.server.main;

import org.maestro.reports.server.ReportsServer;
import org.maestro.reports.server.main.DefaultToolLauncher;
import org.maestro.results.server.collector.ExtendedCollector;

import java.io.File;
import java.util.concurrent.CountDownLatch;

public class ResultsToolLauncher extends DefaultToolLauncher {

    public ResultsToolLauncher(File dataDir, boolean offline, String maestroUrl, String host) {
        super(dataDir, offline, maestroUrl, host);
    }

    @Override
    protected void startCollector(final CountDownLatch latch) {
        super.startCollector(latch, new ExtendedCollector(getMaestroUrl(), getPeerInfo(), getDataDir()));
    }

    @Override
    protected ReportsServer startServer(final CountDownLatch latch) {
        ExtendedReportsServer reportsServer = new ExtendedReportsServer();

        super.startServer(latch, reportsServer);

        return reportsServer;
    }
}
