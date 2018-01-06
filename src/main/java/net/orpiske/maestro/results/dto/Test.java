package net.orpiske.maestro.results.dto;

import java.util.Date;

public class Test {
    private int testId;
    private String testName;
    private String testResult;
    private int testParameterId;
    private int sutId;
    private String testReportLink;
    private String testDataStorageInfo;
    private String testTags;
    private Date testDate;

    public int getTestId() {
        return testId;
    }

    public void setTestId(int testId) {
        this.testId = testId;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getTestResult() {
        return testResult;
    }

    public void setTestResult(final String testResult) {
        this.testResult = testResult;
    }

    public int getTestParameterId() {
        return testParameterId;
    }

    public void setTestParameterId(int testParameterId) {
        this.testParameterId = testParameterId;
    }

    public int getSutId() {
        return sutId;
    }

    public void setSutId(int sutId) {
        this.sutId = sutId;
    }

    public String getTestReportLink() {
        return testReportLink;
    }

    public void setTestReportLink(String testReportLink) {
        this.testReportLink = testReportLink;
    }

    public String getTestDataStorageInfo() {
        return testDataStorageInfo;
    }

    public void setTestDataStorageInfo(String testDataStorageInfo) {
        this.testDataStorageInfo = testDataStorageInfo;
    }

    public String getTestTags() {
        return testTags;
    }

    public void setTestTags(String testTags) {
        this.testTags = testTags;
    }

    public Date getTestDate() {
        return testDate;
    }

    public void setTestDate(Date testDate) {
        this.testDate = testDate;
    }

    @Override
    public String toString() {
        return "Test{" +
                "testId=" + testId +
                ", testName='" + testName + '\'' +
                ", testResult=" + testResult +
                ", testParameterId=" + testParameterId +
                ", sutId=" + sutId +
                ", testReportLink='" + testReportLink + '\'' +
                ", testDataStorageInfo='" + testDataStorageInfo + '\'' +
                ", testTags='" + testTags + '\'' +
                ", testDate=" + testDate +
                '}';
    }
}
