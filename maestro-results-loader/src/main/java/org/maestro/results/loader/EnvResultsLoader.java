package org.maestro.results.loader;

import org.maestro.common.HostTypes;
import org.maestro.results.dao.EnvResourceDao;
import org.maestro.results.dao.EnvResultsDao;
import org.maestro.results.dto.EnvResource;
import org.maestro.results.dto.EnvResults;
import org.maestro.results.dto.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class EnvResultsLoader {
    private static final Logger logger = LoggerFactory.getLogger(EnvResultsLoader.class);

    private Test test;
    private String envName;
    private EnvResultsDao envResultsDao;

    public EnvResultsLoader(final Test test, final String envName) {
        this.test = test;
        this.envName = envName;

        this.envResultsDao = new EnvResultsDao();
    }

    /**
     *
     * @param envResource
     * @param properties
     */
    public void load(final EnvResource envResource, final String hostRole, final Map<String, Object> properties) {
        logger.debug("Recording results for environment: {}", envResource.getEnvResourceName());

        EnvResults envResults = new EnvResults();
        envResults.setTestId(test.getTestId());
        envResults.setTestNumber(test.getTestNumber());
        envResults.setEnvResourceId(envResource.getEnvResourceId());
        envResults.setEnvName(envName);
        envResults.setEnvResourceRole(hostRole);

        if (!hostRole.equals(HostTypes.INSPECTOR_HOST_TYPE)) {
            readSenderReceiverProperties(properties, envResults);
        }

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

        String rateGeometricMeanStr = (String) properties.get("rateGeometricMean");
        if (rateGeometricMeanStr != null) {
            envResults.setTestRateGeometricMean(Double.parseDouble(rateGeometricMeanStr));
        }

        String rateStandardDeviationStr = (String) properties.get("rateStandardDeviation");
        if (rateStandardDeviationStr != null) {
            envResults.setTestRateStandardDeviation(Double.parseDouble(rateStandardDeviationStr));
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
}
