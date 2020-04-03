package com.mapelli.simone.githubclient.viewmodel

import com.mapelli.simone.githubclient.GithubClientApplication
import com.mapelli.simone.githubclient.data.GithubUsersAppRepository
import com.mapelli.simone.githubclient.data.entity.UserProfile_Full
import com.mapelli.simone.githubclient.util.AppExecutors

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class UserDetailViewModel : ViewModel() {

    private val githubUsersAppRepository: GithubUsersAppRepository
    private val executors: AppExecutors

    // Livedata on UserProfile_Full List to be populated by ViewModel
    /**
     * ---------------------------------------------------------------------------------------------
     * Getter for LiveData<List></List><UserProfile_Mini>> list, used to get the observable in
     * UserDetailActvity
     *
     * @return
    </UserProfile_Mini> */
    val userObserved: LiveData<UserProfile_Full>

    /**
     * ---------------------------------------------------------------------------------------------
     * Constructor with parameter used by [UserDetailViewModelFactory]
     */
    init {
        // get repository instance
        githubUsersAppRepository = (GithubClientApplication.getsContext() as GithubClientApplication)
                .repositoryWithNetwork

        executors = (GithubClientApplication.getsContext() as GithubClientApplication)
                .appExecutorsInstance

        userObserved = githubUsersAppRepository.loadUserFull()
    }


    /**
     * ---------------------------------------------------------------------------------------------
     * Load user details into db for being observed,so to get UserActivity and any other observer aware
     */
    fun storeUserFull(login: String) {
        executors.networkIO().execute { githubUsersAppRepository.fetchUserFull(login) }

    }

    companion object {
        private val TAG = UserSearchViewModel::class.java.getSimpleName()
    }

}
