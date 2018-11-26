package org.maestro.results.server.controller.env.results;

import io.javalin.Context;
import io.javalin.Handler;
import org.maestro.reports.dao.exceptions.DataNotFoundException;
import org.maestro.results.dao.EnvResultsDao;
import org.maestro.results.dto.EnvResults;

import java.util.List;

public class AllEnvResultsController implements Handler {
    private EnvResultsDao envResultsDao = new EnvResultsDao();

    @Override
    public void handle(Context context) {
        try {
            List<EnvResults> envResultsList = envResultsDao.fetch();

            context.json(envResultsList);
        } catch (DataNotFoundException e) {
            context.status(404);
            context.result(String.format("Not found: %s", e.getMessage()));
        }
    }
}
