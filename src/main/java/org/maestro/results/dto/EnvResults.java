package org.maestro.results.dto;

public class EnvResults {
    private int envResultsId;
    private int envResourceId;
    private int testId;
    private int testNumber;

    private String envName;
    private String envResourceRole;
    private int testRateMin;
    private int testRateMax;
    private int testRateErrorCount;
    private int testRateSamples;
    private double testRateGeometricMean;
    private double testRateStandardDeviation;
    private int testRateSkipCount;
    private int connectionCount;
    private double latPercentile90;
    private double latPercentile95;
    private double latPercentile99;
    private boolean error;

    public int getEnvResultsId() {
        return envResultsId;
    }

    public void setEnvResultsId(int envResultsId) {
        this.envResultsId = envResultsId;
    }

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

    public String getEnvName() {
        return envName;
    }

    public void setEnvName(String envName) {
        this.envName = envName;
    }

    public int getEnvResourceId() {
        return envResourceId;
    }

    public void setEnvResourceId(int envResourceId) {
        this.envResourceId = envResourceId;
    }

    public String getEnvResourceRole() {
        return envResourceRole;
    }

    public void setEnvResourceRole(final String envResourceRole) {
        this.envResourceRole = envResourceRole;
    }

    public int getTestRateMin() {
        return testRateMin;
    }

    public void setTestRateMin(int testRateMin) {
        this.testRateMin = testRateMin;
    }

    public int getTestRateMax() {
        return testRateMax;
    }

    public void setTestRateMax(int testRateMax) {
        this.testRateMax = testRateMax;
    }

    public int getTestRateErrorCount() {
        return testRateErrorCount;
    }

    public void setTestRateErrorCount(int testRateErrorCount) {
        this.testRateErrorCount = testRateErrorCount;
    }

    public int getTestRateSamples() {
        return testRateSamples;
    }

    public void setTestRateSamples(int testRateSamples) {
        this.testRateSamples = testRateSamples;
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

    public int getConnectionCount() {
        return connectionCount;
    }

    public void setConnectionCount(int connectionCount) {
        this.connectionCount = connectionCount;
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
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

    @Override
    public String toString() {
        return "EnvResults{" +
                "envResultsId=" + envResultsId +
                ", envResourceId=" + envResourceId +
                ", testId=" + testId +
                ", testNumber=" + testNumber +
                ", envName='" + envName + '\'' +
                ", envResourceRole='" + envResourceRole + '\'' +
                ", testRateMin=" + testRateMin +
                ", testRateMax=" + testRateMax +
                ", testRateErrorCount=" + testRateErrorCount +
                ", testRateSamples=" + testRateSamples +
                ", testRateGeometricMean=" + testRateGeometricMean +
                ", testRateStandardDeviation=" + testRateStandardDeviation +
                ", testRateSkipCount=" + testRateSkipCount +
                ", connectionCount=" + connectionCount +
                ", latPercentile90=" + latPercentile90 +
                ", latPercentile95=" + latPercentile95 +
                ", latPercentile99=" + latPercentile99 +
                ", error=" + error +
                '}';
    }
}
