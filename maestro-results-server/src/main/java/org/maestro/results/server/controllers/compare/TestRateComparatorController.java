package org.maestro.results.server.controllers.compare;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.javalin.Context;
import io.javalin.Handler;
import org.jetbrains.annotations.NotNull;
import org.maestro.reports.dao.exceptions.DataNotFoundException;
import org.maestro.results.dao.TestResultsDao;
import org.maestro.results.dto.TestResult;
import org.maestro.results.server.controller.common.CategorizedResponse;

import java.util.List;

public class TestRateComparatorController implements Handler {
    private class RatePair implements Comparable<RatePair> {
        @JsonProperty("Test ID")
        int testId;

        @JsonProperty("Test Number")
        int testNumber;

        @JsonProperty("Target Rate")
        private int testTargetRate;

        @JsonProperty("Combined Target Rate")
        private int testCombinedTargetRate;

        @JsonProperty("Minimum Rate")
        private double testRateMin;

        @JsonProperty("Maximum Rate")
        private double testRateMax;

        @JsonProperty("Rate Geometric Mean")
        private double testRateGeometricMean;

        public RatePair(final TestResult testResult) {
            this(testResult.getTestId(), testResult.getTestNumber(), testResult.getTestTargetRate(),
                    testResult.getTestCombinedTargetRate(), testResult.getTestRateMin(), testResult.getTestRateMax(),
                    testResult.getTestRateGeometricMean());
        }

        private RatePair(int testId, int testNumber, int testTargetRate, int testCombinedTargetRate, double testRateMin, double testRateMax, double testRateGeometricMean) {
            this.testId = testId;
            this.testNumber = testNumber;
            this.testTargetRate = testTargetRate;
            this.testCombinedTargetRate = testCombinedTargetRate;
            this.testRateMin = testRateMin;
            this.testRateMax = testRateMax;
            this.testRateGeometricMean = testRateGeometricMean;
        }

        @Override
        public int compareTo(@NotNull TestRateComparatorController.RatePair ratePair) {
            if (this.testId < ratePair.testId) {
                return -1;
            }
            else {
                if (this.testId > ratePair.testId) {
                    return 1;
                }
            }

            if (this.testNumber < ratePair.testNumber) {
                return -1;
            }
            else {
                if (this.testNumber > ratePair.testNumber) {
                    return 1;
                }
            }

            return 0;
        }
    }

    private TestResultsDao testResultsDao = new TestResultsDao();

    @Override
    public void handle(Context context) {
        try {
            int t0 = Integer.parseInt(context.param("t0"));
            int n0 = Integer.parseInt(context.param("n0"));

            int t1 = Integer.parseInt(context.param("t1"));
            int n1 = Integer.parseInt(context.param("n1"));

            String role = context.param("role");

            List<TestResult> results = testResultsDao.fetchForCompare(t0, n0, t1, n1, role);

            CategorizedResponse resp = new CategorizedResponse<>();

            // It does a transformation of the test results to simplify things on the front-end part of the code
            for (TestResult testResult : results) {
                resp.getPairs().add(new RatePair(testResult));
                resp.getCategories().add(CategorizedResponse.categoryName(testResult.getTestId(), testResult.getTestNumber(),
                        testResult.getEnvResourceName()));
            }

            context.json(resp);
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
