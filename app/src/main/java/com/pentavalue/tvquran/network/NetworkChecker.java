package com.pentavalue.tvquran.network;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Check the availability of com.pentavalue.tvquran.network ad if it connected or not.
 *
 * @author AhmedRabie
 */
public class NetworkChecker {
    /**
     * @param context
     * @return true if conneciton is exist
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }
}