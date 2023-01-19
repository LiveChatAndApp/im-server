package cn.wildfirechat.pojos;

public class SystemVersionDto {
    private String projectCode;
    private String projectVersion;

    public SystemVersionDto() {
    }

    public SystemVersionDto(String projectCode, String projectVersion) {
        this.projectCode = projectCode;
        this.projectVersion = projectVersion;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getProjectVersion() {
        return projectVersion;
    }

    public void setProjectVersion(String projectVersion) {
        this.projectVersion = projectVersion;
    }
}
