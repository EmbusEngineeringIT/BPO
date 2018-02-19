package bpo.crazygamerzz.com.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import bpo.crazygamerzz.com.R;
import bpo.crazygamerzz.com.pojo.QuickStartPreferences;

/**
 * Created by code2 on 1/2/18.
 */

public class GCMRegistrationReceiver extends BroadcastReceiver {

    Context context;

    public GCMRegistrationReceiver(Context context){
        this.context = context;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences sharedPreferences =
                PreferenceManager.getDefaultSharedPreferences(context);
        boolean sentToken = sharedPreferences
                .getBoolean(QuickStartPreferences.SENT_TOKEN_TO_SERVER, false);
        if (sentToken) {
            Log.d("gcm_","registration_complete");
        } else {
            Log.d("gcm_","not_registration_complete");
        }
    }
}
