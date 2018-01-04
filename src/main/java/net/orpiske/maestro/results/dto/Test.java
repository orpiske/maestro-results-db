package net.orpiske.maestro.results.dto;

public class Test {
    private int testId;
    private String testName;
    private String testResult;
    private int testEnvId;
    private int testParameterId;

    public int getTestId() {
        return testId;
    }

    public void setTestId(int testId) {
        this.testId = testId;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getTestResult() {
        return testResult;
    }

    public void setTestResult(String testResult) {
        this.testResult = testResult;
    }

    public int getTestEnvId() {
        return testEnvId;
    }

    public void setTestEnvId(int testEnvId) {
        this.testEnvId = testEnvId;
    }

    public int getTestParameterId() {
        return testParameterId;
    }

    public void setTestParameterId(int testParameterId) {
        this.testParameterId = testParameterId;
    }

    @Override
    public String toString() {
        return "Test{" +
                "testId=" + testId +
                ", testName='" + testName + '\'' +
                ", testResult='" + testResult + '\'' +
                ", testEnvId=" + testEnvId +
                ", testParameterId=" + testParameterId +
                '}';
    }
}
