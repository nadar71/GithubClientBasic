package com.mapelli.simone.githubclient.network;

import com.mapelli.simone.githubclient.data.entity.UserProfile_Full;
import com.mapelli.simone.githubclient.data.entity.UserProfile_Mini;
import com.mapelli.simone.githubclient.data.entity.UserProfile_Mini_List;
import com.mapelli.simone.githubclient.data.entity.UserRepository;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface NetworkService {


    //----------------------------------------------------------------------------------------------
    //                               MAIN REQUESTS
    //----------------------------------------------------------------------------------------------

    // get result from free search users and only get info needed for UserProfile_Mini
    // https://api.github.com/search/users?q=simone
    @GET("/search/users")
    Call<UserProfile_Mini_List> usersListSearch(@Query("q") String searchKeyword);

    // get specific user profile from user login field
    // https://api.github.com/users/nadar71
    @GET("/users/{login}")
    Call<UserProfile_Full> userProfileByUserLogin(@Path("login") String login);


    // get repositories list for a specific user; default : full_name asc
    // https://api.github.com/users/nadar71/repos
    @GET("/users/{user}/repos")
    Call<List<UserRepository>> reposForuser(@Path("user") String user);



    //----------------------------------------------------------------------------------------------
    //                               REPO QUERIES REQUESTS
    //----------------------------------------------------------------------------------------------

    // repositories with direction full_name desc
    // https://api.github.com/users/nadar71/repos?direction=desc
    @GET("/users/{user}/repos")
    Call<List<UserRepository>> reposForuser_name_desc(@Path("user") String user,
                                               @Query("direction") String direction);


    // repositories sort=created  and direction=asc
    // https://api.github.com/users/nadar71/repos?sort=created&direction=asc
    @GET("/users/{user}/repos")
    Call<List<UserRepository>> reposForuser_created_asc(@Path("user") String user,
                                                       @Query("sort") String created,
                                                       @Query("direction") String direction);


    // repositories sort=created  and direction=desc
    // https://api.github.com/users/nadar71/repos?sort=created&direction=desc
    @GET("/users/{user}/repos")
    Call<List<UserRepository>> reposForuser_created_desc(@Path("user") String user,
                                                       @Query("sort") String created,
                                                       @Query("direction") String direction);


    // repositories sort=updated  and direction=asc
    // https://api.github.com/users/nadar71/repos?sort=updated&direction=asc
    @GET("/users/{user}/repos")
    Call<List<UserRepository>> reposForuser_updated_asc(@Path("user") String user,
                                                       @Query("sort") String updated,
                                                       @Query("direction") String direction);


    // repositories sort=updated  and direction=desc
    // https://api.github.com/users/nadar71/repos?sort=updated&direction=desc
    @GET("/users/{user}/repos")
    Call<List<UserRepository>> eposForuser_updated_desc(@Path("user") String user,
                                                       @Query("sort") String updated,
                                                       @Query("direction") String direction);




    // repositories sort=pushed  and direction=asc
    // https://api.github.com/users/nadar71/repos?sort=pushed&direction=asc
    @GET("/users/{user}/repos")
    Call<List<UserRepository>> reposForuser_pushed_asc(@Path("user") String user,
                                                       @Query("sort") String pushed,
                                                       @Query("direction") String direction);



    // repositories sort=pushed  and direction=desc
    // https://api.github.com/users/nadar71/repos?sort=pushed&direction=desc
    @GET("/users/{user}/repos")
    Call<List<UserRepository>> reposForuser_pushed_desc(@Path("user") String user,
                                                       @Query("sort") String pushed,
                                                       @Query("direction") String direction);



}
