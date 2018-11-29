package org.maestro.results.loader;

import org.maestro.plotter.common.serializer.SingleData;
import org.maestro.plotter.common.statistics.Statistics;
import org.maestro.plotter.rate.serializer.RateSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class RateStatisticsReader {
    private static final Logger logger = LoggerFactory.getLogger(RateStatisticsReader.class);

    private RateStatisticsReader() {}


    private static void readRateStatistics(final Statistics statistics, final Map<String, Object> properties) {
        properties.put("rateMax", String.valueOf(statistics.getMax()));
        properties.put("rateMin", String.valueOf(statistics.getMin()));
        properties.put("rateGeometricMean", String.valueOf(statistics.getGeometricMean()));
        properties.put("rateStandardDeviation", String.valueOf(statistics.getStandardDeviation()));
    }

    public static void loadRateStatistics(final File reportFile, final Map<String, Object> properties) {
        RateSerializer rateSerializer = new RateSerializer();

        try {
            SingleData<Long> rateData = rateSerializer.serialize(reportFile);

            if (rateData.getPeriods().size() == 0) {
                logger.warn("Ignoring the rate data file {} which has no records", reportFile);

                return;
            }

            Statistics statistics = rateData.getStatistics();

            readRateStatistics(statistics, properties);
        } catch (IOException e) {
            logger.error("Unable to serialize rate report file {}: {}", reportFile, e.getMessage(), e);
        }


    }
}
