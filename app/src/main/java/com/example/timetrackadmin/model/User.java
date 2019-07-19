package com.example.timetrackadmin.model;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("id")
    public String id;

    @SerializedName("username")
    public String email;

    @SerializedName("firstName")
    public String firstName;

    @SerializedName("lastName")
    public String lastName;

    @SerializedName("password")
    public String password;

    @SerializedName("token")
    public String token;

    @SerializedName("user")
    public User users;


    @SerializedName("message")
    public String message;


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getUsers() {
        return users;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
