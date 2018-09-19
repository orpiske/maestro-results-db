package org.maestro.results.main.actions.xunit;

import org.maestro.results.dao.TestDao;
import org.maestro.results.dao.TestResultsDao;
import org.maestro.results.dto.Test;
import org.maestro.results.dto.TestResult;
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

    public TestSuites convertToTestSuites(final List<TestResult> testList) {
        TestSuite testSuite = new TestSuite();
        testSuite.setId("1");
        testSuite.setTests(testList.size());

        for (TestResult testResult : testList) {
            Test test = testDao.fetch(testResult.getTestId(), testResult.getTestNumber());

            TestCase testCase = new TestCase();

            testCase.setAssertions(1);
            testCase.setClassName("singlepoint.FixedRate");

            // TODO: move the connection count info to Test
            String name = testResult.getMessagingProtocol() + "-s-" + testResult.getMessageSize() + "-c-" + testResult.getConnectionCount()
                    + (testResult.isDurable() ? "-" : "-non-") + "durable";

            testCase.setName(name);
            testCase.setTime(Duration.ofSeconds(test.getTestDuration()));

            testSuite.getTestCaseList().add(testCase);
        }

        TestSuites testSuites = new TestSuites();
        testSuites.getTestSuiteList().add(testSuite);

        return testSuites;
    }


    public int generate(final File fileName, int testId) {
        List<TestResult> testList = testResultsDao.fetch(testId);

        TestSuites testSuites = convertToTestSuites(testList);

        XunitWriter xunitWriter = new XunitWriter();

        xunitWriter.saveToXML(fileName, testSuites);

        return 0;
    }
}
