package org.maestro.results.server.controller.test;

import io.javalin.Context;
import io.javalin.Handler;
import org.maestro.results.dao.TestDao;
import org.maestro.results.dto.Test;

import java.util.List;

public class AllTestsControlller implements Handler {
    private TestDao testDao = new TestDao();

    @Override
    public void handle(Context context) throws Exception {
        List<Test> testList = testDao.fetch();

        context.json(testList);
    }
}
