package org.maestro.results.server.controllers.sut;

import io.javalin.Context;
import io.javalin.Handler;
import org.maestro.results.dao.SutDao;
import org.maestro.results.dto.Sut;

import java.util.List;

public class AllSutsController implements Handler {
    private SutDao sutDao = new SutDao();

    @Override
    public void handle(Context context) throws Exception {
        List<Sut> sutList = sutDao.fetch();
        context.json(sutList);
    }
}
