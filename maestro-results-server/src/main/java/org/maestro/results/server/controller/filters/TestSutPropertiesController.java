package org.maestro.results.server.controller.filters;

import io.javalin.Context;
import io.javalin.Handler;
import org.maestro.results.dao.TestSutPropertiesLinkDao;
import org.maestro.results.dto.TestSutPropertiesLink;

import java.util.List;

public class TestSutPropertiesController implements Handler {
    private TestSutPropertiesLinkDao dao = new TestSutPropertiesLinkDao();

    @Override
    public void handle(Context context) throws Exception {
        List<TestSutPropertiesLink> list = dao.fetch();

        context.json(list);
    }
}
