package org.maestro.results.server.controller.test;

import io.javalin.Context;
import io.javalin.Handler;
import org.maestro.results.dao.TestPropertiesDao;
import org.maestro.results.dto.TestProperties;

import java.util.List;

public class TestPropertiesController implements Handler {
    private TestPropertiesDao dao = new TestPropertiesDao();

    @Override
    public void handle(Context context) throws Exception {
        int id = Integer.parseInt(context.param("id"));

        List<TestProperties> ret = dao.fetch(id);

        context.json(ret);
    }
}
