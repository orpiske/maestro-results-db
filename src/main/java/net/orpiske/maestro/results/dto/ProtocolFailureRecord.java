package net.orpiske.maestro.results.dto;

public class ProtocolFailureRecord {
    private String messagingProtocol;
    private String testResult;
    private int testRecords;

    public String getMessagingProtocol() {
        return messagingProtocol;
    }

    public void setMessagingProtocol(String messagingProtocol) {
        this.messagingProtocol = messagingProtocol;
    }

    public String getTestResult() {
        return testResult;
    }

    public void setTestResult(String testResult) {
        this.testResult = testResult;
    }

    public int getTestRecords() {
        return testRecords;
    }

    public void setTestRecords(int testRecords) {
        this.testRecords = testRecords;
    }
}
