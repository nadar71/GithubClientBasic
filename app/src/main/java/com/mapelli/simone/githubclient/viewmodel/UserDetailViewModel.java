package com.mapelli.simone.githubclient.viewmodel;

import com.mapelli.simone.githubclient.GithubClientApplication;
import com.mapelli.simone.githubclient.data.GithubUsersAppRepository;
import com.mapelli.simone.githubclient.data.entity.UserProfile_Full;
import com.mapelli.simone.githubclient.util.AppExecutors;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class UserDetailViewModel extends ViewModel {
    private static final String TAG = UserSearchViewModel.class.getSimpleName();

    private GithubUsersAppRepository githubUsersAppRepository;
    private AppExecutors executors;

    // Livedata on UserProfile_Full List to be populated by ViewModel
    private LiveData<UserProfile_Full> userProfile_full;


    /**
     * ---------------------------------------------------------------------------------------------
     * Constructor with parameter used by {@link UserDetailViewModelFactory}
     */
    public UserDetailViewModel() {
        // get repository instance
        githubUsersAppRepository = ((GithubClientApplication) GithubClientApplication.getsContext())
                .getRepositoryWithNetwork();

        executors = ((GithubClientApplication) GithubClientApplication.getsContext())
                .getAppExecutorsInstance();

        userProfile_full = githubUsersAppRepository.loadUserFull();
    }


    /**
     * ---------------------------------------------------------------------------------------------
     * Load user details into db for being observed,so to get UserActivity and any other observer aware
     */
    public void storeUserFull(String login) {
        executors.networkIO().execute(() -> {
            githubUsersAppRepository.fetchUserFull(login);
        });

    }

    /**
     * ---------------------------------------------------------------------------------------------
     * Getter for LiveData<List<UserProfile_Mini>> list, used to get the observable in
     * UserDetailActvity
     *
     * @return
     */
    public LiveData<UserProfile_Full> getUserObserved() {
        return userProfile_full;
    }

}
