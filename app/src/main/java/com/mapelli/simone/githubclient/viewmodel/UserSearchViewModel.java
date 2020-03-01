package com.mapelli.simone.githubclient.viewmodel;

import com.mapelli.simone.githubclient.GithubClientApplication;
import com.mapelli.simone.githubclient.data.GithubUsersAppRepository;
import com.mapelli.simone.githubclient.data.entity.UserProfile_Mini;
import com.mapelli.simone.githubclient.util.AppExecutors;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class UserSearchViewModel extends ViewModel {
    private static final String TAG = UserSearchViewModel.class.getSimpleName();

    private GithubUsersAppRepository githubUsersAppRepository;
    private AppExecutors executors;

    // Livedata on UserProfile_Mini List to be populated by ViewModel
    private LiveData<List<UserProfile_Mini>> userProfile_Mini_list;


    /**
     * ---------------------------------------------------------------------------------------------
     * Constructor with parameter used by {@link SearchUserViewModelFactory}
     */
    public UserSearchViewModel() {
        // get repository instance
        githubUsersAppRepository = ((GithubClientApplication) GithubClientApplication.getsContext())
                .getRepositoryWithNetwork();

        executors = ((GithubClientApplication) GithubClientApplication.getsContext())
                .getAppExecutorsInstance();

        userProfile_Mini_list = githubUsersAppRepository.loadUserList();
    }


    /**
     * ---------------------------------------------------------------------------------------------
     * Load user list into db for being observed,so to get UserActivity and any other observer aware
     */
    public void storeUserList(String keyword, String page_num, String per_page) {
        executors.networkIO().execute(() -> {
            githubUsersAppRepository.fetchUserSearch(keyword, page_num, per_page);
        });
    }

    /**
     * ---------------------------------------------------------------------------------------------
     * Getter for LiveData<List<UserProfile_Mini>> list, used to get the observable in
     * SearchUserActvity
     *
     * @return
     */
    public LiveData<List<UserProfile_Mini>> getUserListObserved() {
        return userProfile_Mini_list;
    }


    /**
     * ---------------------------------------------------------------------------------------------
     * Used to delete table from previous user list results
     */
    public void deleteUserList() {
        executors.diskIO().execute(() -> {
            githubUsersAppRepository.dropUserMiniTable();
        });
    }


}
