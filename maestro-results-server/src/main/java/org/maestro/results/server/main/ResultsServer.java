package org.maestro.results.server.main;

import io.javalin.Javalin;
import org.apache.commons.configuration.AbstractConfiguration;
import org.apache.commons.configuration.ConfigurationException;
import org.maestro.common.ConfigurationWrapper;
import org.maestro.common.Constants;
import org.maestro.common.LogConfigurator;
import org.maestro.results.dto.Sut;
import org.maestro.results.server.routes.SutController;

import java.io.FileNotFoundException;

import static io.javalin.ApiBuilder.get;
import static io.javalin.ApiBuilder.path;

public class ResultsServer {
    static {
        LogConfigurator.defaultForDaemons();
    }

    public static void main(String[] args) {

        try {
            ConfigurationWrapper.initConfiguration(Constants.MAESTRO_CONFIG_DIR, "maestro-results-server.properties");
        } catch (FileNotFoundException e) {
            System.out.println("The server configuration file was not found");
            System.exit(1);
        } catch (ConfigurationException e) {
            System.out.println("The server configuration file is invalid");
            System.exit(2);
        }

        AbstractConfiguration config = ConfigurationWrapper.getConfig();

        final int port = config.getInteger("maestro.results.server", 7000);

        Javalin app = Javalin.create()
                .port(port)
                .enableStaticFiles("/site")
                .start();

        app.get("/api/live", ctx -> ctx.result("Hello World"));
        app.get("/api/sut/:id", new SutController());
    }

}
