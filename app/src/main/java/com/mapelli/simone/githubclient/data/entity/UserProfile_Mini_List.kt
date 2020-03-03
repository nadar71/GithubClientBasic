package com.mapelli.simone.githubclient.data.entity

import com.google.gson.annotations.SerializedName


/**
 * -------------------------------------------------------------------------------------------------
 * Collect the list of user data in json array "items"
 * from request like https://api.github.com/search/users
 */
class UserProfile_Mini_List {

    @SerializedName("items")
    var userList: List<UserProfile_Mini>? = null

}
