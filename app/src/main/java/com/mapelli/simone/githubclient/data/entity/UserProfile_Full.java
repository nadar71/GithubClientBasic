package com.mapelli.simone.githubclient.data.entity;


import com.google.gson.annotations.SerializedName;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "USERS_PROFILES_FULL")
public class UserProfile_Full {

    @PrimaryKey(autoGenerate = true)
    private int idx;

    @SerializedName("login")
    private String login;

    @SerializedName("id")
    private String id;

    @SerializedName("avatar_url")
    private String avatar_url;

    @SerializedName("repos_url")
    private String repos_url;

    @SerializedName("name")
    private String name;

    @SerializedName("location")
    private String location;

    @SerializedName("email")
    private String email;

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    @Ignore
    public UserProfile_Full(String login, String id, String avatar_url, String repos_url,
                            String name, String location, String email) {
        this.login = login;
        this.id = id;
        this.avatar_url = avatar_url;
        this.repos_url = repos_url;
        this.name = name;
        this.location = location;
        this.email = email;
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
    public UserProfile_Full(int idx, String login, String id, String avatar_url, String repos_url,
                            String name, String location, String email) {
        this.idx = idx;
        this.login = login;
        this.id = id;
        this.avatar_url = avatar_url;
        this.repos_url = repos_url;
        this.name = name;
        this.location = location;
        this.email = email;
    }


    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public String getRepos_url() {
        return repos_url;
    }

    public void setRepos_url(String repos_url) {
        this.repos_url = repos_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
