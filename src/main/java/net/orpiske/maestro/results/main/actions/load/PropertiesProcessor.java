package net.orpiske.maestro.results.main.actions.load;

import net.orpiske.maestro.results.dao.*;
import net.orpiske.maestro.results.dto.*;
import net.orpiske.mpt.common.URLQuery;

import java.io.File;

import java.net.URISyntaxException;
import java.util.Map;

public class PropertiesProcessor {
    private String envName;
    private Test test;

    public PropertiesProcessor(final Test test, final String envName) {
        this.test = test;
        this.envName = envName;
    }

    private void loadFailConditions(final File reportDir, final Map<String, Object> properties) {
        String[] failConditions = {"fcl"};

        TestFailConditionDao dao = new TestFailConditionDao();
        for (String failCondition : failConditions) {
            String value = (String) properties.get(failCondition);
            if (value != null) {
                TestFailCondition dto = new TestFailCondition();

                dto.setTestId(test.getTestId());
                dto.setTestNumber(test.getTestNumber());
                dto.setTestFailConditionResourceName(reportDir.getParentFile().getName());
                dto.setTestFailConditionName(failCondition);
                dto.setTestFailConditionValue(value);

                dao.insert(dto);
            }
        }
    }


    private void loadMsgProperties(final File reportDir, final Map<String, Object> properties) {
        String[] msgProperties = {"apiName", "variableSize",
                "apiVersion", "messageSize"};

        TestMsgPropertyDao dao = new TestMsgPropertyDao();
        for (String msgProperty : msgProperties) {
            String value = (String) properties.get(msgProperty);
            if (value != null) {
                TestMsgProperty testMsgProperty = new TestMsgProperty();

                testMsgProperty.setTestId(test.getTestId());
                testMsgProperty.setTestNumber(test.getTestNumber());
                testMsgProperty.setTestMsgPropertyResourceName(reportDir.getParentFile().getName());
                testMsgProperty.setTestMsgPropertyName(msgProperty);
                testMsgProperty.setTestMsgPropertyValue(value);

                dao.insert(testMsgProperty);
            }
        }

        String uri = (String) properties.get("brokerUri");
        try {
            URLQuery urlQuery = new URLQuery(uri);

            Map<String, String> uriParams = urlQuery.getParams();

            for (Map.Entry<String, String> entry : uriParams.entrySet()) {
                TestMsgProperty testMsgProperty = new TestMsgProperty();

                testMsgProperty.setTestId(test.getTestId());
                testMsgProperty.setTestNumber(test.getTestNumber());
                testMsgProperty.setTestMsgPropertyResourceName(reportDir.getParentFile().getName());
                testMsgProperty.setTestMsgPropertyName(entry.getKey());
                testMsgProperty.setTestMsgPropertyValue(entry.getValue());

                dao.insert(testMsgProperty);
            }

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private boolean isReceiver(final File reportDir) {
        String[] latencyFiles = { "receiverd-latency_90.png", "receiverd-latency_all.png", "receiverd-latency.csv.gz",
                "receiverd-latency.hdr", "receiverd-rate.csv.gz", "receiverd-rate_rate.png" };

        for (String fileName : latencyFiles) {
            File file = new File(reportDir, fileName);
            if (file.exists()) {
                return true;
            }
        }

        return false;
    }


    private boolean isInspector(final File reportDir) {
        String[] inspectorFiles = { "broker-jvm-inspector.csv.gz", "broker-jvm-inspector_eden_memory.png",
                "broker-jvm-inspector_memory.png",
                "broker-jvm-inspector_pm_memory.png",
                "broker-jvm-inspector_queue_data.png",
                "broker-jvm-inspector_survivor_memory.png",
                "broker-jvm-inspector_tenured_memory.png",
                "broker.properties" };

        for (String fileName : inspectorFiles) {
            File file = new File(reportDir, fileName);
            if (file.exists()) {
                return true;
            }
        }

        return false;
    }

    /**
     *
     * @param reportDir
     * @param properties
     */
    private void loadEnvResults(final File reportDir, final Map<String, Object> properties) {
        EnvResourceDao envResourceDao = new EnvResourceDao();
        final EnvResource envResource = envResourceDao.fetchByName(reportDir.getParentFile().getName());

        EnvResults envResults = new EnvResults();
        envResults.setTestId(test.getTestId());
        envResults.setTestNumber(test.getTestNumber());
        envResults.setEnvResourceId(envResource.getEnvResourceId());
        envResults.setEnvName(envName);

        if (isReceiver(reportDir)) {
            envResults.setEnvResourceRole("receiver");
        }
        else {
            if (isInspector(reportDir)) {
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

    public void loadTest(final File reportDir, final Map<String, Object> properties) {


        System.out.println("Loading message properties: " + reportDir);
        loadMsgProperties(reportDir, properties);

        System.out.println("Loading fail conditions: " + reportDir);
        loadFailConditions(reportDir, properties);

        System.out.println("Loading results per environment: " + reportDir);
        loadEnvResults(reportDir, properties);

        TestDao testDao = new TestDao();
        String rateStr = (String) properties.get("rate");
        String durationStr = (String) properties.get("duration");

        testDao.updateDurationAndRate(test.getTestId(), test.getTestNumber(), rateStr, durationStr);
    }

}
