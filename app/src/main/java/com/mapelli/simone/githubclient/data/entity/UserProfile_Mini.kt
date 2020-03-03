package com.mapelli.simone.githubclient.data.entity

import com.google.gson.annotations.SerializedName

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey


/**
 * -------------------------------------------------------------------------------------------------
 * UserProfile_Mini is a lighter fields number version UserProfile_Full
 * In case of great number of Load More pressing in SearchUserActivity,
 * it decrease the data load in memory.
 */
@Entity(tableName = "USERS_PROFILES_MINI")
class UserProfile_Mini {

    @PrimaryKey(autoGenerate = true)
    var idx: Int = 0

    @SerializedName("id")
    var id: String? = null

    @SerializedName("login")
    var login: String? = null

    @SerializedName("avatar_url")
    var avatar_url: String? = null

    // see issue #13 on github repo
    // @SerializedName("name")
    // private String name;

    @Ignore
    constructor(id: String, login: String, avatar_url: String, name: String) {
        this.id = id
        this.login = login
        this.avatar_url = avatar_url
        // this.name = name;
    }

    /**
     * ---------------------------------------------------------------------------------------------
     * For db insert
     *
     * @param idx
     * @param id
     * @param login
     * @param avatar_url
     */
    constructor(idx: Int, id: String, login: String, avatar_url: String) {
        this.idx = idx
        this.id = id
        this.login = login
        this.avatar_url = avatar_url
    }

    /*
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    */
}
