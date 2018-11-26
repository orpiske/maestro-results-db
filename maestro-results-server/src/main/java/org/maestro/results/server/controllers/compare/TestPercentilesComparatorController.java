package org.maestro.results.server.controllers.compare;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.javalin.Context;
import io.javalin.Handler;
import org.jetbrains.annotations.NotNull;
import org.maestro.common.HostTypes;
import org.maestro.reports.dao.exceptions.DataNotFoundException;
import org.maestro.results.dao.TestResultsDao;
import org.maestro.results.dto.TestResult;
import org.maestro.results.server.controller.common.CategorizedResponse;

import java.util.*;

public class TestPercentilesComparatorController implements Handler {
    private class LatPair implements Comparable<LatPair> {
        @JsonProperty("Test ID")
        int testId;

        @JsonProperty("Test Number")
        int testNumber;

        @JsonProperty("90th percentile")
        double latPercentile90;

        @JsonProperty("95th percentile")
        double latPercentile95;

        @JsonProperty("99th percentile")
        double latPercentile99;

        public LatPair(final TestResult testResult) {
            this(testResult.getTestId(), testResult.getTestNumber(), testResult.getLatPercentile90() / 1000,
                    testResult.getLatPercentile95() / 1000, testResult.getLatPercentile99() / 1000);
        }

        private LatPair(int testId, int testNumber, double latPercentile90, double latPercentile95, double latPercentile99) {
            this.testId = testId;
            this.testNumber = testNumber;
            this.latPercentile90 = latPercentile90;
            this.latPercentile95 = latPercentile95;
            this.latPercentile99 = latPercentile99;
        }


        @Override
        public int compareTo(@NotNull TestPercentilesComparatorController.LatPair latPair) {
            if (this.testId < latPair.testId) {
                return -1;
            }
            else {
                if (this.testId > latPair.testId) {
                    return 1;
                }
            }

            if (this.testNumber < latPair.testNumber) {
                return -1;
            }
            else {
                if (this.testNumber > latPair.testNumber) {
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

            List<TestResult> results = testResultsDao.fetchForCompare(t0, n0, t1, n1, HostTypes.RECEIVER_HOST_TYPE);

            CategorizedResponse resp = new CategorizedResponse<>();

            // It does a transformation of the test results to simplify things on the front-end part of the code
            for (TestResult testResult : results) {
                resp.getPairs().add(new LatPair(testResult));
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
