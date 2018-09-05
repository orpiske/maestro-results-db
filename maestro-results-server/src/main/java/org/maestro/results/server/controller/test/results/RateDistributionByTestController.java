package org.maestro.results.server.controller.test.results;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.javalin.Context;
import io.javalin.Handler;
import org.maestro.results.dao.TestResultsDao;
import org.maestro.results.dto.TestResult;

import java.util.LinkedList;
import java.util.List;

public class RateDistributionByTestController implements Handler {
    public class RateInfo {
        @JsonProperty("Test Number")
        final int testNumber;

        @JsonProperty("Rate Geometric Mean")
        final double rateGeoMean;

        public RateInfo(final TestResult testResult) {
            this(testResult.getTestNumber(), testResult.getTestRateGeometricMean());
        }

        private RateInfo(int testNumber, double rateGeoMean) {
            this.testNumber = testNumber;
            this.rateGeoMean = rateGeoMean;
        }
    }

    private TestResultsDao testResultsDao = new TestResultsDao();

    @Override
    public void handle(Context context) throws Exception {
        int id = Integer.parseInt(context.param("id"));
        String role = context.param("role");

        List<TestResult> testResultList = testResultsDao.fetchOrdered(id, role);

        List<RateInfo> combined = new LinkedList<>();

        // It does a transformation of the test results to simplify things on the front-end part of the code
        for (TestResult testResult : testResultList) {
            combined.add(new RateInfo(testResult));
        }

        context.json(combined);
    }
}
