package org.maestro.results.dto;

@SuppressWarnings("unused")
public class TestFailCondition {
    private int testId;
    private int testNumber;
    private String testFailConditionResourceName;
    private String testFailConditionName;
    private String testFailConditionValue;

    public int getTestId() {
        return testId;
    }

    public void setTestId(int testId) {
        this.testId = testId;
    }

    public int getTestNumber() {
        return testNumber;
    }

    public void setTestNumber(int testNumber) {
        this.testNumber = testNumber;
    }

    public String getTestFailConditionResourceName() {
        return testFailConditionResourceName;
    }

    public void setTestFailConditionResourceName(String testFailConditionResourceName) {
        this.testFailConditionResourceName = testFailConditionResourceName;
    }

    public String getTestFailConditionName() {
        return testFailConditionName;
    }

    public void setTestFailConditionName(String testFailConditionName) {
        this.testFailConditionName = testFailConditionName;
    }

    public String getTestFailConditionValue() {
        return testFailConditionValue;
    }

    public void setTestFailConditionValue(String testFailConditionValue) {
        this.testFailConditionValue = testFailConditionValue;
    }

    @Override
    public String toString() {
        return "TestFailCondition{" +
                "testId=" + testId +
                ", testNumber=" + testNumber +
                ", testFailConditionResourceName='" + testFailConditionResourceName + '\'' +
                ", testFailConditionName='" + testFailConditionName + '\'' +
                ", testFailConditionValue='" + testFailConditionValue + '\'' +
                '}';
    }
}
