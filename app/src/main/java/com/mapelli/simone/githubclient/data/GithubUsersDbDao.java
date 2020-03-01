package com.mapelli.simone.githubclient.data;

import com.mapelli.simone.githubclient.data.entity.UserProfile_Full;
import com.mapelli.simone.githubclient.data.entity.UserProfile_Mini;
import com.mapelli.simone.githubclient.data.entity.UserRepository;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface GithubUsersDbDao {
    // Tables : USERS_PROFILES_FULL, USERS_PROFILES_MINI, USERS_REPOSITORIES

    //----------------------------------------------------------------------------------------------
    //  QUERY
    //----------------------------------------------------------------------------------------------
    @Query("SELECT * FROM USERS_PROFILES_MINI ")
    LiveData<List<UserProfile_Mini>> loadUserList();

    @Query("SELECT * FROM USERS_PROFILES_FULL ")
    LiveData<UserProfile_Full> loadUserFull();

    @Query("SELECT * FROM USERS_REPOSITORIES ")
    LiveData<List<UserRepository>> loadUserRepoList();


    //----------------------------------------------------------------------------------------------
    //  INSERT
    //----------------------------------------------------------------------------------------------
    @Insert
    void insertUserMini(UserProfile_Mini userProfile_Mini);

    @Insert
    void insertUserFull(UserProfile_Full userProfile_Full);

    @Insert
    void insertUserRepo(UserRepository userRepository);

    // Insert all the UserProfile_Min as whole list
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll_UserMini(List<UserProfile_Mini> userProfile_MiniList);

    // Insert all the UserRepository as whole list
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll_UserRepo(List<UserRepository> userRepositoryList);


    //----------------------------------------------------------------------------------------------
    //  DROP TABLE : delete all table content
    //----------------------------------------------------------------------------------------------

    @Query("DELETE FROM USERS_PROFILES_MINI")
    void dropUserMiniTable();

    @Query("DELETE FROM USERS_PROFILES_FULL")
    void dropUserFullTable();

    @Query("DELETE FROM USERS_REPOSITORIES")
    void dropUserRepoTable();

}

