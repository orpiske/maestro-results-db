package org.maestro.results.server.main;

import io.javalin.Javalin;
import org.maestro.common.LogConfigurator;
import org.maestro.results.dto.Sut;

public class ResultsServer {
//    static {
//        LogConfigurator.defaultForDaemons();
//    }


    public static void main(String[] args) {
        Javalin app = Javalin.start(7000);
        app.get("/", ctx -> ctx.result("Hello World"));

        Sut sut = new Sut();

        sut.setSutName("Some sut");
        app.get("/sut", ctx -> ctx.json(sut));
    }

}
