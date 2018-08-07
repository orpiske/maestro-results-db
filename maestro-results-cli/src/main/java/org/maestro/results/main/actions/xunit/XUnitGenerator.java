package org.maestro.results.main.actions.xunit;

import org.maestro.results.dao.TestDao;
import org.maestro.results.dao.TestParametersDao;
import org.maestro.results.dto.Test;
import org.maestro.results.dto.TestParameters;
import org.maestro.results.dto.xunit.TestCase;
import org.maestro.results.dto.xunit.TestSuite;
import org.maestro.results.dto.xunit.TestSuites;
import org.maestro.results.xunit.XunitWriter;

import java.io.File;
import java.time.Duration;
import java.util.List;

public class XUnitGenerator {
    private TestDao testDao = new TestDao();
    private TestParametersDao testParametersDao = new TestParametersDao();

    public TestSuites convertToTestSuites(final List<Test> testList) {
        TestSuite testSuite = new TestSuite();
        testSuite.setId("1");
        testSuite.setTests(testList.size());

        for (Test test : testList) {
            TestParameters testParameters = testParametersDao.fetch(test.getTestId(), test.getTestNumber());

            TestCase testCase = new TestCase();

            testCase.setAssertions(1);
            testCase.setClassName("singlepoint.FixedRate");

            // TODO: move the connection count info to Test
            String name = testParameters.getMessagingProtocol() + "-s-" + testParameters.getMessageSize() + "-c-undef"
                    + (testParameters.isDurable() ? "-" : "-non-") + "durable-";

            testCase.setName(name);
            testCase.setTime(Duration.ofSeconds(test.getTestDuration()));

            testSuite.getTestCaseList().add(testCase);
        }

        TestSuites testSuites = new TestSuites();
        testSuites.getTestSuiteList().add(testSuite);

        return testSuites;
    }


    public int generate(final File fileName, int testId) {
        List<Test> testList = testDao.fetch(testId);

        TestSuites testSuites = convertToTestSuites(testList);

        XunitWriter xunitWriter = new XunitWriter();
        xunitWriter.saveToXML(fileName, testSuites);

        return 0;
    }
}
