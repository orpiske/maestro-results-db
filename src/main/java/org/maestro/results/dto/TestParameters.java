package org.maestro.results.dto;

public class TestParameters {
    private int testId;
    private int testNumber;
    private String apiName;
    private boolean durable;
    private int limitDestinations;
    private int messageSize;
    private String messagingProtocol;
    private boolean variableSize;
    private int maxAcceptableLatency;

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

    public String getApiName() {
        return apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    public boolean isDurable() {
        return durable;
    }

    public void setDurable(boolean durable) {
        this.durable = durable;
    }

    public int getLimitDestinations() {
        return limitDestinations;
    }

    public void setLimitDestinations(int limitDestinations) {
        this.limitDestinations = limitDestinations;
    }

    public int getMessageSize() {
        return messageSize;
    }

    public void setMessageSize(int messageSize) {
        this.messageSize = messageSize;
    }

    public String getMessagingProtocol() {
        return messagingProtocol;
    }

    public void setMessagingProtocol(String messagingProtocol) {
        this.messagingProtocol = messagingProtocol;
    }

    public boolean isVariableSize() {
        return variableSize;
    }

    public void setVariableSize(boolean variableSize) {
        this.variableSize = variableSize;
    }

    public int getMaxAcceptableLatency() {
        return maxAcceptableLatency;
    }

    public void setMaxAcceptableLatency(int maxAcceptableLatency) {
        this.maxAcceptableLatency = maxAcceptableLatency;
    }
}
