package org.maestro.results.server.controllers.sut;

import io.javalin.Context;
import io.javalin.Handler;
import org.maestro.reports.dao.exceptions.DataNotFoundException;
import org.maestro.results.dao.SutDao;
import org.maestro.results.dto.Sut;

import java.util.List;

public class AllSutsController implements Handler {
    private SutDao sutDao = new SutDao();

    @Override
    public void handle(Context context) {
        try {
            List<Sut> sutList = sutDao.fetch();

            context.json(sutList);
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
