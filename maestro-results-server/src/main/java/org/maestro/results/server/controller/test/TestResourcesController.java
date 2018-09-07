package org.maestro.results.server.controller.test;

import io.javalin.Context;
import io.javalin.Handler;
import org.maestro.results.dao.EnvResourceDao;
import org.maestro.results.dto.EnvResource;

import java.util.List;

public class TestResourcesController implements Handler {
    private EnvResourceDao envResourceDao = new EnvResourceDao();

    @Override
    public void handle(Context context) throws Exception {
        int id = Integer.parseInt(context.param("id"));

        List<EnvResource> envResourceList = envResourceDao.fetchForTest(id);

        context.json(envResourceList);
    }
}
