package net.orpiske.maestro.results.dto;

@SuppressWarnings("unused")
public class TestMsgProperty {
    private int testParameterId;
    private String testMsgPropertiesName;
    private String testMsgPropertiesValue;


    public int getTestParameterId() {
        return testParameterId;
    }

    public void setTestParameterId(int testParameterId) {
        this.testParameterId = testParameterId;
    }

    public String getTestMsgPropertiesName() {
        return testMsgPropertiesName;
    }

    public void setTestMsgPropertiesName(String testMsgPropertiesName) {
        this.testMsgPropertiesName = testMsgPropertiesName;
    }

    public String getTestMsgPropertiesValue() {
        return testMsgPropertiesValue;
    }

    public void setTestMsgPropertiesValue(String testMsgPropertiesValue) {
        this.testMsgPropertiesValue = testMsgPropertiesValue;
    }

    @Override
    public String toString() {
        return "TestMsgProperty{" +
                "testParameterId=" + testParameterId +
                ", testMsgPropertiesName='" + testMsgPropertiesName + '\'' +
                ", testMsgPropertiesValue='" + testMsgPropertiesValue + '\'' +
                '}';
    }
}
