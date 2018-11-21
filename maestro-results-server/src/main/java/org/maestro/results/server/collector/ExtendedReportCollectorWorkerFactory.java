package org.maestro.results.server.collector;

import org.maestro.client.MaestroReceiverClient;
import org.maestro.common.client.notes.Test;
import org.maestro.reports.server.collector.ReportCollectorWorker;
import org.maestro.reports.server.collector.ReportCollectorWorkerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class ExtendedReportCollectorWorkerFactory implements ReportCollectorWorkerFactory {
    private static final Logger logger = LoggerFactory.getLogger(ExtendedReportCollectorWorkerFactory.class);

    @Override
    public ReportCollectorWorker newWorker(File dataDir, MaestroReceiverClient client, Test test) {
        logger.debug("Creating a new extended collector worker for test {}", test);

        return new ExtendedReportCollectorWorker(dataDir, client, test);
    }
}
