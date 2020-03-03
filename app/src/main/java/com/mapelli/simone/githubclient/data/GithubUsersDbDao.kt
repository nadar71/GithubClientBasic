package com.mapelli.simone.githubclient.data

import com.mapelli.simone.githubclient.data.entity.UserProfile_Full
import com.mapelli.simone.githubclient.data.entity.UserProfile_Mini
import com.mapelli.simone.githubclient.data.entity.UserRepository

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface GithubUsersDbDao {
    // Tables : USERS_PROFILES_FULL, USERS_PROFILES_MINI, USERS_REPOSITORIES

    //----------------------------------------------------------------------------------------------
    //  QUERY
    //----------------------------------------------------------------------------------------------
    @Query("SELECT * FROM USERS_PROFILES_MINI ")
    fun loadUserList(): LiveData<List<UserProfile_Mini>>

    @Query("SELECT * FROM USERS_PROFILES_FULL ")
    fun loadUserFull(): LiveData<UserProfile_Full>

    @Query("SELECT * FROM USERS_REPOSITORIES ")
    fun loadUserRepoList(): LiveData<List<UserRepository>>


    //----------------------------------------------------------------------------------------------
    //  INSERT
    //----------------------------------------------------------------------------------------------
    @Insert
    fun insertUserMini(userProfile_Mini: UserProfile_Mini)

    @Insert
    fun insertUserFull(userProfile_Full: UserProfile_Full)

    @Insert
    fun insertUserRepo(userRepository: UserRepository)

    // Insert all the UserProfile_Min as whole list
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll_UserMini(userProfile_MiniList: List<UserProfile_Mini>)

    // Insert all the UserRepository as whole list
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll_UserRepo(userRepositoryList: List<UserRepository>)


    //----------------------------------------------------------------------------------------------
    //  DROP TABLE : delete all table content
    //----------------------------------------------------------------------------------------------

    @Query("DELETE FROM USERS_PROFILES_MINI")
    fun dropUserMiniTable()

    @Query("DELETE FROM USERS_PROFILES_FULL")
    fun dropUserFullTable()

    @Query("DELETE FROM USERS_REPOSITORIES")
    fun dropUserRepoTable()

}

