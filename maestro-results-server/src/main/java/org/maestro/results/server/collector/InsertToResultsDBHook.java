package org.maestro.results.server.collector;

import org.maestro.reports.dto.Report;
import org.maestro.reports.server.collector.PostAggregationHook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class InsertToResultsDBHook implements PostAggregationHook {
    private static final Logger logger = LoggerFactory.getLogger(InsertToResultsDBHook.class);

    @Override
    public void exec(List<Report> list) {
        for (Report report : list) {
            logger.info("Running the post aggregation for {}", report);
        }
    }
}
