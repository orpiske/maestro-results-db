package net.orpiske.maestro.results.dto;

@SuppressWarnings("unused")
public class TestMsgProperty {
    private int testParameterId;
    private String testMsgPropertyName;
    private String testMsgPropertyValue;


    public int getTestParameterId() {
        return testParameterId;
    }

    public void setTestParameterId(int testParameterId) {
        this.testParameterId = testParameterId;
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
                "testParameterId=" + testParameterId +
                ", testMsgPropertyName='" + testMsgPropertyName + '\'' +
                ", testMsgPropertyValue='" + testMsgPropertyValue + '\'' +
                '}';
    }
}
