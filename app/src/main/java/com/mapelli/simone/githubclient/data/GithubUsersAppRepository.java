package com.mapelli.simone.githubclient.data;

import com.mapelli.simone.githubclient.data.entity.UserProfile_Full;
import com.mapelli.simone.githubclient.data.entity.UserProfile_Mini;
import com.mapelli.simone.githubclient.data.entity.UserRepository;
import com.mapelli.simone.githubclient.network.NetworkRequests;
import com.mapelli.simone.githubclient.util.AppExecutors;

import java.util.List;

import androidx.lifecycle.LiveData;

public class GithubUsersAppRepository {
    private final static String TAG = GithubUsersAppRepository.class.getSimpleName();

    private static GithubUsersAppRepository sInstance;
    private NetworkRequests networkRequests;
    private AppExecutors executors;
    private GithubUsersDatabase appDb;


    private GithubUsersAppRepository(GithubUsersDatabase githubUsersDatabase) {
        this.appDb = githubUsersDatabase;
    }


    private GithubUsersAppRepository(GithubUsersDatabase githubUsersDatabase,
                                     NetworkRequests networkRequests,
                                     AppExecutors executors) {
        this.appDb = githubUsersDatabase;
        this.networkRequests = networkRequests;
        this.executors = executors;

        // Observe data taken from network and update db
        // The Livedata observing this data from a View (i.e. SearhUserActivity, UserDetailActivity)
        // will be awared and it'll show the data
        LiveData<List<UserProfile_Mini>> userMini_List_fromNet =
                networkRequests.getUsersProfilesMini();

        userMini_List_fromNet.observeForever(newUsers -> {
            executors.diskIO().execute(() -> {
                        // dropUserMiniTable();
                        insertAll_UserMini(newUsers);
                    }
            );
        });


        LiveData<UserProfile_Full> userProfileFull_fromNet =
                networkRequests.getUserProfilesFull();

        userProfileFull_fromNet.observeForever(newUserFull -> {
            executors.diskIO().execute(() -> {
                        dropUserFullTable();
                        insertUserFull(newUserFull);
                    }
            );
        });

        LiveData<List<UserRepository>> userRepoList_fromNet =
                networkRequests.getUserRepositories();

        userRepoList_fromNet.observeForever(newRepositories -> {
            executors.diskIO().execute(() -> {
                        dropUserRepoTable();
                        insertAll_UserRepo(newRepositories);
                    }
            );
        });

    }



    /**
     * ---------------------------------------------------------------------------------------------
     * Setup executors pool
     * @param executors
     */
    public void setExecutors(AppExecutors executors) {
        this.executors = executors;
    }



    /**
     * ---------------------------------------------------------------------------------------------
     * Get repository singleton instance for standard constructor
     *
     * @param database
     * @return
     */
    public static GithubUsersAppRepository getInstance(final GithubUsersDatabase database) {
        if (sInstance == null) {
            synchronized (GithubUsersAppRepository.class) {
                if (sInstance == null) {
                    sInstance = new GithubUsersAppRepository(database);
                }
            }
        }
        return sInstance;
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
    public static GithubUsersAppRepository getInstanceWithDataSource(GithubUsersDatabase earthquakeDatabase,
                                                                     NetworkRequests networkRequests,
                                                                     AppExecutors executors) {
        if (sInstance == null) {
            synchronized (GithubUsersAppRepository.class) {
                if (sInstance == null) {
                    sInstance = new GithubUsersAppRepository(earthquakeDatabase, networkRequests, executors);
                }
            }
        }
        return sInstance;
    }


    //----------------------------------------------------------------------------------------------
    //  NETWORK REQUESTS WRAPPING
    //----------------------------------------------------------------------------------------------

    /**
     * ---------------------------------------------------------------------------------------------
     * Fetch new user search
     */
    public void fetchUserSearch(String keyword, String page_num, String per_page) {
        networkRequests.doUsersSearch(keyword, page_num, per_page);
    }


    /**
     * ---------------------------------------------------------------------------------------------
     * Fetch new user profile Full
     */
    public void fetchUserFull(String login) {
        networkRequests.getUserProfileFullByLogin(login);
    }

    /**
     * ---------------------------------------------------------------------------------------------
     * Fetch new users repositories list
     */
    public void fetchUserRepoList(String login, String direction) {
        networkRequests.getRepoFilterByName_Direction(login, direction);
    }

    /**
     * ---------------------------------------------------------------------------------------------
     * Fetch filter users repositories by created_at
     */
    public void fetchRepoFilterByCreated(String login, String direction) {
        networkRequests.getRepoFilterByCreated_Direction(login, direction);
    }

    /**
     * ---------------------------------------------------------------------------------------------
     * Fetch filter users repositories by updated_at
     */
    public void fetchRepoFilterByUpdated(String login, String direction) {
        networkRequests.getRepoFilterByUpdated_Direction(login, direction);
    }

    /**
     * ---------------------------------------------------------------------------------------------
     * Fetch filter users repositories by pushed_at
     */
    public void fetchRepoFilterByPushed(String login, String direction) {
        networkRequests.getRepoFilterByPushed_Direction(login, direction);
    }


    //----------------------------------------------------------------------------------------------
    //  QUERY WRAPPING
    //----------------------------------------------------------------------------------------------

    public LiveData<List<UserProfile_Mini>> loadUserList() {
        return appDb.githubUsersDao().loadUserList();
    }

    public LiveData<UserProfile_Full> loadUserFull() {
        return appDb.githubUsersDao().loadUserFull();
    }

    public LiveData<List<UserRepository>> loadUserRepoList() {
        return appDb.githubUsersDao().loadUserRepoList();
    }


    //----------------------------------------------------------------------------------------------
    //  INSERT WRAPPING
    //----------------------------------------------------------------------------------------------

    public void insertUserMini(UserProfile_Mini userProfile_Mini) {
        appDb.githubUsersDao().insertUserMini(userProfile_Mini);
    }

    public void insertUserFull(UserProfile_Full userProfile_Full) {
        appDb.githubUsersDao().insertUserFull(userProfile_Full);
    }

    public void insertUserRepo(UserRepository userRepository) {
        appDb.githubUsersDao().insertUserRepo(userRepository);
    }

    public void insertAll_UserMini(List<UserProfile_Mini> userProfile_Mini) {
        appDb.githubUsersDao().insertAll_UserMini(userProfile_Mini);
    }

    public void insertAll_UserRepo(List<UserRepository> userRepository) {
        appDb.githubUsersDao().insertAll_UserRepo(userRepository);
    }


    //----------------------------------------------------------------------------------------------
    //  DROP TABLE  WRAPPING
    //----------------------------------------------------------------------------------------------

    public void dropUserMiniTable() {
        appDb.githubUsersDao().dropUserMiniTable();
    }

    public void dropUserFullTable() {
        appDb.githubUsersDao().dropUserFullTable();
    }

    public void dropUserRepoTable() {
        appDb.githubUsersDao().dropUserRepoTable();
    }


}
