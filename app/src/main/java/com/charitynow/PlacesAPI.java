package com.charitynow;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import org.json.JSONObject;

import java.io.File;
import java.util.Date;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit.mime.TypedFile;

interface PlacesAPI {

    final String token = "Token insert token here";

    @GET("/discover/explore")
//    JsonElement places();
    void places(@Query("at") String at, @Query("cat") String cat, @Query("app_id") String app_id, @Query("app_code") String app_code, Callback<JsonElement> response);

    @FormUrlEncoded
    @POST("/login/")
    void login(@Field("username") String username, @Field("password") String password, Callback<JsonElement> response);


    @FormUrlEncoded
    @POST("/registration/")
    void register(
            @Header("Authorization") String token,
            @Field("email") String email,
            @Field("password2") String password2,
            @Field("password1") String password1,
            @Field("username") String username,
            Callback<JsonElement> response
    );

    @GET("/something/")
    JsonElement something(
            @Header("Authorization") String token
    );


    @GET("/something/{guid}")
    JsonElement somethings(
            @Header("Authorization") String token,
            @Path("guid") String guid
    );
}

