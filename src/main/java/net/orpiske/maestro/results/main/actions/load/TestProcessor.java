package net.orpiske.maestro.results.main.actions.load;

import net.orpiske.maestro.results.dao.TestDao;
import net.orpiske.maestro.results.dto.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class TestProcessor {
    private static final Logger logger = LoggerFactory.getLogger(TestProcessor.class);

    private Test test;

    public TestProcessor(final Test test) {
        this.test = test;
    }

    public int loadTest(final File reportDir) {
        logger.info("Adding new test record into the DB");
        if (test.getTestResult() == null) {
            if (reportDir.getPath().contains("success")) {
                test.setTestResult("success");
            }
            else {
                test.setTestResult("failed");
            }
        }

        logger.debug("Test record to add: {}", reportDir);

        TestDao dao = new TestDao();
        return dao.insert(test);
    }
}
