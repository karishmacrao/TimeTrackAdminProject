package com.example.timetrackadmin.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class UsersList {

    @SerializedName("_id")
    String id;
    @SerializedName("username")
    String email;
    @SerializedName("firstName")
    String firstName;
    @SerializedName("lastName")
    String lastName;

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
