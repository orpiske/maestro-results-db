package org.maestro.results.main.actions.xunit;

import org.maestro.results.dao.TestDao;
import org.maestro.results.dao.TestResultsDao;
import org.maestro.results.dto.Test;
import org.maestro.results.dto.TestResult;
import org.maestro.results.dto.xunit.Failure;
import org.maestro.results.dto.xunit.TestCase;
import org.maestro.results.dto.xunit.TestSuite;
import org.maestro.results.dto.xunit.TestSuites;
import org.maestro.results.xunit.XunitWriter;

import java.io.File;
import java.time.Duration;
import java.util.List;

public class XUnitGenerator {
    private TestResultsDao testResultsDao = new TestResultsDao();
    private TestDao testDao = new TestDao();

    public TestSuites convertToTestSuites(final Test test) {
        TestSuite testSuite = new TestSuite();
        testSuite.setId("1");
        testSuite.setTests(1);


        List<TestResult> testResultList = testResultsDao.fetch(test.getTestId(), test.getTestNumber());

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
        Test test = testDao.fetch(testId, testNumber);


        TestSuites testSuites = convertToTestSuites(test);

        XunitWriter xunitWriter = new XunitWriter();

        xunitWriter.saveToXML(fileName, testSuites);

        return 0;
    }
}
