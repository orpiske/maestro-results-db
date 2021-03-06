package org.maestro.results.server.controllers.compare;

import io.javalin.Context;
import io.javalin.Handler;
import org.maestro.reports.dao.exceptions.DataNotFoundException;
import org.maestro.results.dao.TestResultsDao;
import org.maestro.results.dto.TestResult;

import java.util.List;

public class TestIterationComparatorController implements Handler {
    private TestResultsDao testResultsDao = new TestResultsDao();

    @Override
    public void handle(Context context) {
        try {
            int t0 = Integer.parseInt(context.param("t0"));
            int n0 = Integer.parseInt(context.param("n0"));

            int t1 = Integer.parseInt(context.param("t1"));
            int n1 = Integer.parseInt(context.param("n1"));

            List<TestResult> results = testResultsDao.fetchForCompare(t0, n0, t1, n1);

            context.json(results);
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
