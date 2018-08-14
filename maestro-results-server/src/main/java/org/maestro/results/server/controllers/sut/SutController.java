package org.maestro.results.server.controllers.sut;


import io.javalin.Context;
import io.javalin.Handler;
import org.maestro.results.dao.SutDao;
import org.maestro.results.dto.Sut;

public class SutController implements Handler {
    private SutDao sutDao = new SutDao();


    @Override
    public void handle(Context context) throws Exception {
        int id = Integer.parseInt(context.param("id"));
        Sut sut = sutDao.fetchById(id).get(0);

        context.json(sut);
    }
}
