package com.mapelli.simone.githubclient.viewmodel;

import com.mapelli.simone.githubclient.GithubClientApplication;
import com.mapelli.simone.githubclient.data.GithubUsersAppRepository;
import com.mapelli.simone.githubclient.data.entity.UserRepository;
import com.mapelli.simone.githubclient.util.AppExecutors;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class UserRepositoriesViewModel extends ViewModel {
    private static final String TAG = UserSearchViewModel.class.getSimpleName();

    private GithubUsersAppRepository githubUsersAppRepository;
    private AppExecutors executors;

    // Livedata on UserRepository List to be populated by ViewModel
    private LiveData<List<UserRepository>> userRepository_list;


    /**
     * ---------------------------------------------------------------------------------------------
     * Constructor with parameter used by {@link UserRepositoriesViewModelFactory}
     */
    public UserRepositoriesViewModel() {

        // get repository instance
        githubUsersAppRepository = ((GithubClientApplication) GithubClientApplication.getsContext())
                .getRepositoryWithNetwork();

        executors = ((GithubClientApplication) GithubClientApplication.getsContext())
                .getAppExecutorsInstance();

        userRepository_list = githubUsersAppRepository.loadUserRepoList();
    }


    /**
     * ---------------------------------------------------------------------------------------------
     * Load user repo list into db for being observed sort by name
     */
    public void storeUserRepoList_ByName(String login, String direction) {
        executors.networkIO().execute(() -> {
            githubUsersAppRepository.fetchUserRepoList(login, direction);
        });
    }


    /**
     * ---------------------------------------------------------------------------------------------
     * Load user repo list into db for being observed sort by created
     */
    public void storeUserRepoList_ByCreated(String login, String direction) {
        executors.networkIO().execute(() -> {
            githubUsersAppRepository.fetchRepoFilterByCreated(login, direction);
        });
    }


    /**
     * ---------------------------------------------------------------------------------------------
     * Load user repo list into db for being observed sort by updated
     */
    public void storeUserRepoList_ByUpdated(String login, String direction) {
        executors.networkIO().execute(() -> {
            githubUsersAppRepository.fetchRepoFilterByUpdated(login, direction);
        });
    }


    /**
     * ---------------------------------------------------------------------------------------------
     * Load user repo list into db for being observed sort by pushed
     */
    public void storeUserRepoList_ByPushed(String login, String direction) {
        executors.networkIO().execute(() -> {
            githubUsersAppRepository.fetchRepoFilterByPushed(login, direction);
        });
    }

    /**
     * ---------------------------------------------------------------------------------------------
     * Getter for LiveData<List<UserRepository>> list
     *
     * @return
     */
    public LiveData<List<UserRepository>> getUserRepoListObserved() {
        return userRepository_list;
    }

}