package com.mapelli.simone.githubclient.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.mapelli.simone.githubclient.GithubClientApplication;

public class MyUtil {
    private static final String TAG = MyUtil.class.getSimpleName();

    /**
     * ---------------------------------------------------------------------------------------------
     * Check if internet connection is on
     */
    public static boolean isConnectionOk() {
        // reference to connection manager
        ConnectivityManager connManager =
                (ConnectivityManager) ((GithubClientApplication)GithubClientApplication.getsContext())
                        .getSystemService(Context.CONNECTIVITY_SERVICE);

        // network status retrieving
        NetworkInfo netinfo = connManager.getActiveNetworkInfo();
        if (netinfo != null && netinfo.isConnected()) {
            Log.d(TAG, "Connections is down !");
            return true;
        }
        else return false;

    }
}
