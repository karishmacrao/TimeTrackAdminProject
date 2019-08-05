package com.example.timetrackadmin.model;

import com.google.gson.annotations.SerializedName;

public class Project {
    @SerializedName("id")
    public String id;

    @SerializedName("projectId")
    public String projectCode;

    @SerializedName("projectName")
    public String projectName;

    @SerializedName("companyId")
    public String companyId;

    @SerializedName("projectHead")
    public String projectHead;

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getProjectHead() {
        return projectHead;
    }

    public void setProjectHead(String projectHead) {
        this.projectHead = projectHead;
    }

    public String getId() {
        return id;
    }
}
