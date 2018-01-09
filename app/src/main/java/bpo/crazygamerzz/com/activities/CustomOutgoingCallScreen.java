package bpo.crazygamerzz.com.activities;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import bpo.crazygamerzz.com.R;

public class CustomOutgoingCallScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_outgoing_call_screen);
        boolean handler = new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        },3000);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
