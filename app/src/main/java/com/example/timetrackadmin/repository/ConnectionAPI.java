package com.example.timetrackadmin.repository;

import com.example.timetrackadmin.model.CompList;
import com.example.timetrackadmin.model.Company;
import com.example.timetrackadmin.model.Project;
import com.example.timetrackadmin.model.UsersList;
import com.example.timetrackadmin.model.User;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ConnectionAPI {

    String RegUrl = "http://172.17.20.170:8080/api/";

    @POST("user/register")
    Call<User> registerUser(@Body User user);

    @POST("user/login")
    @Headers("Content-Type:application/json")
    Call<User> loginUser(@Body RequestBody requestBody);

    @GET("user/getemp/{id}")
    Call<User> getUser(@HeaderMap HashMap<String, String> header, @Path("id") String id);

    @PUT("user/{id}")
    Call<User> updateUser(@HeaderMap HashMap<String, String> header, @Path("id") String id, @Body User user);

    @DELETE("user/delete/{id}")
    Call<User> deleteUser(@HeaderMap HashMap<String, String> header, @Path("id") String id);

    @GET("user/all")
    Call<ArrayList<UsersList>> getUsers(@HeaderMap HashMap<String, String> header);


    @POST("company/addcompany")
    Call<Company> addCompany(@HeaderMap HashMap<String, String> header, @Body Company company);

    @GET("company/allcompany")
    Call<ArrayList<CompList>> getCompanys(@HeaderMap HashMap<String, String> header);

    @DELETE("company/delete/{id}")
    Call<Company> deleteCompany(@HeaderMap HashMap<String, String> header, @Path("id") String id);


    @POST("project/addproject")
    Call<Project> addproject(@HeaderMap HashMap<String, String> header, @Body Project project);

    @GET("project/allproject")
    Call<ArrayList<Project>> getAllProject(@HeaderMap HashMap<String, String> header);

    @DELETE("project/delete/{id}")
    Call<Project> deleteProject(@HeaderMap HashMap<String, String> header, @Path("id") String id);

    @DELETE("project/deleteall")
    Call<Project> deleteAllProjectOfCompany(@HeaderMap HashMap<String, String> header);

    @GET("project/getproject/{id}")
    Call<ArrayList<Project>> getProject(@HeaderMap HashMap<String, String> header);


}
