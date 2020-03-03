package com.mapelli.simone.githubclient.viewmodel

import com.mapelli.simone.githubclient.GithubClientApplication
import com.mapelli.simone.githubclient.data.GithubUsersAppRepository
import com.mapelli.simone.githubclient.data.entity.UserRepository
import com.mapelli.simone.githubclient.util.AppExecutors

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class UserRepositoriesViewModel : ViewModel() {

    private val githubUsersAppRepository: GithubUsersAppRepository
    private val executors: AppExecutors?

    // Livedata on UserRepository List to be populated by ViewModel
    /**
     * ---------------------------------------------------------------------------------------------
     * Getter for LiveData<List></List><UserRepository>> list
     *
     * @return
    </UserRepository> */
    val userRepoListObserved: LiveData<List<UserRepository>>

    /**
     * ---------------------------------------------------------------------------------------------
     * Constructor with parameter used by [UserRepositoriesViewModelFactory]
     */
    init {

        // get repository instance
        githubUsersAppRepository = (GithubClientApplication.getsContext() as GithubClientApplication)
                .repositoryWithNetwork

        executors = (GithubClientApplication.getsContext() as GithubClientApplication)
                .appExecutorsInstance

        userRepoListObserved = githubUsersAppRepository.loadUserRepoList()
    }


    /**
     * ---------------------------------------------------------------------------------------------
     * Load user repo list into db for being observed sort by name
     */
    fun storeUserRepoList_ByName(login: String, direction: String) {
        executors!!.networkIO().execute { githubUsersAppRepository.fetchUserRepoList(login, direction) }
    }


    /**
     * ---------------------------------------------------------------------------------------------
     * Load user repo list into db for being observed sort by created
     */
    fun storeUserRepoList_ByCreated(login: String, direction: String) {
        executors!!.networkIO().execute { githubUsersAppRepository.fetchRepoFilterByCreated(login, direction) }
    }


    /**
     * ---------------------------------------------------------------------------------------------
     * Load user repo list into db for being observed sort by updated
     */
    fun storeUserRepoList_ByUpdated(login: String, direction: String) {
        executors!!.networkIO().execute { githubUsersAppRepository.fetchRepoFilterByUpdated(login, direction) }
    }


    /**
     * ---------------------------------------------------------------------------------------------
     * Load user repo list into db for being observed sort by pushed
     */
    fun storeUserRepoList_ByPushed(login: String, direction: String) {
        executors!!.networkIO().execute { githubUsersAppRepository.fetchRepoFilterByPushed(login, direction) }
    }

    companion object {
        private val TAG = UserSearchViewModel::class.java!!.getSimpleName()
    }

}