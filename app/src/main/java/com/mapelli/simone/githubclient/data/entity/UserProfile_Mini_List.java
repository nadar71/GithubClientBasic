package com.mapelli.simone.githubclient.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;


/**
 * -------------------------------------------------------------------------------------------------
 * Collect the list of user data in json array "items"
 * from request like https://api.github.com/search/users
 */
public class UserProfile_Mini_List {

    @SerializedName("items")
    private List<UserProfile_Mini> userList;

    public List<UserProfile_Mini> getUserList() {
        return userList;
    }

    public void setUserList(List<UserProfile_Mini> userList) {
        this.userList = userList;
    }

}
