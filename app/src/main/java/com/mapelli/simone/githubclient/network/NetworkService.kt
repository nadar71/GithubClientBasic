package com.mapelli.simone.githubclient.network

import com.mapelli.simone.githubclient.data.entity.UserProfile_Full
import com.mapelli.simone.githubclient.data.entity.UserProfile_Mini_List
import com.mapelli.simone.githubclient.data.entity.UserRepository

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NetworkService {


    //----------------------------------------------------------------------------------------------
    //                               MAIN REQUESTS
    //----------------------------------------------------------------------------------------------

    // get result from free search users loading next pages sequentially
    // https://api.github.com/search/users?q=simone&page=2&per_page=100
    @GET("/search/users")
    fun usersListSearch_Paging(@Query("q") searchKeyword: String,
                               @Query("page") page_num: String,
                               @Query("per_page") res_x_page: String): Call<UserProfile_Mini_List>


    // get specific user profile from user login field
    // https://api.github.com/users/nadar71
    @GET("/users/{login}")
    fun userProfileByUserLogin(@Path("login") login: String): Call<UserProfile_Full>


    //----------------------------------------------------------------------------------------------
    //                               REPO QUERIES REQUESTS
    //----------------------------------------------------------------------------------------------

    // repositories with direction full_name desc
    // https://api.github.com/users/nadar71/repos?direction=desc
    @GET("/users/{user}/repos")
    fun userReposBy_name_direction(@Path("user") user: String,
                                   @Query("direction") direction: String): Call<List<UserRepository>>


    // repositories sort=created  and direction=asc
    // https://api.github.com/users/nadar71/repos?sort=created&direction=asc
    @GET("/users/{user}/repos")
    fun userReposBy_created_direction(@Path("user") user: String,
                                      @Query("sort") created: String,
                                      @Query("direction") direction: String): Call<List<UserRepository>>


    // repositories sort=updated  and direction=asc
    // https://api.github.com/users/nadar71/repos?sort=updated&direction=asc
    @GET("/users/{user}/repos")
    fun reposForuser_updated_asc(@Path("user") user: String,
                                 @Query("sort") updated: String,
                                 @Query("direction") direction: String): Call<List<UserRepository>>


    // repositories sort=pushed  and direction=asc
    // https://api.github.com/users/nadar71/repos?sort=pushed&direction=asc
    @GET("/users/{user}/repos")
    fun reposForuser_pushed_asc(@Path("user") user: String,
                                @Query("sort") pushed: String,
                                @Query("direction") direction: String): Call<List<UserRepository>>


}
