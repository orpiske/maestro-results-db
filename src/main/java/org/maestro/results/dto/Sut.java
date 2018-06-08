package org.maestro.results.dto;

public class Sut {
    private int sutId;
    private String sutName;
    private String sutVersion;
    private String sutJvmInfo;
    private String sutOther;
    private String sutTags;

    public int getSutId() {
        return sutId;
    }

    public void setSutId(int sutId) {
        this.sutId = sutId;
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

    public String getSutJvmInfo() {
        return sutJvmInfo;
    }

    public void setSutJvmInfo(String sutJvmInfo) {
        this.sutJvmInfo = sutJvmInfo;
    }

    public String getSutOther() {
        return sutOther;
    }

    public void setSutOther(String sutOther) {
        this.sutOther = sutOther;
    }

    public String getSutTags() {
        return sutTags;
    }

    public void setSutTags(String sutTags) {
        this.sutTags = sutTags;
    }

    @Override
    public String toString() {
        return "Sut{" +
                "sutId=" + sutId +
                ", sutName='" + sutName + '\'' +
                ", sutVersion='" + sutVersion + '\'' +
                ", sutJvmInfo='" + sutJvmInfo + '\'' +
                ", sutOther='" + sutOther + '\'' +
                ", sutTags='" + sutTags + '\'' +
                '}';
    }
}
