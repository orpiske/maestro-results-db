package net.orpiske.maestro.results.dto;

import java.util.Date;

public class TestResultRecord {
    private String sutName;
    private String sutVersion;
    private String testResult;
    private boolean error;
    private int connectionCount;
    private int limitDestinations;
    private int messageSize;
    private String apiName;
    private String apiVersion;
    private String messagingProtocol;
    private boolean durable;
    private double testRateMin;
    private double testRateMax;
    private double testRateGeometricMean;
    private double testRateStandardDeviation;
    private int testRateSkipCount;
    private Date testDate;
    private String testReportLink;
    private boolean variableSize;
    private String envResourceRole;
    private String testHost;

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

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public int getConnectionCount() {
        return connectionCount;
    }

    public void setConnectionCount(int connectionCount) {
        this.connectionCount = connectionCount;
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

    public String getMessagingProtocol() {
        return messagingProtocol;
    }

    public void setMessagingProtocol(String messagingProtocol) {
        this.messagingProtocol = messagingProtocol;
    }

    public boolean isDurable() {
        return durable;
    }

    public void setDurable(boolean durable) {
        this.durable = durable;
    }

    public double getTestRateMin() {
        return testRateMin;
    }

    public void setTestRateMin(double testRateMin) {
        this.testRateMin = testRateMin;
    }

    public double getTestRateMax() {
        return testRateMax;
    }

    public void setTestRateMax(double testRateMax) {
        this.testRateMax = testRateMax;
    }

    public double getTestRateGeometricMean() {
        return testRateGeometricMean;
    }

    public void setTestRateGeometricMean(double testRateGeometricMean) {
        this.testRateGeometricMean = testRateGeometricMean;
    }

    public double getTestRateStandardDeviation() {
        return testRateStandardDeviation;
    }

    public void setTestRateStandardDeviation(double testRateStandardDeviation) {
        this.testRateStandardDeviation = testRateStandardDeviation;
    }

    public int getTestRateSkipCount() {
        return testRateSkipCount;
    }

    public void setTestRateSkipCount(int testRateSkipCount) {
        this.testRateSkipCount = testRateSkipCount;
    }

    public Date getTestDate() {
        return testDate;
    }

    public void setTestDate(Date testDate) {
        this.testDate = testDate;
    }

    public String getTestReportLink() {
        return testReportLink;
    }

    public void setTestReportLink(String testReportLink) {
        this.testReportLink = testReportLink;
    }

    public boolean isVariableSize() {
        return variableSize;
    }

    public void setVariableSize(boolean variableSize) {
        this.variableSize = variableSize;
    }

    public String getTestHost() {
        return testHost;
    }

    public void setTestHost(String testHost) {
        this.testHost = testHost;
    }

    public String getEnvResourceRole() {
        return envResourceRole;
    }

    public void setEnvResourceRole(String envResourceRole) {
        this.envResourceRole = envResourceRole;
    }

    @Override
    public String toString() {
        return "TestResultRecord{" +
                "sutName='" + sutName + '\'' +
                ", sutVersion='" + sutVersion + '\'' +
                ", testResult='" + testResult + '\'' +
                ", error=" + error +
                ", connectionCount=" + connectionCount +
                ", limitDestinations=" + limitDestinations +
                ", messageSize=" + messageSize +
                ", apiName='" + apiName + '\'' +
                ", apiVersion='" + apiVersion + '\'' +
                ", messagingProtocol='" + messagingProtocol + '\'' +
                ", durable=" + durable +
                ", testRateMin=" + testRateMin +
                ", testRateMax=" + testRateMax +
                ", testRateGeometricMean=" + testRateGeometricMean +
                ", testRateStandardDeviation=" + testRateStandardDeviation +
                ", testRateSkipCount=" + testRateSkipCount +
                ", testDate=" + testDate +
                ", testReportLink='" + testReportLink + '\'' +
                ", variableSize=" + variableSize +
                ", envResourceRole='" + envResourceRole + '\'' +
                ", testHost='" + testHost + '\'' +
                '}';
    }
}
