package net.orpiske.maestro.results.dto;

@SuppressWarnings("unused")
public class TestFailCondition {
    private int testId;
    private String testFailConditionName;
    private String testFailConditionValue;

    public int getTestId() {
        return testId;
    }

    public void setTestId(int testId) {
        this.testId = testId;
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
                ", testFailConditionName='" + testFailConditionName + '\'' +
                ", testFailConditionValue='" + testFailConditionValue + '\'' +
                '}';
    }
}
