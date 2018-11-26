package org.maestro.results.server.controller.test.results;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.javalin.Context;
import io.javalin.Handler;
import org.maestro.reports.dao.exceptions.DataNotFoundException;
import org.maestro.results.dao.TestResultsDao;
import org.maestro.results.dto.TestResult;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class RateDistributionByTestController implements Handler {
    public class RateInfo {
        @JsonProperty("Test Number")
        final int testNumber;

        @JsonProperty("Rate Geometric Mean")
        final double rateGeoMean;

        @JsonProperty("Target Rate")
        final int targetRate;

        @JsonProperty("Combined Target Rate")
        final int combinedTargetRate;

        public RateInfo(final TestResult testResult) {
            this(testResult.getTestNumber(), testResult.getTestRateGeometricMean(), testResult.getTestTargetRate(),
                    testResult.getTestCombinedTargetRate());
        }

        private RateInfo(int testNumber, double rateGeoMean, int targetRate, int combinedTargetRate) {
            this.testNumber = testNumber;
            this.rateGeoMean = rateGeoMean;
            this.targetRate = targetRate;
            this.combinedTargetRate = combinedTargetRate;
        }
    }

    private class Resp {
        @JsonProperty("Categories")
        Set<String> categories = new TreeSet<>();

        @JsonProperty("Pairs")
        List<RateInfo> pairs = new LinkedList<>();

        public Set<String> getCategories() {
            return categories;
        }

        public void setCategories(Set<String> categories) {
            this.categories = categories;
        }

        public List<RateInfo> getPairs() {
            return pairs;
        }

        public void setPairs(List<RateInfo> pairs) {
            this.pairs = pairs;
        }
    }


    private TestResultsDao testResultsDao = new TestResultsDao();

    @Override
    public void handle(Context context) {
        try {
            int id = Integer.parseInt(context.param("id"));
            String role = context.param("role");

            List<TestResult> testResultList = testResultsDao.fetchOrdered(id, role);

            Resp resp = new Resp();

            // It does a transformation of the test results to simplify things on the front-end part of the code
            for (TestResult testResult : testResultList) {
                resp.pairs.add(new RateInfo(testResult));
                resp.categories.add(String.format("%d/%d %s", testResult.getTestId(), testResult.getTestNumber(),
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
