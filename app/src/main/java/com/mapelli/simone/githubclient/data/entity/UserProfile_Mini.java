package com.mapelli.simone.githubclient.data.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "USERS_PROFILES_MINI")
public class UserProfile_Mini {

    @PrimaryKey
    private String login;
    private String avatar_url;
    private String name;

    public UserProfile_Mini(String login, String avatar_url, String name) {
        this.login = login;
        this.avatar_url = avatar_url;
        this.name = name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
