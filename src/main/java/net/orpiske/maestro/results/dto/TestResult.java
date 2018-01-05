package net.orpiske.maestro.results.dto;

public enum TestResult {
    SUCCESS("success"),
    FAILED("failed"),
    ERROR("error");

    private String result;

    TestResult(final String result) {
        this.result = result;
    }
}
