package com.mapelli.simone.githubclient.viewmodel;

import android.content.Context;
import android.util.Log;

import com.mapelli.simone.githubclient.GithubClientApplication;
import com.mapelli.simone.githubclient.data.GithubUsersAppRepository;
import com.mapelli.simone.githubclient.data.entity.UserProfile_Mini;
import com.mapelli.simone.githubclient.util.AppExecutors;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class UserSearchViewModel extends ViewModel {
    private static final String TAG = UserSearchViewModel.class.getSimpleName();

    private String keyword;
    private String page_num;
    private String per_page;
    private GithubUsersAppRepository githubUsersAppRepository;
    private AppExecutors executors;
    private static Context context;


    // Livedata on UserProfile_Mini List to be populated by ViewModel
    private LiveData<List<UserProfile_Mini>> userProfile_Mini_list;

    // Livedata var on UserProfile_Full obj to populate through ViewModel
    // private LiveData<UserProfile_Full> userProfile_Full;


    /**
     * ---------------------------------------------------------------------------------------------
     * Standard UserSearchViewModel constructor
     * ---------------------------------------------------------------------------------------------
     */
    /*
    public UserSearchViewModel() {
        context = (GithubClientApplication) GithubClientApplication.getsContext();

        // init repository
        githubUsersAppRepository = ((GithubClientApplication) GithubClientApplication
                .getsContext()).getRepository();
        // get user list from db when there are new data
        userProfile_Mini_list = githubUsersAppRepository.loadUserList();
    }
     */


    /**
     * ---------------------------------------------------------------------------------------------
     * Constructor with parameter used by {@link SearchUserViewModelFactory}
     * @param keyword
     * @param page_num
     * @param per_page
     */
    public UserSearchViewModel(String keyword, String page_num, String per_page) {
        Log.d(TAG, "Actively retrieving the User List from repository i.e. db");
        this.keyword  = keyword;
        this.page_num = page_num;
        this.per_page = per_page;

        context = (GithubClientApplication) GithubClientApplication.getsContext();

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
    public void storeUserList(){
        Log.d(TAG, "storeUserList: CALLED");
        executors.networkIO().execute(()->{
            githubUsersAppRepository.fetchUserSearch(keyword, page_num, per_page);
        });

    }

    /**
     * ---------------------------------------------------------------------------------------------
     * Getter for LiveData<List<UserProfile_Mini>> list, used to get the observable in
     * SearchUserActvity
     * @return
     */
    public LiveData<List<UserProfile_Mini>> getEqList() {
        return userProfile_Mini_list;
    }



}
