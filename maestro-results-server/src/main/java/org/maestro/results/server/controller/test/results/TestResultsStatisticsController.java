package org.maestro.results.server.controller.test.results;

import io.javalin.Context;
import io.javalin.Handler;
import org.maestro.results.dao.TestResultsStatisticsDao;
import org.maestro.results.dto.TestResultStatistics;

public class TestResultsStatisticsController implements Handler {
    private TestResultsStatisticsDao dao = new TestResultsStatisticsDao();

    @Override
    public void handle(Context context) throws Exception {
        int id = Integer.parseInt(context.param("id"));

        TestResultStatistics dto = dao.successFailureCount(id);
        context.json(dto);
    }
}
