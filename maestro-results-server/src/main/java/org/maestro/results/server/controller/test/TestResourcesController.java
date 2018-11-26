package org.maestro.results.server.controller.test;

import io.javalin.Context;
import io.javalin.Handler;
import org.maestro.reports.dao.exceptions.DataNotFoundException;
import org.maestro.results.dao.EnvResourceDao;
import org.maestro.results.dto.EnvResource;

import java.util.List;

public class TestResourcesController implements Handler {
    private EnvResourceDao envResourceDao = new EnvResourceDao();

    @Override
    public void handle(Context context) {
        try {
            int id = Integer.parseInt(context.param("id"));

            List<EnvResource> envResourceList = envResourceDao.fetchForTest(id);

            context.json(envResourceList);
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
