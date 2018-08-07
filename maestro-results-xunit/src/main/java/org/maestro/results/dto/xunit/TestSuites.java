package org.maestro.results.dto.xunit;

import java.util.LinkedList;
import java.util.List;

public class TestSuites {
    private List<TestSuite> testSuiteList = new LinkedList<>();
    private Properties properties = new Properties();

    public List<TestSuite> getTestSuiteList() {
        return testSuiteList;
    }

    public Properties getProperties() {
        return properties;
    }
}
