package bpo.crazygamerzz.com.services;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.security.PrivateKey;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import bpo.crazygamerzz.com.activities.LoginScreen;
import bpo.crazygamerzz.com.networkapi.APIServices;
import bpo.crazygamerzz.com.networkapi.APIUtils;
import bpo.crazygamerzz.com.pojo.CallForwardData;
import bpo.crazygamerzz.com.pojo.CallRemainderData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by code2 on 12/21/2017.
 */

public class Backgroundservices extends IntentService{

    private Handler handler = new Handler();
    private SharedPreferences sharedPreferences;
    private APIServices apiServices;
    private String myEmail;
    public Backgroundservices(String name) {
        super(name);
    }

    public Backgroundservices() {
        super("call forward service");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        apiServices= APIUtils.getAPIService();
        sharedPreferences=getSharedPreferences(LoginScreen.APP_PREFERENCES,MODE_PRIVATE);
        myEmail = sharedPreferences.getString(LoginScreen.emailKey,"");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Log.d("tst_service_","running_"+myEmail);
                apiServices.checkForCallTransfer(myEmail).enqueue(new Callback<CallForwardData>() {
                    @Override
                    public void onResponse(Call<CallForwardData> call, Response<CallForwardData> response) {

                        if (response.isSuccessful()){
                            CallForwardData calForwardedDta = response.body();
                            if(calForwardedDta.getStatusMessage().contentEquals("1")){
                                Log.d("tst_Id:_",""+calForwardedDta.getId()+" num:_"+calForwardedDta.getForwardedNumber());
                                Intent broadCastSender = new Intent();
                                broadCastSender.setAction("com.bpo.newcallreceived");
                                broadCastSender.putExtra("id",calForwardedDta.getId());
                                broadCastSender.putExtra("number",calForwardedDta.getForwardedNumber());
                                sendBroadcast(broadCastSender);
                            }else {
                                Log.d("tst_error_msg_","no_new_call");
                            }
                        }else {
                            Log.d("tst_error_msg_","un_successful");
                        }
                    }

                    @Override
                    public void onFailure(Call<CallForwardData> call, Throwable t) {
                        Log.d("tst_error_msg_","fail!");
                    }
                });

                String currentDate = getDate();
                String currentTime = getTime();
                Log.d("date_"+currentDate," time_"+currentTime);
                apiServices.checkForCallRemainder(myEmail,currentDate,currentTime).enqueue(new Callback<CallRemainderData>() {
                    @Override
                    public void onResponse(Call<CallRemainderData> call, Response<CallRemainderData> response) {
                        if (response.isSuccessful()){
                            CallRemainderData calRemainderDta = response.body();
                            if(calRemainderDta.getStatusMessage().contentEquals("success")){
                                Log.d("rem_tst_Id:_",""+calRemainderDta.getId()+" num:_"+calRemainderDta.getStatusMessage());
                                Intent broadCastRemainderSender = new Intent();
                                broadCastRemainderSender.setAction("com.bpo.newcall.remainder.received");
                                broadCastRemainderSender.putExtra("id",calRemainderDta.getId());
                                broadCastRemainderSender.putExtra("user_name",calRemainderDta.getUserName());
                                broadCastRemainderSender.putExtra("user_number",calRemainderDta.getUserContactNumber());
                                broadCastRemainderSender.putExtra("user_calbck_comment",calRemainderDta.getUserCalBackComment());
                                sendBroadcast(broadCastRemainderSender);
                            }else {
                                Log.d("tst_error_msg_","no_cal_remainder_received");
                            }
                        }else {
                            Log.d("tst_error_msg_","un_successful");
                        }
                    }

                    @Override
                    public void onFailure(Call<CallRemainderData> call, Throwable t) {

                    }
                });
                handler.postDelayed(this,1000);
            }
        };
        handler.postDelayed(runnable,1000);
    }

    private String getTime() {
        String AMORPM;
        Calendar currentTime    = Calendar.getInstance();
        System.out.println("Current time =&gt; "+currentTime.getTime());
        int hour = currentTime.get(Calendar.HOUR_OF_DAY) ;
        int minute = currentTime.get(Calendar.MINUTE);
        int AM_OR_PM = currentTime.get(Calendar.AM_PM);
        if (AM_OR_PM == Calendar.AM){
            AMORPM = "AM";
        }else {
            AMORPM = "PM";
        }
        return hour+":"+minute+":"+AMORPM;
    }

    private String getDate() {

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = df.format(c.getTime());
        // Now formattedDate have current date/time
        Log.d("current_date_",""+formattedDate);
        return formattedDate;

    }

}