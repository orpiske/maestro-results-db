package net.orpiske.maestro.results.dto;

@SuppressWarnings("unused")
public class TestMsgProperty {
    private int testId;
    private int testNumber;
    private String testMsgPropertyResourceName;
    private String testMsgPropertyName;
    private String testMsgPropertyValue;


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

    public String getTestMsgPropertyResourceName() {
        return testMsgPropertyResourceName;
    }

    public void setTestMsgPropertyResourceName(String testMsgPropertyResourceName) {
        this.testMsgPropertyResourceName = testMsgPropertyResourceName;
    }

    public String getTestMsgPropertyName() {
        return testMsgPropertyName;
    }

    public void setTestMsgPropertyName(String testMsgPropertyName) {
        this.testMsgPropertyName = testMsgPropertyName;
    }

    public String getTestMsgPropertyValue() {
        return testMsgPropertyValue;
    }

    public void setTestMsgPropertyValue(String testMsgPropertyValue) {
        this.testMsgPropertyValue = testMsgPropertyValue;
    }

    @Override
    public String toString() {
        return "TestMsgProperty{" +
                "testId=" + testId +
                ", testNumber=" + testNumber +
                ", testMsgPropertyResourceName='" + testMsgPropertyResourceName + '\'' +
                ", testMsgPropertyName='" + testMsgPropertyName + '\'' +
                ", testMsgPropertyValue='" + testMsgPropertyValue + '\'' +
                '}';
    }
}
