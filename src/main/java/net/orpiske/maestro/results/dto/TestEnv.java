package net.orpiske.maestro.results.dto;

@SuppressWarnings("unused")
public class TestEnv {
    private int testEnvId;
    private int envResourceId;

    public int getTestEnvId() {
        return testEnvId;
    }

    public void setTestEnvId(int testEnvId) {
        this.testEnvId = testEnvId;
    }

    public int getEnvResourceId() {
        return envResourceId;
    }

    public void setEnvResourceId(int envResourceId) {
        this.envResourceId = envResourceId;
    }

    @Override
    public String toString() {
        return "TestEnv{" +
                "testEnvId=" + testEnvId +
                ", envResourceId=" + envResourceId +
                '}';
    }
}
