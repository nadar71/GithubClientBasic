package com.mapelli.simone.githubclient.data.entity


import com.google.gson.annotations.SerializedName

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(tableName = "USERS_REPOSITORIES")
class UserRepository {

    @PrimaryKey(autoGenerate = true)
    var idx: Int = 0

    // @SerializedName("login")
    var user_id_owner: String
    // var user_id_owner: String? = null

    @SerializedName("name")
    var name: String
    // var name: String? = null

    @SerializedName("full_name")
    var full_name: String
    // var full_name: String? = null

    @SerializedName("html_url")
    var html_url: String
    // var html_url: String? = null

    @SerializedName("created_at")
    var created_at: String
    // var created_at: String? = null

    @SerializedName("updated_at")
    var updated_at: String
    // var updated_at: String? = null

    @SerializedName("pushed_at")
    var pushed_at: String
    // var pushed_at: String? = null

    @SerializedName("stargazers_count")
    var stargazers_count: String
    // var stargazers_count: String? = null

    @SerializedName("forks_count")
    var forks_count: String
    // var forks_count: String? = null

    @Ignore
    constructor(user_id_owner: String, name: String, full_name: String, html_url: String,
                created_at: String, updated_at: String, pushed_at: String,
                stargazers_count: String, forks_count: String) {
        this.user_id_owner = user_id_owner
        this.name = name
        this.full_name = full_name
        this.html_url = html_url
        this.created_at = created_at
        this.updated_at = updated_at
        this.pushed_at = pushed_at
        this.stargazers_count = stargazers_count
        this.forks_count = forks_count
    }

    /**
     * ---------------------------------------------------------------------------------------------
     * For db insert
     *
     * @param idx
     * @param user_id_owner
     * @param name
     * @param full_name
     * @param html_url
     * @param created_at
     * @param updated_at
     * @param pushed_at
     * @param stargazers_count
     * @param forks_count
     */
    constructor(idx: Int, user_id_owner: String, name: String, full_name: String,
                html_url: String, created_at: String, updated_at: String, pushed_at: String,
                stargazers_count: String, forks_count: String) {
        this.idx = idx
        this.user_id_owner = user_id_owner
        this.name = name
        this.full_name = full_name
        this.html_url = html_url
        this.created_at = created_at
        this.updated_at = updated_at
        this.pushed_at = pushed_at
        this.stargazers_count = stargazers_count
        this.forks_count = forks_count
    }
}
