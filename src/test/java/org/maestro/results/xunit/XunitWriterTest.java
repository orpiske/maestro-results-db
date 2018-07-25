package org.maestro.results.xunit;

import org.junit.Test;
import org.maestro.results.dto.xunit.TestCase;
import org.maestro.results.dto.xunit.TestSuite;
import org.maestro.results.dto.xunit.TestSuites;

import java.io.File;
import java.time.Duration;
import static org.junit.Assert.*;


public class XunitWriterTest {

    @Test
    public void testSaveXml() {
        String path = this.getClass().getResource("/").getPath() + "xunit.test.xml";

        TestCase testCase = new TestCase();
        testCase.setAssertions(1);
        testCase.setClassName("singlepoint.FixedRate");
        testCase.setName("Fixed Rate");
        testCase.setTime(Duration.ofSeconds(300));

        TestSuite testSuite = new TestSuite();
        testSuite.setId("0");
        testSuite.setTests(1);
        testSuite.getTestCaseList().add(testCase);

        TestSuites testSuites = new TestSuites();
        testSuites.getTestSuiteList().add(testSuite);

        XunitWriter xunitWriter = new XunitWriter();
        File outFile = new File(path);

        xunitWriter.saveToXML(outFile, testSuites);

        assertTrue(outFile.exists());
    }


}
