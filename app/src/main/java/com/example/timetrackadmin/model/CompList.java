package com.example.timetrackadmin.model;

import com.google.gson.annotations.SerializedName;

public class CompList {
    @SerializedName("id")
    public String id;

    @SerializedName("companyName")
    public String companyName;

    @SerializedName("location")
    public String location;

    @SerializedName("companyId")
    public String companyCode;


    public String getCompanyName() {
        return companyName;
    }

    public String getId() {
        return id;
    }
    public String getLocation() {
        return location;
    }
    public String getCompanyCode() {
        return companyCode;
    }
}
