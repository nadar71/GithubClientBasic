package com.mapelli.simone.githubclient.data.entity;

import com.google.gson.annotations.SerializedName;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;


/**
 * -------------------------------------------------------------------------------------------------
 * UserProfile_Mini is a lighter fields number version UserProfile_Full
 * In case of great number of Load More pressing in SearchUserActivity,
 * it decrease the data load in memory.
 */
@Entity(tableName = "USERS_PROFILES_MINI")
public class UserProfile_Mini {

    @PrimaryKey(autoGenerate = true)
    private int idx;

    @SerializedName("id")
    private String id;

    @SerializedName("login")
    private String login;

    @SerializedName("avatar_url")
    private String avatar_url;

    // see issue #13 on github repo
    // @SerializedName("name")
    // private String name;

    @Ignore
    public UserProfile_Mini(String id, String login, String avatar_url, String name) {
        this.id = id;
        this.login = login;
        this.avatar_url = avatar_url;
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
    public UserProfile_Mini(int idx, String id, String login, String avatar_url) {
        this.idx = idx;
        this.id = id;
        this.login = login;
        this.avatar_url = avatar_url;
    }

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
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
