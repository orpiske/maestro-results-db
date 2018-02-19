package net.orpiske.maestro.results.dto;

public class TestResultMetricRecord {
    public String sutName;
    public String sutVersion;
    public int total;
    public int passedCount;
    public int failedCount;

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

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPassedCount() {
        return passedCount;
    }

    public void setPassedCount(int passedCount) {
        this.passedCount = passedCount;
    }

    public int getFailedCount() {
        return failedCount;
    }

    public void setFailedCount(int failedCount) {
        this.failedCount = failedCount;
    }
}
