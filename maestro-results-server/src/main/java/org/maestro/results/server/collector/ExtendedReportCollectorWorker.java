package org.maestro.results.server.collector;

import org.maestro.client.MaestroReceiverClient;
import org.maestro.common.client.notes.Test;
import org.maestro.common.client.notes.TestExecutionInfo;
import org.maestro.reports.server.collector.AggregationService;
import org.maestro.reports.server.collector.ReportCollectorWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class ExtendedReportCollectorWorker extends ReportCollectorWorker {
    private static final Logger logger = LoggerFactory.getLogger(ExtendedReportCollectorWorker.class);
    private static final InsertToResultsDBHook dbHook = new InsertToResultsDBHook();

    public ExtendedReportCollectorWorker(final File dataDir, final MaestroReceiverClient client,
                                         final TestExecutionInfo executionInfo) {
        super(dataDir, client, executionInfo);
    }

    @Override
    protected void runAggregation(int maxTestId, int maxTestNumber, final AggregationService aggregationService) {
        logger.debug("Adding a new hook");
        aggregationService.getHooks().add(dbHook);

        logger.debug("Running the aggregation");
        super.runAggregation(maxTestId, maxTestNumber, aggregationService);
    }
}
