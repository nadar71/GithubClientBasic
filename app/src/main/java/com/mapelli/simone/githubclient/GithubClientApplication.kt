package com.mapelli.simone.githubclient

import android.app.Application
import android.content.Context

import com.mapelli.simone.githubclient.data.GithubUsersAppRepository
import com.mapelli.simone.githubclient.data.GithubUsersDatabase
import com.mapelli.simone.githubclient.network.NetworkRequests
import com.mapelli.simone.githubclient.util.AppExecutors


/**
 * -------------------------------------------------------------------------------------------------
 * Class used for access classes singletons and application context wherever in the app.
 * Just like repository is an interface for all data operations.
 * NB :
 * registered in manifest in <Application android:name=
".com.mapelli.simone.githubclient.GithubClientApplication"> </Application>
 */
class GithubClientApplication : Application() {
    /**
     * ---------------------------------------------------------------------------------------------
     * Return AppExecutors singleton instance
     * @return
     */
    lateinit var appExecutorsInstance: AppExecutors
        private set

    /**
     * ---------------------------------------------------------------------------------------------
     * Return singleton db instance
     * @return
     */
    val database: GithubUsersDatabase?
        get() = GithubUsersDatabase.getsDbInstance(this)


    /**
     * ---------------------------------------------------------------------------------------------
     * Return depository singleton instance
     * @return
     */
    // repo standard constructor
    val repository: GithubUsersAppRepository
        get() = GithubUsersAppRepository.getInstance(database)


    // repo constructor with network support
    val repositoryWithNetwork: GithubUsersAppRepository
        get() {
            val db = database
            val executors = AppExecutors.instance
            val networkRequests = NetworkRequests()

            return GithubUsersAppRepository.getInstanceWithDataSource(db!!, networkRequests, executors)
        }

    override fun onCreate() {
        super.onCreate()
        appExecutorsInstance = AppExecutors.instance
        sContext = applicationContext
    }

    companion object {
        private var sContext: Context? = null


        /**
         * ---------------------------------------------------------------------------------------------
         * Return application context wherever we are in the app
         * @return
         */
        fun getsContext(): Context? {
            return sContext
        }
    }

}
