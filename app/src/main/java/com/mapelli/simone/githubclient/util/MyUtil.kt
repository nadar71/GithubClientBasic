package com.mapelli.simone.githubclient.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.Log

import com.mapelli.simone.githubclient.GithubClientApplication

object MyUtil {
    private val TAG = MyUtil::class.java!!.getSimpleName()

    /**
     * ---------------------------------------------------------------------------------------------
     * Check if internet connection is on
     */
    // reference to connection manager
    // network status retrieving
    val isConnectionOk: Boolean
        get() {
            val connManager = (GithubClientApplication.getsContext() as GithubClientApplication)
                    .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val netinfo = connManager.activeNetworkInfo
            if (netinfo != null && netinfo.isConnected) {
                Log.d(TAG, "Connections is down !")
                return true
            } else
                return false

        }
}
