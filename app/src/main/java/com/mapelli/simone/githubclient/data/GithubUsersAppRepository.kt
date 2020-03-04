package com.mapelli.simone.githubclient.data

import com.mapelli.simone.githubclient.data.entity.UserProfile_Full
import com.mapelli.simone.githubclient.data.entity.UserProfile_Mini
import com.mapelli.simone.githubclient.data.entity.UserRepository
import com.mapelli.simone.githubclient.network.NetworkRequests
import com.mapelli.simone.githubclient.util.AppExecutors

import androidx.lifecycle.LiveData

class GithubUsersAppRepository {
    lateinit var networkRequests: NetworkRequests
    lateinit var appExecutors: AppExecutors
    var appDb: GithubUsersDatabase

    private constructor(githubUsersDatabase: GithubUsersDatabase) {
        this.appDb = githubUsersDatabase
    }


    private constructor(githubUsersDatabase: GithubUsersDatabase,
                        networkRequests: NetworkRequests,
                        executors: AppExecutors) {
        this.appDb = githubUsersDatabase
        this.networkRequests = networkRequests
        this.appExecutors = executors

        // Observe data taken from network and update db
        // The Livedata observing this data from a View (i.e. SearhUserActivity, UserDetailActivity)
        // will be awared and it'll show the data
        val userMini_List_fromNet = networkRequests.usersProfiles_Minis

        userMini_List_fromNet.observeForever { newUsers ->
            executors.diskIO().execute {
                // dropUserMiniTable();
                insertAll_UserMini(newUsers)
            }
        }


        val userProfileFull_fromNet = networkRequests.userProfile_Full

        userProfileFull_fromNet.observeForever { newUserFull ->
            executors.diskIO().execute {
                dropUserFullTable()
                insertUserFull(newUserFull)
            }
        }

        val userRepoList_fromNet = networkRequests.userRepositories

        userRepoList_fromNet.observeForever { newRepositories ->
            executors.diskIO().execute {
                dropUserRepoTable()
                insertAll_UserRepo(newRepositories)
            }
        }

    }


    /**
     * ---------------------------------------------------------------------------------------------
     * Setup appExecutors pool
     * @param executors
     */
    fun setExecutors(executors: AppExecutors) {
        this.appExecutors = executors
    }


    //----------------------------------------------------------------------------------------------
    //  NETWORK REQUESTS WRAPPING
    //----------------------------------------------------------------------------------------------

    /**
     * ---------------------------------------------------------------------------------------------
     * Fetch new user search
     */
    fun fetchUserSearch(keyword: String, page_num: String, per_page: String) {
        networkRequests.doUsersSearch(keyword, page_num, per_page)
    }


    /**
     * ---------------------------------------------------------------------------------------------
     * Fetch new user profile Full
     */
    fun fetchUserFull(login: String) {
        networkRequests.getUserProfileFullByLogin(login)
    }

    /**
     * ---------------------------------------------------------------------------------------------
     * Fetch new users repositories list
     */
    fun fetchUserRepoList(login: String, direction: String) {
        networkRequests.getRepoFilterByName_Direction(login, direction)
    }

    /**
     * ---------------------------------------------------------------------------------------------
     * Fetch filter users repositories by created_at
     */
    fun fetchRepoFilterByCreated(login: String, direction: String) {
        networkRequests.getRepoFilterByCreated_Direction(login, direction)
    }

    /**
     * ---------------------------------------------------------------------------------------------
     * Fetch filter users repositories by updated_at
     */
    fun fetchRepoFilterByUpdated(login: String, direction: String) {
        networkRequests.getRepoFilterByUpdated_Direction(login, direction)
    }

    /**
     * ---------------------------------------------------------------------------------------------
     * Fetch filter users repositories by pushed_at
     */
    fun fetchRepoFilterByPushed(login: String, direction: String) {
        networkRequests.getRepoFilterByPushed_Direction(login, direction)
    }


    //----------------------------------------------------------------------------------------------
    //  QUERY WRAPPING
    //----------------------------------------------------------------------------------------------

    fun loadUserList(): LiveData<List<UserProfile_Mini>> {
        return appDb.githubUsersDao().loadUserList()
    }

    fun loadUserFull(): LiveData<UserProfile_Full> {
        return appDb.githubUsersDao().loadUserFull()
    }

    fun loadUserRepoList(): LiveData<List<UserRepository>> {
        return appDb.githubUsersDao().loadUserRepoList()
    }


    //----------------------------------------------------------------------------------------------
    //  INSERT WRAPPING
    //----------------------------------------------------------------------------------------------

    fun insertUserMini(userProfile_Mini: UserProfile_Mini) {
        appDb.githubUsersDao().insertUserMini(userProfile_Mini)
    }

    fun insertUserFull(userProfile_Full: UserProfile_Full) {
        appDb.githubUsersDao().insertUserFull(userProfile_Full)
    }

    fun insertUserRepo(userRepository: UserRepository) {
        appDb.githubUsersDao().insertUserRepo(userRepository)
    }

    fun insertAll_UserMini(userProfile_Mini: List<UserProfile_Mini>) {
        appDb.githubUsersDao().insertAll_UserMini(userProfile_Mini)
    }

    fun insertAll_UserRepo(userRepository: List<UserRepository>) {
        appDb.githubUsersDao().insertAll_UserRepo(userRepository)
    }


    //----------------------------------------------------------------------------------------------
    //  DROP TABLE  WRAPPING
    //----------------------------------------------------------------------------------------------

    fun dropUserMiniTable() {
        appDb.githubUsersDao().dropUserMiniTable()
    }

    fun dropUserFullTable() {
        appDb.githubUsersDao().dropUserFullTable()
    }

    fun dropUserRepoTable() {
        appDb.githubUsersDao().dropUserRepoTable()
    }

    companion object {
        private val TAG = GithubUsersAppRepository::class.java.getSimpleName()

        private var sInstance: GithubUsersAppRepository? = null


        /**
         * ---------------------------------------------------------------------------------------------
         * Get repository singleton instance for standard constructor
         *
         * @param database
         * @return
         */
        fun getInstance(database: GithubUsersDatabase): GithubUsersAppRepository {

            return sInstance ?: synchronized(this) {
                sInstance ?: GithubUsersAppRepository(database);
            }

            /* BEFORE :
            if (sInstance == null) {
                synchronized(GithubUsersAppRepository::class.java) {
                    if (sInstance == null) {
                        sInstance = GithubUsersAppRepository(database)
                    }
                }
            }
            return sInstance
            */
        }


        /**
         * ---------------------------------------------------------------------------------------------
         * Get repo singleton instance for constructor with network requests support
         *
         * @param earthquakeDatabase
         * @param networkRequests
         * @param executors
         * @return
         */
        fun getInstanceWithDataSource(earthquakeDatabase: GithubUsersDatabase,
                                      networkRequests: NetworkRequests,
                                      executors: AppExecutors): GithubUsersAppRepository {

            return sInstance ?: synchronized(this) {
                sInstance ?: GithubUsersAppRepository(earthquakeDatabase, networkRequests, executors);
            }
            /* BEFORE :
            if (sInstance == null) {
                synchronized(GithubUsersAppRepository::class.java) {
                    if (sInstance == null) {
                        sInstance = GithubUsersAppRepository(earthquakeDatabase, networkRequests, executors)
                    }
                }
            }
            return sInstance
            */
        }
    }


}
