package org.maestro.results.main.actions.load;

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


        // If it's the first test, just add it to the DB
        if (test.getTestId() == 0) {
            logger.info("This is the first test. All test can be skipped and the record added straight to the DB",
                    test.getTestId());

            int testId = dao.insert(test);
            test.setTestId(testId);

            return testId;
        }
        else {
            // Otherwise, check if there are previous tests recorded
            logger.info("Checking the number of records with ID {} in the database", test.getTestId());
            int count = dao.countRecords(test.getTestId());
            if (count == 0) {
                logger.info("Previous test execution not found, adding a new test record: {}", reportDir);
                int testId = dao.insert(test);
                test.setTestId(testId);

                logger.info("New test ID {} added to the database", reportDir);
                return testId;
            }
            else {
                // If yes, check the test numbers
                logger.info("There are {} records with ID {} in the database", count, test.getTestId());

                count = dao.countRecordsWithExecution(test.getTestId(), test.getTestNumber());
                if (count == 0){
                    // ... and add a new execution (aka test number) if not recorded yet
                    logger.info("Adding a new execution with number {} under the same ID", test.getTestNumber());
                    return dao.insertNewExecution(test);
                }
                else {
                    // ... or return if previous records are already loaded
                    logger.info("There are {} records with IDs {}/{} in the database ... Skipping", count, test.getTestId(),
                            test.getTestNumber());

                    return test.getTestId();
                }
            }
        }
    }
}
