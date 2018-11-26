package org.maestro.results.server.controller.test.results;

import io.javalin.Context;
import io.javalin.Handler;
import org.maestro.reports.dao.exceptions.DataNotFoundException;
import org.maestro.results.dao.TestResultsDao;
import org.maestro.results.dto.TestResult;

import java.util.List;

public class AllTestsResultsController implements Handler {
    private TestResultsDao testResultsDao = new TestResultsDao();

    @Override
    public void handle(Context context) {
        try {
            List<TestResult> testResultList = testResultsDao.fetch();

            context.json(testResultList);
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
