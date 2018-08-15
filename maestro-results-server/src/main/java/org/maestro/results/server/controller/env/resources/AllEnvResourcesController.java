package org.maestro.results.server.controller.env.resources;

import io.javalin.Context;
import io.javalin.Handler;
import org.maestro.results.dao.EnvResourceDao;
import org.maestro.results.dto.EnvResource;

import java.util.List;

public class AllEnvResourcesController implements Handler {
    private EnvResourceDao envResourceDao = new EnvResourceDao();

    @Override
    public void handle(Context context) throws Exception {
        List<EnvResource> envResourceList = envResourceDao.fetch();

        context.json(envResourceList);
    }
}
