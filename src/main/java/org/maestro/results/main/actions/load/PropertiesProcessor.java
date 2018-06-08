package org.maestro.results.main.actions.load;

import org.maestro.common.exceptions.MaestroException;
import org.maestro.results.dao.*;
import org.maestro.results.dto.*;
import org.maestro.results.main.actions.load.utils.PropertyUtils;
import org.maestro.common.URLQuery;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class PropertiesProcessor {
    private static final Logger logger = LoggerFactory.getLogger(PropertiesProcessor.class);

    private String envName;
    private Test test;

    public PropertiesProcessor(final Test test, final String envName) {
        this.test = test;
        this.envName = envName;
    }

    private void loadFailConditions(final File reportFile, final Map<String, Object> properties) {
        String[] failConditions = {"fcl"};

        TestFailConditionDao dao = new TestFailConditionDao();
        for (String failCondition : failConditions) {
            String value = (String) properties.get(failCondition);
            if (value != null) {
                TestFailCondition dto = new TestFailCondition();

                dto.setTestId(test.getTestId());
                dto.setTestNumber(test.getTestNumber());
                dto.setTestFailConditionResourceName(reportFile.getName());
                dto.setTestFailConditionName(failCondition);
                dto.setTestFailConditionValue(value);

                logger.debug("About to fail condition {} for test {}", dto, test.getTestId());
                dao.insert(dto);
            }
        }
    }


    private void loadMsgProperties(final File hostDir, final Map<String, Object> properties) {
        String[] msgProperties = {"apiName", "variableSize",
                "apiVersion", "messageSize"};

        TestMsgPropertyDao dao = new TestMsgPropertyDao();
        for (String msgProperty : msgProperties) {
            String value = (String) properties.get(msgProperty);
            if (value != null) {
                TestMsgProperty testMsgProperty = new TestMsgProperty();

                testMsgProperty.setTestId(test.getTestId());
                testMsgProperty.setTestNumber(test.getTestNumber());
                testMsgProperty.setTestMsgPropertyResourceName(hostDir.getName());
                testMsgProperty.setTestMsgPropertyName(msgProperty);
                testMsgProperty.setTestMsgPropertyValue(value);

                logger.debug("About to insert property {} for test {}", testMsgProperty, test.getTestId());
                dao.insert(testMsgProperty);
            }
        }

        String tmpUrl = (String) properties.get("brokerUri");

        if (tmpUrl == null) {
            throw new MaestroException("Invalid test case: the backend did not save the endpoint URL");
        }
        String url = URLDecoder.decode(tmpUrl);

        try {
            URLQuery urlQuery = new URLQuery(url);

            Map<String, String> uriParams = urlQuery.getParams();

            for (Map.Entry<String, String> entry : uriParams.entrySet()) {
                TestMsgProperty testMsgProperty = new TestMsgProperty();

                testMsgProperty.setTestId(test.getTestId());
                testMsgProperty.setTestNumber(test.getTestNumber());
                testMsgProperty.setTestMsgPropertyResourceName(hostDir.getName());
                testMsgProperty.setTestMsgPropertyName(entry.getKey());
                testMsgProperty.setTestMsgPropertyValue(entry.getValue());

                logger.debug("About to insert property {} for test {}", testMsgProperty, test.getTestId());
                dao.insert(testMsgProperty);
            }

        } catch (URISyntaxException e) {
            logger.error("Unable to parse URL {}", url, e);
            throw new MaestroException("Invalid URL", e);
        }
    }

    private boolean isReceiver(final File hostDir) {
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


    private boolean isInspector(final File hostDir) {
        String[] inspectorFiles = { "broker-jvm-inspector.csv.gz", "broker-jvm-inspector_eden_memory.png",
                "broker-jvm-inspector_memory.png",
                "broker-jvm-inspector_pm_memory.png",
                "broker-jvm-inspector_queue_data.png",
                "broker-jvm-inspector_survivor_memory.png",
                "broker-jvm-inspector_tenured_memory.png",
                "broker.properties" };

        for (String fileName : inspectorFiles) {
            File file = new File(hostDir, fileName);
            if (file.exists()) {
                return true;
            }
        }

        return false;
    }

    /**
     *
     * @param hostDir
     * @param properties
     */
    private void loadEnvResults(final File hostDir, final Map<String, Object> properties) {
        EnvResourceDao envResourceDao = new EnvResourceDao();
        final EnvResource envResource = envResourceDao.fetchByName(hostDir.getName());

        EnvResults envResults = new EnvResults();
        envResults.setTestId(test.getTestId());
        envResults.setTestNumber(test.getTestNumber());
        envResults.setEnvResourceId(envResource.getEnvResourceId());
        envResults.setEnvName(envName);

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


        if (!envResults.getEnvResourceRole().equals("inspector")) {
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
        }

        envResults.setConnectionCount(Integer.parseInt((String) properties.get("parallelCount")));

        EnvResultsDao envResultsDao = new EnvResultsDao();
        envResultsDao.insert(envResults);
    }

    public void loadTest(final File hostDir) {
        logger.debug("Loading host-specific properties: {}", hostDir);
        Map<String, Object> properties = new HashMap<>();

        Collection<File> fileCollection = FileUtils.listFiles(hostDir, new String[] { "properties"}, true);
        fileCollection.forEach(item -> PropertyUtils.loadProperties(item, properties));

        logger.debug("Recording message properties: {}", hostDir);
        loadMsgProperties(hostDir, properties);

        logger.debug("Recording fail conditions: {}", hostDir);
        loadFailConditions(hostDir, properties);

        logger.debug("Recording results per environment: {}", hostDir);
        loadEnvResults(hostDir, properties);


        String rateStr = (String) properties.get("rate");
        String durationStr = (String) properties.get("duration");

        logger.info("Updating duration to {} and rate to {} for test {}/{}", durationStr, rateStr, test.getTestId(),
                test.getTestNumber());


        TestDao testDao = new TestDao();
        testDao.updateDurationAndRate(test.getTestId(), test.getTestNumber(), Integer.parseInt(durationStr),
                Integer.parseInt(rateStr));
    }

}
