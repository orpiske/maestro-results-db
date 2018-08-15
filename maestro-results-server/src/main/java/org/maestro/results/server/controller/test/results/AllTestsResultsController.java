package org.maestro.results.server.controller.test.results;

import io.javalin.Context;
import io.javalin.Handler;
import org.maestro.results.dao.TestResultsDao;
import org.maestro.results.dto.TestResult;

import java.util.List;

public class AllTestsResultsController implements Handler {
    private TestResultsDao testResultsDao = new TestResultsDao();

    @Override
    public void handle(Context context) throws Exception {
        List<TestResult> testResultList = testResultsDao.fetch();

        context.json(testResultList);
    }
}
