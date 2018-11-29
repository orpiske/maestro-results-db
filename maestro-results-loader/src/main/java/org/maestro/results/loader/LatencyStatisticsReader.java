package org.maestro.results.loader;

import org.maestro.plotter.common.exceptions.EmptyDataSet;
import org.maestro.plotter.latency.serializer.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class LatencyStatisticsReader {
    private static final Logger logger = LoggerFactory.getLogger(LatencyStatisticsReader.class);

    private LatencyStatisticsReader() {}

    private static void readLatencyStatistics(final Statistics statistics, final Map<String, Object> properties) {
        properties.put("latency90th", String.valueOf(statistics.getLatency90th()));
        properties.put("latency95th", String.valueOf(statistics.getLatency95th()));
        properties.put("latency99th", String.valueOf(statistics.getLatency99th()));

    }

    public static void loadLatencyStatistics(final File reportFile, final Map<String, Object> properties) {
        LatencySerializer latencySerializer = new DefaultLatencySerializer();

        try {
            LatencyDistribution latencyDistribution = latencySerializer.serialize(reportFile);

            Map<String, Latency> values = latencyDistribution.getLatencyDistribution();
            Latency serviceTimeLatency = values.get("serviceTime");
            if (serviceTimeLatency == null) {
                logger.warn("Tried to read latency file {} but there was no latency information present", reportFile);

                return;
            }

            Statistics statistics = serviceTimeLatency.getStatistics();

            readLatencyStatistics(statistics, properties);
        }
        catch (EmptyDataSet e) {
            logger.warn("Empty latency report file {}: {}", reportFile, e.getMessage());
        }
        catch (IOException e) {
            logger.error("Unable to serialize latency report file {}: {}", reportFile, e.getMessage(), e);
        }
    }
}
