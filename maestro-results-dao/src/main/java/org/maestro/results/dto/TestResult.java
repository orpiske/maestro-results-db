package org.maestro.results.dto;

import java.util.Date;

public class TestResult {
    private int testId;
    private int testNumber;
    private int sutId;
    private String sutTags;
    private String sutName;
    private String sutVersion;
    private String testResult;
    private boolean error;
    private boolean testValid;
    private String envResourceName;
    private String envResourceRole;
    private double testRateMin;
    private double testRateMax;
    private double testRateGeometricMean;
    private double testRateStandardDeviation;
    private int testRateSkipCount;
    private double latPercentile90;
    private double latPercentile95;
    private double latPercentile99;
    private int connectionCount;
    private Date testDate;
    private String testName;
    private String testTags;
    private String testReportLink;
    private boolean variableSize;

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

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public boolean isTestValid() {
        return testValid;
    }

    public void setTestValid(boolean testValid) {
        this.testValid = testValid;
    }

    public String getEnvResourceName() {
        return envResourceName;
    }

    public void setEnvResourceName(String envResourceName) {
        this.envResourceName = envResourceName;
    }

    public String getEnvResourceRole() {
        return envResourceRole;
    }

    public void setEnvResourceRole(String envResourceRole) {
        this.envResourceRole = envResourceRole;
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

    public double getLatPercentile90() {
        return latPercentile90;
    }

    public void setLatPercentile90(double latPercentile90) {
        this.latPercentile90 = latPercentile90;
    }

    public double getLatPercentile95() {
        return latPercentile95;
    }

    public void setLatPercentile95(double latPercentile95) {
        this.latPercentile95 = latPercentile95;
    }

    public double getLatPercentile99() {
        return latPercentile99;
    }

    public void setLatPercentile99(double latPercentile99) {
        this.latPercentile99 = latPercentile99;
    }

    public int getConnectionCount() {
        return connectionCount;
    }

    public void setConnectionCount(int connectionCount) {
        this.connectionCount = connectionCount;
    }

    public Date getTestDate() {
        return testDate;
    }

    public void setTestDate(Date testDate) {
        this.testDate = testDate;
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

    @Override
    public String toString() {
        return "TestResult{" +
                "testId=" + testId +
                ", testNumber=" + testNumber +
                ", sutId=" + sutId +
                ", sutTags='" + sutTags + '\'' +
                ", sutName='" + sutName + '\'' +
                ", sutVersion='" + sutVersion + '\'' +
                ", testResult='" + testResult + '\'' +
                ", error=" + error +
                ", testValid=" + testValid +
                ", envResourceName='" + envResourceName + '\'' +
                ", envResourceRole='" + envResourceRole + '\'' +
                ", testRateMin=" + testRateMin +
                ", testRateMax=" + testRateMax +
                ", testRateGeometricMean=" + testRateGeometricMean +
                ", testRateStandardDeviation=" + testRateStandardDeviation +
                ", testRateSkipCount=" + testRateSkipCount +
                ", latPercentile90=" + latPercentile90 +
                ", latPercentile95=" + latPercentile95 +
                ", latPercentile99=" + latPercentile99 +
                ", connectionCount=" + connectionCount +
                ", testDate=" + testDate +
                ", testName='" + testName + '\'' +
                ", testTags='" + testTags + '\'' +
                ", testReportLink='" + testReportLink + '\'' +
                ", variableSize=" + variableSize +
                '}';
    }
}
