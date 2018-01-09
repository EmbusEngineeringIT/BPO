package bpo.crazygamerzz.com.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by code1 on 1/5/2018.
 */

public class NetworkConnectivity
{
    private static Context context;
    public static boolean isNetworkAvailable()
    {
        ConnectivityManager connectivityManager
                = (ConnectivityManager)((Activity)context).getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        if (activeNetworkInfo != null && activeNetworkInfo.isConnected() == true)
        {
            return true;
        }

        return false;
    }
}
