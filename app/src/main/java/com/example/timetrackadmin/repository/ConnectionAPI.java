package com.example.timetrackadmin.repository;

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

    String RegUrl = "http://172.17.20.170:8080/api/user/";

    @POST("register")
    Call<User> registerUser(@Body User user);

    @POST("login")
    @Headers("Content-Type:application/json")
    Call<User> loginUser(@Body RequestBody requestBody);

    @GET("{id}")
    Call<User> getUser(@HeaderMap HashMap<String, String> header, @Path("id") String id);

    @PUT("{id}")
    Call<User> updateUser(@HeaderMap HashMap<String, String> header, @Path("id") String id, @Body User user);

    @DELETE("delete/{id}")
    Call<User> deleteUser(@Path("id") String id);

    @GET("all")
    Call<ArrayList<UsersList>> getUsers(@HeaderMap HashMap<String, String> header);

}
