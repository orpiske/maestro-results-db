package net.orpiske.maestro.results.dto;

public class EnvResults {
    private int envResultsId;
    private String envName;
    private int envResourceId;
    private EnvResourceRole envResourceRole;
    private int testRateMin;
    private int testRateMax;
    private int testRateErrorCount;
    private int testRateSamples;
    private double testRateGeometricMean;
    private double testRateStandardDeviation;
    private int testRateSkipCount;
    private boolean error;


    public int getEnvResultsId() {
        return envResultsId;
    }

    public void setEnvResultsId(int envResultsId) {
        this.envResultsId = envResultsId;
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

    public EnvResourceRole getEnvResourceRole() {
        return envResourceRole;
    }

    public void setEnvResourceRole(EnvResourceRole envResourceRole) {
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

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "EnvResults{" +
                "envResultsId=" + envResultsId +
                ", envName='" + envName + '\'' +
                ", envResourceId=" + envResourceId +
                ", envResourceRole=" + envResourceRole +
                ", testRateMin=" + testRateMin +
                ", testRateMax=" + testRateMax +
                ", testRateErrorCount=" + testRateErrorCount +
                ", testRateSamples=" + testRateSamples +
                ", testRateGeometricMean=" + testRateGeometricMean +
                ", testRateStandardDeviation=" + testRateStandardDeviation +
                ", testRateSkipCount=" + testRateSkipCount +
                ", error=" + error +
                '}';
    }
}