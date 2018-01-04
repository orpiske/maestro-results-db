package net.orpiske.maestro.results.dto;

@SuppressWarnings("unused")
public class TestFailCondition {
    private int testParameterId;
    private String testFailConditionName;
    private String testFailConditionValue;

    public int getTestParameterId() {
        return testParameterId;
    }

    public void setTestParameterId(int testParameterId) {
        this.testParameterId = testParameterId;
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
                "testParameterId=" + testParameterId +
                ", testFailConditionName='" + testFailConditionName + '\'' +
                ", testFailConditionValue='" + testFailConditionValue + '\'' +
                '}';
    }
}
