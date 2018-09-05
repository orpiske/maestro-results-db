package org.maestro.results.server.controller.test.results;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.javalin.Context;
import io.javalin.Handler;
import org.maestro.common.HostTypes;
import org.maestro.results.dao.TestResultsDao;
import org.maestro.results.dto.TestResult;

import java.util.LinkedList;
import java.util.List;

public class LatencyDistributionByTestController implements Handler {
    public class LatPair {
        @JsonProperty("Test Number")
        int testNumber;

        @JsonProperty("90th percentile")
        double latPercentile90;

        @JsonProperty("95th percentile")
        double latPercentile95;

        @JsonProperty("99th percentile")
        double latPercentile99;

        public LatPair(final TestResult testResult) {
            this(testResult.getTestNumber(), testResult.getLatPercentile90() / 1000,
                    testResult.getLatPercentile95() / 1000, testResult.getLatPercentile99() / 1000);
        }

        private LatPair(int testNumber, double latPercentile90, double latPercentile95, double latPercentile99) {
            this.testNumber = testNumber;
            this.latPercentile90 = latPercentile90;
            this.latPercentile95 = latPercentile95;
            this.latPercentile99 = latPercentile99;
        }
    }

    private TestResultsDao testResultsDao = new TestResultsDao();

    @Override
    public void handle(Context context) throws Exception {
        int id = Integer.parseInt(context.param("id"));

        List<TestResult> testResultList = testResultsDao.fetch(id, HostTypes.RECEIVER_HOST_TYPE);

        List<LatPair> combined = new LinkedList<>();

        // It does a transformation of the test results to simplify things on the front-end part of the code
        for (TestResult testResult : testResultList) {
            combined.add(new LatPair(testResult));
        }

        context.json(combined);
    }
}
