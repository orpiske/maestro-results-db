package org.maestro.results.main.actions.xunit;

import org.maestro.common.xunit.Failure;
import org.maestro.common.xunit.TestCase;
import org.maestro.common.xunit.TestSuite;
import org.maestro.common.xunit.TestSuites;
import org.maestro.common.xunit.writer.XunitWriter;
import org.maestro.reports.dao.exceptions.DataNotFoundException;
import org.maestro.results.dao.TestDao;
import org.maestro.results.dao.TestResultsDao;
import org.maestro.results.dto.Test;
import org.maestro.results.dto.TestResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.File;
import java.time.Duration;
import java.util.List;

public class XUnitGenerator {
    private static final Logger logger = LoggerFactory.getLogger(XUnitGenerator.class);
    private TestResultsDao testResultsDao = new TestResultsDao();
    private TestDao testDao = new TestDao();

    public TestSuites convertToTestSuites(final Test test) throws DataNotFoundException {
        TestSuite testSuite = new TestSuite();
        testSuite.setId("1");
        testSuite.setTests(1);


        List<TestResult> testResultList = null;
        try {
            testResultList = testResultsDao.fetch(test.getTestId(), test.getTestNumber());
        } catch (DataNotFoundException e) {
            logger.error("There are no results with test ID {} and test number {}", test.getTestId(),
                    test.getTestNumber());

            throw e;
        }

        TestCase testCase = new TestCase();

        testCase.setAssertions(1);
        testCase.setClassName("singlepoint.FixedRate");

        TestResult testResult = testResultList.get(0);

        String name = testResult.getMessagingProtocol() + (testResult.isDurable() ? "-" : "-non-") + "durable" +
                "-ld-" + testResult.getLimitDestinations() + "-s-" + testResult.getMessageSize() +
                (testResult.isVariableSize() ? "-variable-size" : "-non-variable-size") + "-c-" +
                testResult.getConnectionCount();

        testCase.setName(name);
        testCase.setTime(Duration.ofSeconds(test.getTestDuration()));

        for (TestResult others : testResultList) {
            if (others.getTestResult() == "failure") {
                Failure failure = new Failure();

                testCase.setFailure(failure);
            }
        }

        testSuite.getTestCaseList().add(testCase);

        TestSuites testSuites = new TestSuites();
        testSuites.getTestSuiteList().add(testSuite);

        return testSuites;
    }


    public int generate(final File fileName, int testId, int testNumber) {
        try {
            Test test = testDao.fetch(testId, testNumber);

            TestSuites testSuites = convertToTestSuites(test);

            XunitWriter xunitWriter = new XunitWriter();

            xunitWriter.saveToXML(fileName, testSuites);

            return 0;
        }
        catch (DataNotFoundException e) {
            logger.error("There are no records matching the parameters", e);

            return 1;
        }


    }
}
