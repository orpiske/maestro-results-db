package org.maestro.results.server.controller.test.results;

import io.javalin.Context;
import io.javalin.Handler;
import org.maestro.results.dao.TestResultsDao;
import org.maestro.results.dto.TestResult;

import java.util.List;

public class SingleTestResultsController implements Handler {
    private TestResultsDao testResultsDao = new TestResultsDao();

    @Override
    public void handle(Context context) throws Exception {
        int id = Integer.parseInt(context.param("id"));

        List<TestResult> testResultList = testResultsDao.fetch(id);

        context.json(testResultList);
    }
}
