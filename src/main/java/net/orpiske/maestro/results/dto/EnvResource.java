package net.orpiske.maestro.results.dto;

@SuppressWarnings("unused")
public class EnvResource {
    private int envResourceId;
    private int envResourceName;
    private String envResourceType;
    private String envResourceOsName;
    private String envResourceOsArch;
    private String envResourceOsVersion;
    private String envResourceOsOther;
    private String envResourceHwName;
    private String envResourceHwModel;
    private String envResourceHwCpu;
    private int envResourceHwRam;
    private String envResourceHwDiskType;
    private String envResourceHwOther;

    public int getEnvResourceId() {
        return envResourceId;
    }

    public void setEnvResourceId(int envResourceId) {
        this.envResourceId = envResourceId;
    }

    public int getEnvResourceName() {
        return envResourceName;
    }

    public void setEnvResourceName(int envResourceName) {
        this.envResourceName = envResourceName;
    }

    public String getEnvResourceType() {
        return envResourceType;
    }

    public void setEnvResourceType(String envResourceType) {
        this.envResourceType = envResourceType;
    }

    public String getEnvResourceOsName() {
        return envResourceOsName;
    }

    public void setEnvResourceOsName(String envResourceOsName) {
        this.envResourceOsName = envResourceOsName;
    }

    public String getEnvResourceOsArch() {
        return envResourceOsArch;
    }

    public void setEnvResourceOsArch(String envResourceOsArch) {
        this.envResourceOsArch = envResourceOsArch;
    }

    public String getEnvResourceOsVersion() {
        return envResourceOsVersion;
    }

    public void setEnvResourceOsVersion(String envResourceOsVersion) {
        this.envResourceOsVersion = envResourceOsVersion;
    }

    public String getEnvResourceOsOther() {
        return envResourceOsOther;
    }

    public void setEnvResourceOsOther(String envResourceOsOther) {
        this.envResourceOsOther = envResourceOsOther;
    }

    public String getEnvResourceHwName() {
        return envResourceHwName;
    }

    public void setEnvResourceHwName(String envResourceHwName) {
        this.envResourceHwName = envResourceHwName;
    }

    public String getEnvResourceHwModel() {
        return envResourceHwModel;
    }

    public void setEnvResourceHwModel(String envResourceHwModel) {
        this.envResourceHwModel = envResourceHwModel;
    }

    public String getEnvResourceHwCpu() {
        return envResourceHwCpu;
    }

    public void setEnvResourceHwCpu(String envResourceHwCpu) {
        this.envResourceHwCpu = envResourceHwCpu;
    }

    public int getEnvResourceHwRam() {
        return envResourceHwRam;
    }

    public void setEnvResourceHwRam(int envResourceHwRam) {
        this.envResourceHwRam = envResourceHwRam;
    }

    public String getEnvResourceHwDiskType() {
        return envResourceHwDiskType;
    }

    public void setEnvResourceHwDiskType(String envResourceHwDiskType) {
        this.envResourceHwDiskType = envResourceHwDiskType;
    }

    public String getEnvResourceHwOther() {
        return envResourceHwOther;
    }

    public void setEnvResourceHwOther(String envResourceHwOther) {
        this.envResourceHwOther = envResourceHwOther;
    }

    @Override
    public String toString() {
        return "EnvResource{" +
                "envResourceId=" + envResourceId +
                ", envResourceName=" + envResourceName +
                ", envResourceType='" + envResourceType + '\'' +
                ", envResourceOsName='" + envResourceOsName + '\'' +
                ", envResourceOsArch='" + envResourceOsArch + '\'' +
                ", envResourceOsVersion='" + envResourceOsVersion + '\'' +
                ", envResourceOsOther='" + envResourceOsOther + '\'' +
                ", envResourceHwName='" + envResourceHwName + '\'' +
                ", envResourceHwModel='" + envResourceHwModel + '\'' +
                ", envResourceHwCpu='" + envResourceHwCpu + '\'' +
                ", envResourceHwRam=" + envResourceHwRam +
                ", envResourceHwDiskType='" + envResourceHwDiskType + '\'' +
                ", envResourceHwOther='" + envResourceHwOther + '\'' +
                '}';
    }
}
