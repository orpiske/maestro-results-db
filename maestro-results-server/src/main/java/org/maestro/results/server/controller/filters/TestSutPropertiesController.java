package org.maestro.results.server.controller.filters;

import io.javalin.Context;
import io.javalin.Handler;
import org.maestro.reports.dao.exceptions.DataNotFoundException;
import org.maestro.results.dao.TestSutPropertiesLinkDao;
import org.maestro.results.dto.TestSutPropertiesLink;

import java.util.List;

public class TestSutPropertiesController implements Handler {
    private TestSutPropertiesLinkDao dao = new TestSutPropertiesLinkDao();

    @Override
    public void handle(Context context) throws Exception {
        try {
            List<TestSutPropertiesLink> list = dao.fetch();

            context.json(list);
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
