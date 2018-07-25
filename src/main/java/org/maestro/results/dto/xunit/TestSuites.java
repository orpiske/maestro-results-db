package org.maestro.results.dto.xunit;

import java.util.LinkedList;
import java.util.List;

public class TestSuites {
    private List<TestSuite> testSuiteList = new LinkedList<>();

    public List<TestSuite> getTestSuiteList() {
        return testSuiteList;
    }
}
