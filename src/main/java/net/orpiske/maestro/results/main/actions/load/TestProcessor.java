package net.orpiske.maestro.results.main.actions.load;

import net.orpiske.maestro.results.dao.TestDao;
import net.orpiske.maestro.results.dto.Test;

import java.io.File;

public class TestProcessor {
    private Test test;

    public TestProcessor(final Test test) {
        this.test = test;
    }

    public int loadTest(final File reportDir) {
        if (test.getTestResult() == null) {
            if (reportDir.getPath().contains("success")) {
                test.setTestResult("success");
            }
            else {
                test.setTestResult("failed");
            }
        }

        System.out.println("Recording new test");
        TestDao dao = new TestDao();
        return dao.insert(test);
    }
}
