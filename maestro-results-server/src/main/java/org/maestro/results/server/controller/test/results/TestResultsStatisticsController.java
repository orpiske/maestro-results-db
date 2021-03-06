package org.maestro.results.server.controller.test.results;

import io.javalin.Context;
import io.javalin.Handler;
import org.maestro.reports.dao.exceptions.DataNotFoundException;
import org.maestro.results.dao.TestResultsStatisticsDao;
import org.maestro.results.dto.TestResultStatistics;

public class TestResultsStatisticsController implements Handler {
    private TestResultsStatisticsDao dao = new TestResultsStatisticsDao();

    @Override
    public void handle(Context context) {
        try {
            int id = Integer.parseInt(context.param("id"));

            TestResultStatistics dto = dao.successFailureCount(id);
            context.json(dto);
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
