package org.maestro.results.loader;

import org.maestro.results.dao.TestDao;
import org.maestro.results.dto.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class TestProcessor {
    private static final Logger logger = LoggerFactory.getLogger(TestProcessor.class);

    private TestDao dao = new TestDao();
    private Test test;

    public TestProcessor(final Test test) {
        this.test = test;
    }

    public int loadTest() {
        logger.info("Adding new test record into the DB");

        int id = dao.insert(test);
        logger.info("New test ID {} added to the database", id);
        return id;
    }
}
