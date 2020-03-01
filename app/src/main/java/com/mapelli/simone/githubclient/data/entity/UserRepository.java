package com.mapelli.simone.githubclient.data.entity;


import com.google.gson.annotations.SerializedName;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "USERS_REPOSITORIES")
public class UserRepository {

    @PrimaryKey(autoGenerate = true)
    private int idx;

    // @SerializedName("login")
    private String user_id_owner;

    @SerializedName("name")
    private String name;

    @SerializedName("full_name")
    private String full_name;

    @SerializedName("html_url")
    private String html_url;

    @SerializedName("created_at")
    private String created_at;

    @SerializedName("updated_at")
    private String updated_at;

    @SerializedName("pushed_at")
    private String pushed_at;

    @SerializedName("stargazers_count")
    private String stargazers_count;

    @SerializedName("forks_count")
    private String forks_count;

    @Ignore
    public UserRepository(String user_id_owner, String name, String full_name, String html_url,
                          String created_at, String updated_at, String pushed_at,
                          String stargazers_count, String forks_count) {
        this.user_id_owner = user_id_owner;
        this.name = name;
        this.full_name = full_name;
        this.html_url = html_url;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.pushed_at = pushed_at;
        this.stargazers_count = stargazers_count;
        this.forks_count = forks_count;
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
    public UserRepository(int idx, String user_id_owner, String name, String full_name,
                          String html_url, String created_at, String updated_at, String pushed_at,
                          String stargazers_count, String forks_count) {
        this.idx = idx;
        this.user_id_owner = user_id_owner;
        this.name = name;
        this.full_name = full_name;
        this.html_url = html_url;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.pushed_at = pushed_at;
        this.stargazers_count = stargazers_count;
        this.forks_count = forks_count;
    }

    public String getUser_id_owner() {
        return user_id_owner;
    }

    public void setUser_id_owner(String user_id_owner) {
        this.user_id_owner = user_id_owner;
    }

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getHtml_url() {
        return html_url;
    }

    public void setHtml_url(String html_url) {
        this.html_url = html_url;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getPushed_at() {
        return pushed_at;
    }

    public void setPushed_at(String pushed_at) {
        this.pushed_at = pushed_at;
    }

    public String getStargazers_count() {
        return stargazers_count;
    }

    public void setStargazers_count(String stargazers_count) {
        this.stargazers_count = stargazers_count;
    }

    public String getForks_count() {
        return forks_count;
    }

    public void setForks_count(String forks_count) {
        this.forks_count = forks_count;
    }
}
