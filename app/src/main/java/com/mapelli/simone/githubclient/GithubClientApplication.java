package com.mapelli.simone.githubclient;

import android.app.Application;
import android.content.Context;

import com.mapelli.simone.githubclient.data.GithubUsersAppRepository;
import com.mapelli.simone.githubclient.data.GithubUsersDatabase;
import com.mapelli.simone.githubclient.network.NetworkRequests;
import com.mapelli.simone.githubclient.util.AppExecutors;


/**
 * -------------------------------------------------------------------------------------------------
 * Class used for access classes singletons and application context wherever in the app.
 * Just like repository is an interface for all data operations.
 * NB :
 * registered in manifest in <Application android:name=
 * ".com.mapelli.simone.githubclient.GithubClientApplication" > </Application>
 */
public class GithubClientApplication extends Application {
    private AppExecutors mAppExecutors;

    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppExecutors = AppExecutors.getInstance();
        sContext = getApplicationContext();
    }

    /**
     * ---------------------------------------------------------------------------------------------
     * Return singleton db instance
     * @return
     */
    public GithubUsersDatabase getDatabase() {
        return GithubUsersDatabase.getsDbInstance(this);
    }


    /**
     * ---------------------------------------------------------------------------------------------
     * Return depository singleton instance
     * @return
     */
    // repo standard constructor
    public GithubUsersAppRepository getRepository() {
        return GithubUsersAppRepository.getInstance(getDatabase());
    }


    // repo constructor with network support
    public GithubUsersAppRepository getRepositoryWithNetwork() {
        GithubUsersDatabase db = getDatabase();
        AppExecutors executors = AppExecutors.getInstance();
        NetworkRequests networkRequests = new NetworkRequests();

        return GithubUsersAppRepository.getInstanceWithDataSource(db, networkRequests, executors);
    }


    /**
     * ---------------------------------------------------------------------------------------------
     * Return AppExecutors singleton instance
     * @return
     */
    public AppExecutors getAppExecutorsInstance() {
        return mAppExecutors;
    }


    /**
     * ---------------------------------------------------------------------------------------------
     * Return application context wherever we are in the app
     * @return
     */
    public static Context getsContext() {
        return sContext;
    }

}
