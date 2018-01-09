package bpo.crazygamerzz.com.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Kumar on 12/21/2017.
 */

public class CheckInternetConnection extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        int[] type={ConnectivityManager.TYPE_MOBILE,ConnectivityManager.TYPE_WIFI};

        if (isNetworkState(context,type) == true)
        {
            Toast.makeText(context,"Internet Connection",Toast.LENGTH_SHORT).show();
            Log.d("Connection123",""+type);
            return;
        }
        else
        {
            Toast.makeText(context,"No Internet Connection",Toast.LENGTH_SHORT).show();
            Log.d("Connection123","No Internet Connection");
        }
    }

    public static boolean isNetworkState(Context context, int[] netWorkTypes)
    {
        try {
            ConnectivityManager connectivityManager=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
            for (int netWorType : netWorkTypes)
            {
                NetworkInfo networkInfo=connectivityManager.getNetworkInfo(netWorType);

                if (networkInfo !=null && networkInfo.getState() == NetworkInfo.State.CONNECTED)
                {
                    return true;
                }
            }
        }
        catch (Exception  e)
        {
            return false;
        }
        return false;
    }
}
