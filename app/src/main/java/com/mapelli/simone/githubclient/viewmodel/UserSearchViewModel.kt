package com.mapelli.simone.githubclient.viewmodel

import com.mapelli.simone.githubclient.GithubClientApplication
import com.mapelli.simone.githubclient.data.GithubUsersAppRepository
import com.mapelli.simone.githubclient.data.entity.UserProfile_Mini
import com.mapelli.simone.githubclient.util.AppExecutors

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class UserSearchViewModel : ViewModel() {

    private val githubUsersAppRepository: GithubUsersAppRepository
    private val executors: AppExecutors?

    // Livedata on UserProfile_Mini List to be populated by ViewModel
    /**
     * ---------------------------------------------------------------------------------------------
     * Getter for LiveData<List></List><UserProfile_Mini>> list, used to get the observable in
     * SearchUserActvity
     *
     * @return
    </UserProfile_Mini> */
    val userListObserved: LiveData<List<UserProfile_Mini>>

    /**
     * ---------------------------------------------------------------------------------------------
     * Standard Constructor
     */
    init {
        // get repository instance
        githubUsersAppRepository = (GithubClientApplication.getsContext() as GithubClientApplication)
                .repositoryWithNetwork

        executors = (GithubClientApplication.getsContext() as GithubClientApplication)
                .appExecutorsInstance

        userListObserved = githubUsersAppRepository.loadUserList()
    }


    /**
     * ---------------------------------------------------------------------------------------------
     * Load user list into db for being observed,so to get UserActivity and any other observer aware
     */
    fun storeUserList(keyword: String, page_num: String, per_page: String) {
        executors!!.networkIO().execute { githubUsersAppRepository.fetchUserSearch(keyword, page_num, per_page) }
    }


    /**
     * ---------------------------------------------------------------------------------------------
     * Used to delete table from previous user list results
     */
    fun deleteUserList() {
        executors!!.diskIO().execute { githubUsersAppRepository.dropUserMiniTable() }
    }

    companion object {
        private val TAG = UserSearchViewModel::class.java!!.getSimpleName()
    }


}
