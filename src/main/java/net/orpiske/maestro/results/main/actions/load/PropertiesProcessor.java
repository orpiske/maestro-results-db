package net.orpiske.maestro.results.main.actions.load;

import net.orpiske.maestro.results.dao.*;
import net.orpiske.maestro.results.dto.*;
import net.orpiske.mpt.common.URLQuery;

import java.io.File;

import java.net.URISyntaxException;
import java.util.Map;

public class PropertiesProcessor {
    private String envName;
    private int testId;

    public PropertiesProcessor(final Test test, final String envName, int testId) {
        this.envName = envName;
        this.testId = testId;
    }

    private void loadFailConditions(final File reportDir, final Map<String, Object> properties, int testId) {
        String[] failConditions = {"fcl"};

        TestFailConditionDao dao = new TestFailConditionDao();
        for (String failCondition : failConditions) {
            String value = (String) properties.get(failCondition);
            if (value != null) {
                TestFailCondition dto = new TestFailCondition();

                dto.setTestId(testId);
                dto.setTestFailConditionResourceName(reportDir.getName());
                dto.setTestFailConditionName(failCondition);
                dto.setTestFailConditionValue(value);

                dao.insert(dto);
            }
        }
    }


    private void loadMsgProperties(final File reportDir, final Map<String, Object> properties, int testId) {
        String[] msgProperties = {"limitDestinations", "apiName", "variableSize",
                "apiVersion", "messageSize"};

        TestMsgPropertyDao dao = new TestMsgPropertyDao();
        for (String msgProperty : msgProperties) {
            String value = (String) properties.get(msgProperty);
            if (value != null) {
                TestMsgProperty testMsgProperty = new TestMsgProperty();

                testMsgProperty.setTestId(testId);
                testMsgProperty.setTestMsgPropertyResourceName(reportDir.getName());
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

                testMsgProperty.setTestId(testId);
                testMsgProperty.setTestMsgPropertyResourceName(reportDir.getName());
                testMsgProperty.setTestMsgPropertyName(entry.getKey());
                testMsgProperty.setTestMsgPropertyValue(entry.getValue());

                dao.insert(testMsgProperty);
            }

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private boolean isReceiver(final File reportDir) {
        String[] latencyFiles = { "receiverd-latency_90.png", "receiverd-latency_all.png", "receiverd-latency.csv",
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
     * rateMax=109.0
     rateErrorCount=0
     rateSamples=1.0
     rateGeometricMean=108.99999999999997
     rateMin=109.0
     rateStandardDeviation=0.0
     rateSkipCount=0
     * @param reportDir
     * @param properties
     */
    private void loadEnvResults(final File reportDir, final Map<String, Object> properties, int testId) {
        EnvResourceDao envResourceDao = new EnvResourceDao();
        final EnvResource envResource = envResourceDao.fetchByName(reportDir.getName());

        EnvResults envResults = new EnvResults();
        envResults.setTestId(testId);
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

            Double rateMin = Double.parseDouble((String) properties.get("rateMin"));
            envResults.setTestRateMin(rateMin.intValue());
            envResults.setTestRateErrorCount(Integer.parseInt((String) properties.get("rateErrorCount")));

            Double rateSamples = Double.parseDouble((String) properties.get("rateSamples"));
            envResults.setTestRateSamples(rateSamples.intValue());
            envResults.setTestRateGeometricMean(Double.parseDouble((String) properties.get("rateGeometricMean")));
            envResults.setTestRateStandardDeviation(Double.parseDouble((String) properties.get("rateStandardDeviation")));
            envResults.setTestRateSkipCount(Integer.parseInt((String) properties.get("rateSkipCount")));
        }


        envResults.setConnectionCount(Integer.parseInt((String) properties.get("parallelCount")));

        EnvResultsDao envResultsDao = new EnvResultsDao();
        envResultsDao.insert(envResults);
    }

    public void loadTest(final File reportDir, final Map<String, Object> properties) {


        System.out.println("Loading message properties");
        loadMsgProperties(reportDir, properties, testId);

        System.out.println("Loading fail conditions");
        loadFailConditions(reportDir, properties, testId);

        System.out.println("Loading results per environment");
        loadEnvResults(reportDir, properties, testId);

        TestDao testDao = new TestDao();
        String rateStr = (String) properties.get("rate");
        String durationStr = (String) properties.get("duration");

        testDao.updateDurationAndRate(testId, rateStr, durationStr);
    }

}
