package org.maestro.results.server.controllers.sut;

import io.javalin.Context;
import io.javalin.Handler;
import org.maestro.results.dao.SutDao;
import org.maestro.results.dto.Sut;

public class TestSutControlller implements Handler {
    private SutDao dao = new SutDao();

    @Override
    public void handle(Context context) throws Exception {
        int id = Integer.parseInt(context.param("id"));

        Sut sut = dao.testSut(id);

        context.json(sut);
    }
}
