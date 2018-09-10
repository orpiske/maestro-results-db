package org.maestro.results.dto;

import java.util.Date;

public class TestSutPropertiesLink {
    private int testId;
    private int testNumber;
    private int sutId;
    private String sutTags;
    private String sutName;
    private String sutVersion;
    private String testResult;
    private int connectionCount;
    private String testName;
    private String testTags;
    private int testTargetRate;
    private String apiName;
    private String apiVersion;
    private boolean durable;
    private int limitDestinations;
    private int messageSize;
    private String messagingProtocol;
    private int maxAcceptableLatency;
    private Date testDate;

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

    public int getSutId() {
        return sutId;
    }

    public void setSutId(int sutId) {
        this.sutId = sutId;
    }

    public String getSutTags() {
        return sutTags;
    }

    public void setSutTags(String sutTags) {
        this.sutTags = sutTags;
    }

    public String getSutName() {
        return sutName;
    }

    public void setSutName(String sutName) {
        this.sutName = sutName;
    }

    public String getSutVersion() {
        return sutVersion;
    }

    public void setSutVersion(String sutVersion) {
        this.sutVersion = sutVersion;
    }

    public String getTestResult() {
        return testResult;
    }

    public void setTestResult(String testResult) {
        this.testResult = testResult;
    }

    public int getConnectionCount() {
        return connectionCount;
    }

    public void setConnectionCount(int connectionCount) {
        this.connectionCount = connectionCount;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getTestTags() {
        return testTags;
    }

    public void setTestTags(String testTags) {
        this.testTags = testTags;
    }

    public int getTestTargetRate() {
        return testTargetRate;
    }

    public void setTestTargetRate(int testTargetRate) {
        this.testTargetRate = testTargetRate;
    }

    public String getApiName() {
        return apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    public String getApiVersion() {
        return apiVersion;
    }

    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
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

    public int getMaxAcceptableLatency() {
        return maxAcceptableLatency;
    }

    public void setMaxAcceptableLatency(int maxAcceptableLatency) {
        this.maxAcceptableLatency = maxAcceptableLatency;
    }

    public Date getTestDate() {
        return testDate;
    }

    public void setTestDate(Date testDate) {
        this.testDate = testDate;
    }

    @Override
    public String toString() {
        return "TestSutPropertiesLink{" +
                "testId=" + testId +
                ", testNumber=" + testNumber +
                ", sutId=" + sutId +
                ", sutTags='" + sutTags + '\'' +
                ", sutName='" + sutName + '\'' +
                ", sutVersion='" + sutVersion + '\'' +
                ", testResult='" + testResult + '\'' +
                ", connectionCount=" + connectionCount +
                ", testName='" + testName + '\'' +
                ", testTags='" + testTags + '\'' +
                ", testTargetRate=" + testTargetRate +
                ", apiName='" + apiName + '\'' +
                ", apiVersion='" + apiVersion + '\'' +
                ", durable=" + durable +
                ", limitDestinations=" + limitDestinations +
                ", messageSize=" + messageSize +
                ", messagingProtocol='" + messagingProtocol + '\'' +
                ", maxAcceptableLatency=" + maxAcceptableLatency +
                ", testDate=" + testDate +
                '}';
    }
}
