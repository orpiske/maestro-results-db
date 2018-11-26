package org.maestro.results.server.controller.test.results;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.javalin.Context;
import io.javalin.Handler;
import org.maestro.common.HostTypes;
import org.maestro.reports.dao.exceptions.DataNotFoundException;
import org.maestro.results.dao.TestResultsDao;
import org.maestro.results.dto.TestResult;
import org.maestro.results.server.controller.common.CategorizedResponse;

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
    public void handle(Context context) {
        try {
            int id = Integer.parseInt(context.param("id"));

            List<TestResult> testResultList = testResultsDao.fetch(id, HostTypes.RECEIVER_HOST_TYPE);

            CategorizedResponse response = new CategorizedResponse<>();

            // It does a transformation of the test results to simplify things on the front-end part of the code
            for (TestResult testResult : testResultList) {
                response.getPairs().add(new LatPair(testResult));
                response.getCategories().add(CategorizedResponse.categoryName(testResult.getTestId(), testResult.getTestNumber(),
                        testResult.getEnvResourceName()));
            }

            context.json(response);
        }
        catch (DataNotFoundException e) {
            context.status(404);
            context.result(String.format("Not found: %s", e.getMessage()));
        }
        catch (Throwable t) {
            context.status(500);
            context.result(String.format("Internal server error: %s", t.getMessage()));
        }
    }
}
