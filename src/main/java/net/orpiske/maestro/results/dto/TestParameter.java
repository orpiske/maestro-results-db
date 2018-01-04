package net.orpiske.maestro.results.dto;


@SuppressWarnings("unused")
public class TestParameter {
    private int testParameterId;
    private int testTargetRate;
    private int testSenderCount;
    private int testReceiverCount;

    public int getTestParameterId() {
        return testParameterId;
    }

    public void setTestParameterId(int testParameterId) {
        this.testParameterId = testParameterId;
    }

    public int getTestTargetRate() {
        return testTargetRate;
    }

    public void setTestTargetRate(int testTargetRate) {
        this.testTargetRate = testTargetRate;
    }

    public int getTestSenderCount() {
        return testSenderCount;
    }

    public void setTestSenderCount(int testSenderCount) {
        this.testSenderCount = testSenderCount;
    }

    public int getTestReceiverCount() {
        return testReceiverCount;
    }

    public void setTestReceiverCount(int testReceiverCount) {
        this.testReceiverCount = testReceiverCount;
    }


    @Override
    public String toString() {
        return "TestParameter{" +
                "testParameterId=" + testParameterId +
                ", testTargetRate=" + testTargetRate +
                ", testSenderCount=" + testSenderCount +
                ", testReceiverCount=" + testReceiverCount +
                '}';
    }
}
