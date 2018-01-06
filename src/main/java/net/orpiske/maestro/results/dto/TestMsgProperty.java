package net.orpiske.maestro.results.dto;

@SuppressWarnings("unused")
public class TestMsgProperty {
    private int testId;
    private String testMsgPropertyName;
    private String testMsgPropertyValue;


    public int getTestId() {
        return testId;
    }

    public void setTestId(int testId) {
        this.testId = testId;
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
                ", testMsgPropertyName='" + testMsgPropertyName + '\'' +
                ", testMsgPropertyValue='" + testMsgPropertyValue + '\'' +
                '}';
    }
}
