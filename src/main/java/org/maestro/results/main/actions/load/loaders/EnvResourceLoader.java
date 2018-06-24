package org.maestro.results.main.actions.load.loaders;

import org.maestro.results.dao.EnvResourceDao;
import org.maestro.results.dao.EnvResultsDao;
import org.maestro.results.dto.EnvResource;
import org.maestro.results.dto.EnvResults;
import org.maestro.results.dto.Test;

import java.io.File;
import java.util.Map;

public class EnvResourceLoader {

    private Test test;
    private String envName;
    private EnvResourceDao envResourceDao;
    private EnvResultsDao envResultsDao;

    public EnvResourceLoader(Test test, final String envName) {
        this.test = test;
        this.envName = envName;

        this.envResourceDao = new EnvResourceDao();
        this.envResultsDao = new EnvResultsDao();
    }

    /**
     *
     * @param hostDir
     * @param properties
     */
    public void load(final File hostDir, final Map<String, Object> properties) {
        final EnvResource envResource = envResourceDao.fetchByName(hostDir.getName());

        EnvResults envResults = new EnvResults();
        envResults.setTestId(test.getTestId());
        envResults.setTestNumber(test.getTestNumber());
        envResults.setEnvResourceId(envResource.getEnvResourceId());
        envResults.setEnvName(envName);

        setEnvResourceRole(hostDir, envResults);

        if (!envResults.getEnvResourceRole().equals("inspector")) {
            readSenderReceiverProperties(properties, envResults);
        }

        envResults.setConnectionCount(Integer.parseInt((String) properties.get("parallelCount")));

        envResultsDao.insert(envResults);
    }

    private void readSenderReceiverProperties(final Map<String, Object> properties, final EnvResults envResults) {
        String rateMaxStr = (String) properties.get("rateMax");
        if (rateMaxStr != null) {
            Double rateMax = Double.parseDouble(rateMaxStr);
            envResults.setTestRateMax(rateMax.intValue());
        }

        String rateMinStr = (String) properties.get("rateMin");
        if (rateMinStr != null) {
            Double rateMin = Double.parseDouble(rateMinStr);

            envResults.setTestRateMin(rateMin.intValue());
        }

        String rateErrorCountStr = (String) properties.get("rateErrorCount");
        if (rateErrorCountStr != null) {
            envResults.setTestRateErrorCount(Integer.parseInt(rateErrorCountStr));
        }

        String rateSamplesStr = (String) properties.get("rateSamples");
        if (rateSamplesStr != null) {
            Double rateSamples = Double.parseDouble(rateSamplesStr);
            envResults.setTestRateSamples(rateSamples.intValue());
        }


        String rateGeometricMeanStr = (String) properties.get("rateGeometricMean");
        if (rateGeometricMeanStr != null) {
            envResults.setTestRateGeometricMean(Double.parseDouble(rateGeometricMeanStr));
        }

        String rateStandardDeviationStr = (String) properties.get("rateStandardDeviation");
        if (rateStandardDeviationStr != null) {
            envResults.setTestRateStandardDeviation(Double.parseDouble(rateStandardDeviationStr));
        }

        String rateSkipCountStr = (String) properties.get("rateSkipCount");
        if (rateSkipCountStr != null) {
            envResults.setTestRateSkipCount(Integer.parseInt(rateSkipCountStr));
        }

        String latPercentile90 = (String) properties.get("latency90th");
        if (latPercentile90 != null) {
            envResults.setLatPercentile90(Double.parseDouble(latPercentile90));
        }

        String latPercentile95 = (String) properties.get("latency95th");
        if (latPercentile95 != null) {
            envResults.setLatPercentile95(Double.parseDouble(latPercentile95));
        }

        String latPercentile99 = (String) properties.get("latency99th");
        if (latPercentile99 != null) {
            envResults.setLatPercentile99(Double.parseDouble(latPercentile99));
        }
    }

    private static void setEnvResourceRole(final File hostDir, final EnvResults envResults) {
        if (isReceiver(hostDir)) {
            envResults.setEnvResourceRole("receiver");
        }
        else {
            if (isInspector(hostDir)) {
                envResults.setEnvResourceRole("inspector");
            }
            else {
                envResults.setEnvResourceRole("sender");
            }
        }
    }

    private static boolean isReceiver(final File hostDir) {
        String[] latencyFiles = { "receiverd-latency_90.png", "receiverd-latency_all.png", "receiverd-latency.csv.gz",
                "receiverd-latency.hdr", "receiverd-rate.csv.gz", "receiverd-rate_rate.png" };

        for (String fileName : latencyFiles) {
            File file = new File(hostDir, fileName);
            if (file.exists()) {
                return true;
            }
        }

        return false;
    }


    private static boolean isInspector(final File hostDir) {
        String[] inspectorFiles = { "inspector.properties", "heap.csv",
                "broker-jvm-inspector_eden_memory.png",
                "broker-jvm-inspector_memory.png",
                "broker-jvm-inspector_pm_memory.png",
                "broker-jvm-inspector_queue_data.png",
                "broker-jvm-inspector_survivor_memory.png",
                "broker-jvm-inspector_tenured_memory.png",
                "broker.properties"};

        for (String fileName : inspectorFiles) {
            File file = new File(hostDir, fileName);
            if (file.exists()) {
                return true;
            }
        }

        return false;
    }
}
