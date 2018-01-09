package bpo.crazygamerzz.com.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import bpo.crazygamerzz.com.R;
import bpo.crazygamerzz.com.broadcastreceiver.CheckInternetConnection;
import bpo.crazygamerzz.com.networkapi.APIServices;
import bpo.crazygamerzz.com.networkapi.APIUtils;
import bpo.crazygamerzz.com.pojo.DefaultTronXError;
import bpo.crazygamerzz.com.pojo.LoginData;
import bpo.crazygamerzz.com.pojo.NumberData;
import bpo.crazygamerzz.com.sqliteDataBase.SqlDataBase;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginScreen extends AppCompatActivity implements View.OnClickListener{

    private TextView loginButton;
    private String loginStatus;
    private APIServices loginService;
    private EditText loginEmailEdt,loginPswdEdt;
    private SqlDataBase dBHandler;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    public static String emailKey="emailKey";
    private BroadcastReceiver broadcastReceiver;
    public static String APP_PREFERENCES="BPOLOCALEMPID";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        setContentView(R.layout.activity_login_screen);
        sharedPreferences = getSharedPreferences(APP_PREFERENCES,MODE_PRIVATE);
        loginEmailEdt = (EditText) findViewById(R.id.login_email_edt);
        loginPswdEdt = (EditText) findViewById(R.id.login_pswd_edt);
        loginService = APIUtils.getAPIService();
        loginButton=(TextView)findViewById(R.id.login_id);
        dBHandler = new SqlDataBase(LoginScreen.this);
        loginButton.setOnClickListener(this);
        loginStatus = sharedPreferences.getString("LOGIN_STATUS","0");
        if(loginStatus.contentEquals("0")){
            //startActivity(new Intent(LoginScreen.this,Home.class));
        }else if(loginStatus.contentEquals("1")){
            startActivity(new Intent(LoginScreen.this,Home.class));
        }else {}

    }

    private void internetConnection()
    {
        IntentFilter intentFilter=new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
        broadcastReceiver=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                int[] type={ConnectivityManager.TYPE_MOBILE,ConnectivityManager.TYPE_WIFI};

                if (CheckInternetConnection.isNetworkState(context,type) == true)
                {
                    String emailVal = loginEmailEdt.getText().toString().trim();
                    String pswdVal = loginPswdEdt.getText().toString().trim();
                    if(!emailVal.isEmpty()){
                        if(!pswdVal.isEmpty())
                        {
                            validateFieldsNdProceed(emailVal,pswdVal);
                        }
                        else
                        {
                            Toast.makeText(LoginScreen.this,"Please Enter Password",Toast.LENGTH_SHORT).show();
                        }
                    }
                    else
                    {
                        Toast.makeText(LoginScreen.this,"Please Enter Email",Toast.LENGTH_SHORT).show();
                    }
                    return;
                }
                else
                {
                    final AlertDialog.Builder builder=new AlertDialog.Builder(LoginScreen.this);
                    final AlertDialog alertDialog = builder.create();
                    builder.setTitle("Internet Problem");
                    builder.setMessage("Please Connect Internet Then Only Login Should Will Happen");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialogInterface,int id)
                        {
                            alertDialog.dismiss();
                        }
                    });
                    builder.show();
                }
            }
        };

        registerReceiver(broadcastReceiver,intentFilter);
    }


    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.login_id:
                internetConnection();
                break;
        }
    }

        public void validateFieldsNdProceed(String email, String password) {
            final ProgressDialog loginDialog;
            loginDialog = DialogUtils.showProgressDialog(this, "Loading Please Wait.....");
            loginDialog.show();
            loginService.checkLogin(email, password).enqueue(new Callback<LoginData>()
            {
                @Override
                public void onResponse(Call<LoginData> call, Response<LoginData> response) {

                    if(response.isSuccessful())
                    {
                        //showResponse(response.body().toString());
                        loginDialog.dismiss();
                        //Toast.makeText(LoginScreen.this,"db_count_"+dBHandler.checkDataBaseDump(),Toast.LENGTH_LONG).show();
                        if (dBHandler.checkDataBaseDump() == 0){
                            Log.d("db_","null");
                            ArrayList<NumberData> numberDump = response.body().getNumberDump();

                            if(numberDump.size() != 0){
                                for(NumberData numData: numberDump){
                                    ContentValues contentValues=new ContentValues();
                                    contentValues.put(SqlDataBase.SERVER_NUMBER_ID,numData.getPhnId());
                                    contentValues.put(SqlDataBase.CONTACT_NUMBER,numData.getNumber());
                                    contentValues.put(SqlDataBase.NUMBER_FLAG,"0");
                                    dBHandler.addNumbers(contentValues);
                                }
                                dBHandler.getAllContactNumber();
                            }
                            else
                            {
                                Log.d("db_","is_null");
                            }
                        }
                        else
                        {
                            Log.d("db_","already_dumped");
                        }
                        Intent intent=new Intent(LoginScreen.this,Home.class);
                        startActivity(intent);
                        editor=sharedPreferences.edit();
                        editor.putString(emailKey,response.body().getEmail());
                        editor.putString("LOGIN_STATUS","1");
                        editor.commit();
                        finish();
                        Log.i("fial", "post submitted to API." + response.body().toString());
                    }
                    else
                        {
                        try
                        {
                            loginDialog.dismiss();
                            String errorResponse = new String(response.errorBody().bytes());
                            Log.e("Error", response.raw().toString() + " : " + errorResponse);
                            DefaultTronXError xError = new Gson().fromJson(errorResponse, DefaultTronXError.class);
                            //Displaye error message as given by TronX_API.
                            Log.e("Error", xError.getStatus());
                            Log.e("Error", xError.getError());
                            Toast.makeText(getApplicationContext(), xError.getError(), Toast.LENGTH_SHORT).show();
                            Log.e("Error", call.toString());
                            Log.e("Error", call.request().toString());
                            Log.e("Error", call.request().body().toString());
                        }
                        catch (IOException e)
                        {
                            e.printStackTrace();
                        }
                     }
            }
                @Override
                public void onFailure(Call<LoginData> call, Throwable t)
                {
                    loginDialog.dismiss();
                    Log.e("", "Unable to submit post to API."+t.getMessage());
                }
            });
        }

    @Override
    protected void onResume()
    {
        super.onResume();
     //   Toast.makeText(LoginScreen.this,"L_S: "+loginStatus,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}