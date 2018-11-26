package org.maestro.results.server.controller.test;

import io.javalin.Context;
import io.javalin.Handler;
import org.maestro.reports.dao.exceptions.DataNotFoundException;
import org.maestro.results.dao.TestPropertiesDao;
import org.maestro.results.dto.TestProperties;

public class SingleTestPropertiesController implements Handler {
    private TestPropertiesDao dao = new TestPropertiesDao();

    @Override
    public void handle(Context context) {
        try {
            int id = Integer.parseInt(context.param("id"));
            int number = Integer.parseInt(context.param("number"));

            TestProperties ret = dao.fetch(id, number);

            context.json(ret);
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
