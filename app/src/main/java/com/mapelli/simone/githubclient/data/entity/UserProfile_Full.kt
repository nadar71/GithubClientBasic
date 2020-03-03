package com.mapelli.simone.githubclient.data.entity


import com.google.gson.annotations.SerializedName

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "USERS_PROFILES_FULL")
class UserProfile_Full {

    @PrimaryKey(autoGenerate = true)
    var idx: Int = 0

    @SerializedName("login")
    var login: String? = null

    @SerializedName("id")
    var id: String? = null

    @SerializedName("avatar_url")
    var avatar_url: String? = null

    @SerializedName("repos_url")
    var repos_url: String? = null

    @SerializedName("name")
    var name: String? = null

    @SerializedName("location")
    var location: String? = null

    @SerializedName("email")
    var email: String? = null


    @Ignore
    constructor(login: String, id: String, avatar_url: String, repos_url: String,
                name: String, location: String, email: String) {
        this.login = login
        this.id = id
        this.avatar_url = avatar_url
        this.repos_url = repos_url
        this.name = name
        this.location = location
        this.email = email
    }

    /**
     * ---------------------------------------------------------------------------------------------
     * For db insert
     *
     * @param idx
     * @param login
     * @param id
     * @param avatar_url
     * @param repos_url
     * @param name
     * @param location
     * @param email
     */
    constructor(idx: Int, login: String, id: String, avatar_url: String, repos_url: String,
                name: String, location: String, email: String) {
        this.idx = idx
        this.login = login
        this.id = id
        this.avatar_url = avatar_url
        this.repos_url = repos_url
        this.name = name
        this.location = location
        this.email = email
    }
}
