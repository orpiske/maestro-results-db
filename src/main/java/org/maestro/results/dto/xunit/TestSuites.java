package org.maestro.results.dto.xunit;

import java.util.LinkedList;
import java.util.List;

public class TestSuites {
    private List<TestSuite> testSuiteList = new LinkedList<>();
    private Properties properties;

    public List<TestSuite> getTestSuiteList() {
        return testSuiteList;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }
}
