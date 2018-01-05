package net.orpiske.maestro.results.dto;

public enum EnvResourceRole {
    SENDER("sender"),
    RECEIVER("receiver"),
    INSPECTOR("inspector"),
    OTHER("other");

    private String role;

    EnvResourceRole(final String role) {
        this.role = role;
    }


    @Override
    public String toString() {
        return "EnvResourceRole{" +
                "role='" + role + '\'' +
                "} " + super.toString();
    }
}
