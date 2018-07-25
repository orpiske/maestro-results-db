package org.maestro.results.dto.xunit;

import java.util.LinkedList;
import java.util.List;

public class TestSuite {
    private static final String name = "Maestro";

    private List<TestCase> testCaseList = new LinkedList<>();
    private String id;
    private int tests;

    public List<TestCase> getTestCaseList() {
        return testCaseList;
    }

    public static String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getTests() {
        return tests;
    }

    public void setTests(int tests) {
        this.tests = tests;
    }
}
