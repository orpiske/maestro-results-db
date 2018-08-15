package org.maestro.results.server.controller.env.results;

import io.javalin.Context;
import io.javalin.Handler;
import org.maestro.results.dao.EnvResultsDao;
import org.maestro.results.dto.EnvResults;

import java.util.List;

public class AllEnvResultsController implements Handler {
    private EnvResultsDao envResultsDao = new EnvResultsDao();

    @Override
    public void handle(Context context) throws Exception {
        List<EnvResults> envResultsList = envResultsDao.fetch();

        context.json(envResultsList);
    }
}
