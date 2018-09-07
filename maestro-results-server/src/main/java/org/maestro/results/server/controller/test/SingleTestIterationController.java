package org.maestro.results.server.controller.test;

import io.javalin.Context;
import io.javalin.Handler;
import org.maestro.results.dao.TestDao;
import org.maestro.results.dto.Test;

import java.util.List;

public class SingleTestIterationController implements Handler {
    private TestDao testDao = new TestDao();

    @Override
    public void handle(Context context) throws Exception {
        int id = Integer.parseInt(context.param("id"));
        int number = Integer.parseInt(context.param("number"));

        Test testInfo = testDao.fetch(id, number);

        context.json(testInfo);
    }
}