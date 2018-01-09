package bpo.crazygamerzz.com.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.app.IntentService;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.RemoteException;
import android.os.SystemClock;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import com.android.internal.telephony.ITelephony;
import com.github.thunder413.datetimeutils.DateTimeUnits;
import com.github.thunder413.datetimeutils.DateTimeUtils;
import com.google.gson.Gson;
import com.rom4ek.arcnavigationview.ArcNavigationView;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import bpo.crazygamerzz.com.CallTransferReceiver.NewCallReceiver;
import bpo.crazygamerzz.com.DateDialogPickerDesignFunction;
import bpo.crazygamerzz.com.R;
import bpo.crazygamerzz.com.broadcastreceiver.CheckInternetConnection;
import bpo.crazygamerzz.com.networkapi.APIServices;
import bpo.crazygamerzz.com.networkapi.APIUtils;
import bpo.crazygamerzz.com.pojo.CallTransferData;
import bpo.crazygamerzz.com.pojo.DefaultTronXError;
import bpo.crazygamerzz.com.pojo.EmployeeStatusData;
import bpo.crazygamerzz.com.pojo.SqlitePojo;
import bpo.crazygamerzz.com.pojo.SubmitData;
import bpo.crazygamerzz.com.pojo.UpdateCallBackResponse;
import bpo.crazygamerzz.com.pojo.UserDataForCallT;
import bpo.crazygamerzz.com.pojo.UsersForCTResponse;
import bpo.crazygamerzz.com.services.Backgroundservices;
import bpo.crazygamerzz.com.sqliteDataBase.SqlDataBase;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener {
    AlertDialog.Builder builderSubmit,builderEmp;
    private BroadcastReceiver broadcastReceiver;
    private AlertDialog.Builder alertDialog,callTransferDialog;
    private FloatingActionButton fabCSrtBtn,fabCStpBtn;
    private NavigationView navigationView;
    private ArrayList<UserDataForCallT> dataResponseList;
    private int RED_COLOR= R.color.red_trans,CALL_STATE = 0; //0-> Call Stopped; 1-> Call running
    private int GREEN_COLOR= R.color.original_green;
    private boolean CALL_STATUS=false;
    private boolean SNACKS_TEA_STATUS=false;
    private boolean LAUNCH_STATUS=false;
    private Intent checkForCT;
    private TextView noOfCallsMade,crntNumHdng,avgCalDur,submitFunction;
    private EditText othersCommentEdit;
    private TelephonyManager telephonicManager;
    private PhoneStateListener phoneCallListener;
    private LinearLayout submitBtnLayout,logOutLayout,commentBoxLayot,manualCallBtnLyout;
    public TextView currentDateTv,snackTeaTv,launchTv,dateTv,timeTv,callTransferTextOk,callTransferTextCancel,prePaidTv,postpaidTv,threeG,fourG,mainHeadNo,employeeIdHeader,logoutEmp,empLeads,relaxationTimerTextView,fwdCall;
    private int FIRST_TIME_COUNTER = 1,CALL_COUNTER = 1;
    private LinearLayout snackTeaBreak,launchBreak;
    private RelativeLayout postPaidLayout,prePaidLayout,networkLayout,plansLayout,commentLayout,amountLayout,threeGLayout,fourGLayout;
    private Chronometer stopWatch;
    public static long timeWhenStopped = 0,avgCallInSec = 0;
    private boolean CHRONOMETER=false;
    private boolean BACK_COLOR=false,G_BACK_CLOR=false,CALL_BACK_OPTIONS=false,status = false,IS_CALL_ON_GOING = false;
    private String[] items={"PrePaid","PostPaid","3G","4G"};
    //private CustomGrid customGrid;
    private GridView gridLayout;
    private  DrawerLayout drawer;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private Calendar calendar;
    private Spinner networkSpinner,plansSpinner,commentSpinner,hindiEmployee,allEmployee;
    private String language;
    private ArrayAdapter<String> simAdapter,plansAdapter,commentsAdapter,hindiAdapter,allAdapter;
    private ArrayList<String> simOptions,plansOffer,commentsBpo,followUpOptions,hindiList,allList;
    private static long start_time_in_millsec = 0, end_time_in_millsec = 0,hours = 0,minutes = 0 ,seconds = 0;
    private EditText amountText;
    private String amountString,emailIdShared,manualCallStatus="No",commentString=null;
    private String s1;
    private AlertDialog dialog;
    private android.app.AlertDialog callIniaterDialog;
    private final int MY_PERMISSIONS_REQUEST_MAKE_CALLS = 222;
    private BroadcastReceiver phoneStateRecever,receiveCallRemainder;
    private LinearLayout callLayout;
    private APIServices apiServices;
    private String TAG="Home.class";
    private boolean isPaused = false,isCanceled = false;
    private View viewHeader;
    private EditText customerName,customerMobileNumber,alternativeNumber,flatNo,streetNo,landMark,cityName,pincode;
    private String prepaidOrPostpaid=null,threeOrFourG=null;
    private boolean LANGUAGE_TRANSFORMATION = false;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    //login logout Time Function
    public static int SUBMIT_COUNTER=0;
    private int finalteaBreak = 0,finallunchBreak=0;

    private String TIME_PREFERENCE="TIME_FUNCTION";
    private String loginKey="LOGIN_KEY";
    private String logoutKey="LOGOUT_KEY";
    private String teaStartKey="TEA_START_KEY";
    private String teaEndingKey="TEA_ENDING_KEY";
    private String lunchStartKey="LUNCH_START_KEY";
    private String lunchEndingKey="LUNCH_ENDING_KEY";
    private String finalTeaBreakKey="Key";
    private String finalLunchBreakKey="KeyLunch";
    private SqlDataBase sqlDataBase;
    private SqlitePojo currentCallData;
    public String teaBreakTimeValue="00:00";
    public int lunchBreakTimeValue=0;
    public NewCallReceiver receiver;
    private SharedPreferences timeShare;
    private SharedPreferences.Editor timerEditor;
    private RelativeLayout dateLayout,timeLayout;
    private CountDownTimer countDownTimer1;
    private long relaxationTimeLeft1=20000;
    public static boolean NEW_CALL_STATUS=false;
    private Animation animation;
    private String chronometerString=null;
    private  long time=0;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        allList=new ArrayList<String>();
        hindiList = new ArrayList<String>();
        animation= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.blinking);
        timeShare=getSharedPreferences(TIME_PREFERENCE,MODE_PRIVATE);
        timerEditor=timeShare.edit();
        sharedPreferences=getSharedPreferences(LoginScreen.APP_PREFERENCES,MODE_PRIVATE);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        viewHeader=navigationView.getHeaderView(0);
        //viewHeader=(View)findViewById(R.layout.nav_header_home);
        mainHeadNo=(TextView)findViewById(R.id.num_hd);
        manualCallBtnLyout = (LinearLayout) viewHeader.findViewById(R.id.manual_call_btn_lyout);
        othersCommentEdit = (EditText) findViewById(R.id.others_comment_edit);
        employeeIdHeader=(TextView)viewHeader.findViewById(R.id.emp_id_header);
        customerName=(EditText)findViewById(R.id.name_edit);
        customerMobileNumber=(EditText)findViewById(R.id.current_number_edit);
        alternativeNumber=(EditText)findViewById(R.id.mobile_edit);
        flatNo=(EditText)findViewById(R.id.flat_edit);
        fwdCall=(TextView)findViewById(R.id.fwd_call);
        streetNo=(EditText)findViewById(R.id.street_edit);
        landMark=(EditText)findViewById(R.id.land_edit);
        cityName=(EditText)findViewById(R.id.city_edit);
        pincode=(EditText)findViewById(R.id.pincode_edit);
        emailIdShared=sharedPreferences.getString(LoginScreen.emailKey,"");
        employeeIdHeader.setText(emailIdShared);
        empLeads=(TextView)viewHeader.findViewById(R.id.emp_lead_header);
        prePaidTv=(TextView)findViewById(R.id.prepaid_id);
        postpaidTv=(TextView)findViewById(R.id.postpaid_id);
        threeG=(TextView)findViewById(R.id.three_g_id);
        fourG=(TextView)findViewById(R.id.four_g_id);
        currentDateTv=(TextView)viewHeader.findViewById(R.id.curntdate_tv);
        snackTeaBreak=(LinearLayout)viewHeader.findViewById(R.id.snack_tea_back);
        snackTeaTv=(TextView)viewHeader.findViewById(R.id.snack_tea_id);
        launchBreak=(LinearLayout)viewHeader.findViewById(R.id.launch_back);
        launchTv=(TextView)viewHeader.findViewById(R.id.launch_id);
        stopWatch = (Chronometer)viewHeader.findViewById(R.id.chrono);
        dateLayout=(RelativeLayout)findViewById(R.id.date_layout);
        timeLayout=(RelativeLayout)findViewById(R.id.time_layout);
        dateLayout.setOnClickListener(this);
        timeLayout.setOnClickListener(this);
        avgCalDur = (TextView) viewHeader.findViewById(R.id.avg_cal_dur);
        noOfCallsMade = (TextView) viewHeader.findViewById(R.id.no_of_calls);
       // gridLayout=(GridView)findViewById(R.id.grid);
        dateTv=(TextView)findViewById(R.id.date_tv_id);
        timeTv=(TextView)findViewById(R.id.time_tv_id);
        prePaidLayout=(RelativeLayout)findViewById(R.id.prepaid_lay_id);
        postPaidLayout=(RelativeLayout)findViewById(R.id.postpaid_lay_id);
        threeGLayout=(RelativeLayout)findViewById(R.id.three_g_lay_id);
        fourGLayout=(RelativeLayout)findViewById(R.id.four_g_lay_id);
        networkLayout=(RelativeLayout)findViewById(R.id.net_lay_id);
        plansLayout=(RelativeLayout)findViewById(R.id.plans_lay_id);
        commentLayout=(RelativeLayout)findViewById(R.id.cmt_lay_id);
        amountLayout=(RelativeLayout)findViewById(R.id.amt_lay_id);
        submitBtnLayout = (LinearLayout) findViewById(R.id.submit_btn_layout);
        logOutLayout = (LinearLayout) viewHeader.findViewById(R.id.log_out_layout);
        amountText=(EditText)findViewById(R.id.amt_id);
        submitFunction=(TextView)findViewById(R.id.submit_id);
        commentBoxLayot = (LinearLayout) findViewById(R.id.comnt_box_edt);
        submitFunction.setOnClickListener(this);
        submitBtnLayout.setOnClickListener(this);
        manualCallBtnLyout.setOnClickListener(this);
        logOutLayout.setOnClickListener(this);
        dateTv.setOnClickListener(this);
        timeTv.setOnClickListener(this);
        logoutEmp=(TextView)viewHeader.findViewById(R.id.emp_logout);
        logoutEmp.setOnClickListener(this);
        sqlDataBase = new SqlDataBase(this);
        amountString=amountText.getText().toString().trim();
        relaxationTimerTextView=(TextView)toolbar.findViewById(R.id.relax_id);
        apiServices= APIUtils.getAPIService();
        initiateCallForwardService();
        sqlDataBase.testValues();
        IntentFilter filter = new IntentFilter("com.bpo.newcallreceived");

        receiver = new NewCallReceiver();
        registerReceiver(receiver, filter);

        phoneStateRecever = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                if (intent.getAction().equals("android.intent.action.NEW_OUTGOING_CALL")){
                    //String phoneNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
                    //Toast.makeText(context,"Call Out : "+phoneNumber,Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent i = new Intent(Home.this, CustomOutgoingCallScreen.class);
//                            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            startActivity(i);
                        }
                    }, 800);
                }else {

                    String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
                    if(state.equals(TelephonyManager.EXTRA_STATE_RINGING)){

                    }

                    if ((state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK))){

                        fabCSrtBtn.setBackgroundTintList(getResources().getColorStateList(R.color.red_trans));
                        CALL_STATE = 1;
                        status = false;
                        //fab.setEnabled(false);
                        //IS_CALL_ON_GOING = true;
                        start_time_in_millsec = System.currentTimeMillis();
                    }

                    if (state.equals(TelephonyManager.EXTRA_STATE_IDLE)){

                        //Toast.makeText(context,"Idle State",Toast.LENGTH_SHORT).show();
                        if(!status){
                            Log.d("idle_","state");
                            countDownTimer1.start();
                            relaxationTimerTextView.startAnimation(animation);
                            end_time_in_millsec = System.currentTimeMillis();
                            long callDuration = end_time_in_millsec - start_time_in_millsec;
                            avgCallInSec += callDuration;
                            fabCSrtBtn.setBackgroundTintList(getResources().getColorStateList(R.color.original_green));
                            CALL_STATE = 0;
                            //fab.setEnabled(true);
                            String finalCallDuration = getFinalCallDuration(callDuration);
                            Log.d("call_duration_:",""+finalCallDuration);
                            clearGlobalVariables();
                            status = true;
                        }
                    }
                }

            }
        };

        IntentFilter actionFilter = new IntentFilter();
        actionFilter.addAction("android.intent.action.PHONE_STATE");
        actionFilter.addAction("android.intent.action.NEW_OUTGOING_CALL");

        this.registerReceiver(phoneStateRecever,actionFilter);

        receiveCallRemainder = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                String Id = intent.getStringExtra("ID");
                String userName = intent.getStringExtra("USERNAME");
                String userNumber = intent.getStringExtra("USERNUMBER");
                String userComment = intent.getStringExtra("USERCOMMENT");
                showPopUpToUser(Id,userNumber,userComment);
            }
        };

        IntentFilter callBckfilter = new IntentFilter();
        callBckfilter.addAction("bpo.crazygamerzz.com.action.calback.received");
        this.registerReceiver(receiveCallRemainder,callBckfilter);
        telephonicManager = (TelephonyManager) getSystemService(getApplicationContext().TELEPHONY_SERVICE);
        amountText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                amountLayout.setBackgroundColor(getResources().getColor(R.color.green));

            }
        });

        networkSpinner=(Spinner)findViewById(R.id.net_spnr_id);
        plansSpinner=(Spinner)findViewById(R.id.plans_spr_id);
        commentSpinner=(Spinner)findViewById(R.id.cmt_spr_id);
       // followUpSpinner=(Spinner)findViewById(R.id.followup_spr_id);
        simOptions = new ArrayList<String>();
        simOptions.add("Select");
        simOptions.add("Airtel");
        simOptions.add("Aircel");
        simOptions.add("Vodafone");
        simOptions.add("Idea");
        simOptions.add("Tata Docomo");
        simOptions.add("Telenor");
        simOptions.add("Jio");
        simOptions.add("BSNL");
        simOptions.add("MTNL");

        simOptions.add("Reliance Communications");

        plansOffer=new ArrayList<String>();
        plansOffer.add("Select");
        plansOffer.add("299");
        plansOffer.add("399");
        plansOffer.add("499");
        plansOffer.add("599");

        simAdapter=new ArrayAdapter<String>(this,R.layout.spinner_item,simOptions);
        simAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        networkSpinner.setAdapter(simAdapter);

        networkSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //String s= String.valueOf(parent.getItemIdAtPosition(position));


                if (position == 0)
                {
                    networkLayout.setBackgroundColor(getResources().getColor(R.color.light_blue));
                }
                else
                {
                    networkLayout.setBackgroundColor(getResources().getColor(R.color.green));
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        plansAdapter=new ArrayAdapter<String>(this,R.layout.spinner_item,plansOffer);
        plansAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        plansSpinner.setAdapter(plansAdapter);

        plansSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String s= String.valueOf(parent.getItemIdAtPosition(position));
                if (position == 0)
                {
                    plansLayout.setBackgroundColor(getResources().getColor(R.color.light_blue));
                }
                else
                {
                    plansLayout.setBackgroundColor(getResources().getColor(R.color.green));
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        commentsBpo = new ArrayList<String>();
        commentsBpo.add("Select");
        commentsBpo.add("Not Interested");
        commentsBpo.add("Call Transfer");
        commentsBpo.add("Network Problem");
        commentsBpo.add("Not Reachable");
        commentsBpo.add("Out Of Area");
        commentsBpo.add("Schedule Pickup");
        commentsBpo.add("Fancy Number Request");
        commentsBpo.add("Call Back");
        commentsBpo.add("Others");

        commentsAdapter=new ArrayAdapter<String>(this,R.layout.spinner_item,commentsBpo);
        commentsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        commentSpinner.setAdapter(commentsAdapter);
        commentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
                final String s= String.valueOf(parent.getItemIdAtPosition(position));
                Toast.makeText(Home.this,""+position,Toast.LENGTH_SHORT).show();

                if (position == 0)
                {
                    SUBMIT_COUNTER=0;
                    commentLayout.setBackgroundColor(getResources().getColor(R.color.light_blue));
                    hideCommentBox();
                }
                else
                {
                    if (position ==1)
                    {
                        SUBMIT_COUNTER=1;
                        hideCommentBox();
                    }
                    else if (position ==2)
                    {
                        SUBMIT_COUNTER=2;
                        hideCommentBox();
                        callTransferDialog=new AlertDialog.Builder(Home.this);
                        LayoutInflater layoutInflater=getLayoutInflater();
                        View view1=layoutInflater.inflate(R.layout.call_transfer_dialog,null);
                        callTransferDialog.setView(view1);
                        hindiEmployee=(Spinner)view1.findViewById(R.id.hindi_content);
                        allEmployee=(Spinner)view1.findViewById(R.id.all_content);
                        callTransferTextOk=(TextView)view1.findViewById(R.id.call_trans_ok);
                        callTransferTextCancel=(TextView)view1.findViewById(R.id.call_trans_cancel);
                        hindiAdapter=new ArrayAdapter<String>(Home.this,R.layout.spinner_item,hindiList);
                        hindiAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        hindiEmployee.setAdapter(hindiAdapter);
                        hindiEmployee.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                s1 = hindiEmployee.getSelectedItem().toString();

                                if (LANGUAGE_TRANSFORMATION == false)
                                {
                                    language = hindiEmployee.getSelectedItem().toString();
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                        allAdapter=new ArrayAdapter<String>(Home.this,R.layout.spinner_item,allList);
                        allAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        allEmployee.setAdapter(allAdapter);
                        allEmployee.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
                        {
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
                            {
                                s1= allEmployee.getSelectedItem().toString();
                                if (LANGUAGE_TRANSFORMATION == false)
                                {
                                    language=allEmployee.getSelectedItem().toString();
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });

                        callTransferTextOk.setOnClickListener(new View.OnClickListener()
                        {
                            @Override
                            public void onClick(View v)
                            {
                                String number,emailId;
                                number=mainHeadNo.getText().toString();
                                if(!language.contains("Select")){
                                    emailId=getUserEmailId(language);
                                    Log.d("email_tr_",""+emailId);
                                    if(!emailId.isEmpty()){
                                        if(!number.isEmpty()){
                                            callTransferFunction(emailId,number);
                                        }else {
                                            Toast.makeText(Home.this,"Number Is null",Toast.LENGTH_SHORT).show();
                                        }
                                    }else {
                                        Toast.makeText(Home.this,"Email Is null",Toast.LENGTH_SHORT).show();
                                    }
                                    dialog.dismiss();
                                }else {
                                    Toast.makeText(Home.this,"Please Select Person",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                        callTransferTextCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                commentSpinner.setSelection(0);
                                dialog.dismiss();
                            }
                        });

                        dialog = callTransferDialog.create();
                        dialog.show();

                    }
                    else if (position == 3)
                    {
                        SUBMIT_COUNTER=3;
                        hideCommentBox();
                    }
                    else if (position == 4)
                    {
                        SUBMIT_COUNTER=4;
                        hideCommentBox();

                    }
                    else if (position == 5)
                    {
                        SUBMIT_COUNTER=5;
                        hideCommentBox();

                    }
                    else if(position == 6)
                    {
                        SUBMIT_COUNTER=6;
                        hideCommentBox();
                    }
                    else if(position == 7)
                    {
                        SUBMIT_COUNTER=7;
                        hideCommentBox();
                    }else if (position == 8){
                        SUBMIT_COUNTER = 8;
                        commentBoxLayot.setVisibility(View.VISIBLE);
                    }else if(position == 9){
                        SUBMIT_COUNTER = 9;
                        commentBoxLayot.setVisibility(View.VISIBLE);
                    }else {
                        hideCommentBox();
                    }

                    commentLayout.setBackgroundColor(getResources().getColor(R.color.green));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });

        prePaidLayout.setOnClickListener(this);
        postPaidLayout.setOnClickListener(this);
        threeGLayout.setOnClickListener(this);
        fourGLayout.setOnClickListener(this);

        prePaidLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                prePaidLayout.setBackgroundColor(getResources().getColor(R.color.light_blue));
                BACK_COLOR=true;
                return true;
            }
        });

        postPaidLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                postPaidLayout.setBackgroundColor(getResources().getColor(R.color.light_blue));
                BACK_COLOR=true;
                return true;
            }
        });

        threeGLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                threeGLayout.setBackgroundColor(getResources().getColor(R.color.light_blue));
                G_BACK_CLOR=true;
                return true;
            }
        });

        fourGLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                fourGLayout.setBackgroundColor(getResources().getColor(R.color.light_blue));
                G_BACK_CLOR=true;
                return true;
            }
        });

        dateTv.setOnClickListener(this);
        timeTv.setOnClickListener(this);
        snackTeaTv.setOnClickListener(this);
        launchTv.setOnClickListener(this);
        currentDateTv.setText(getDateTime());
        fabCSrtBtn = (FloatingActionButton) findViewById(R.id.fab_csrt_btn);
        fabCSrtBtn.setOnClickListener(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        getCurrentPhoneNumberData();
        if (currentCallData != null){
            mainHeadNo.setText(currentCallData.getNumber());
            customerMobileNumber.setText(currentCallData.getNumber());
        }
        //Populating navigation view details from sharedpreferences
        empLeads.setText(sharedPreferences.getString("LEAD_COUNT","0"));
        noOfCallsMade.setText(sharedPreferences.getString("NO_OF_CALLS","0"));
        avgCalDur.setText(sharedPreferences.getString("AVG_TIME_PER_CALL","0"));


        countDownTimer1=new CountDownTimer(relaxationTimeLeft1,1000) {
            @Override
            public void onTick(long millisUntilFinished)
            {
                relaxationTimeLeft1= millisUntilFinished;
                updateRelaxationTimer();
            }

            @Override
            public void onFinish()
            {
                relaxationTimerTextView.setText("Time Over ");
                countDownTimer1.cancel();
                Toast.makeText(getApplicationContext(), "Time Over For New Call", Toast.LENGTH_SHORT).show();
                Log.d("Status","Working");
            }
        };

    }

    private void showPopUpToUser(final String id, final String number, String customerComment) {

        Toast.makeText(Home.this,"Call Back Received!",Toast.LENGTH_SHORT).show();
        android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(Home.this);
        alertDialog.setTitle("Call Back : "+number);
        alertDialog.setMessage(""+customerComment);
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton("Snooze", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                dialogInterface.dismiss();
                updateCallBackDataToServer(id);
            }
        });

        alertDialog.setNegativeButton("Call", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                    dialogInterface.dismiss();
                    mainHeadNo.setText(number);
                    customerMobileNumber.setText(number);
                    initiateCall("tel:"+number);
                //initiateCall(newCallNum,dialogInterface);
            }
        });

        callIniaterDialog = alertDialog.create();
        callIniaterDialog.show();

       /* AlertDialog dialog = null;

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setTitle("Call Back : "+number);
        alertBuilder.setMessage(""+customerComment);
        final AlertDialog finalDialog = dialog;
        alertBuilder.setPositiveButton("Call", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finalDialog.dismiss();
                initiateCall(number);
            }
        });
        dialog = alertBuilder.create();
        dialog.show();*/

    }

    private void updateCallBackDataToServer(String id) {

        String updatableDate = getDate();
        String updatableTime = getTime();
        apiServices.updateCallRemainder(id,updatableDate,updatableTime).enqueue(new Callback<UpdateCallBackResponse>() {
            @Override
            public void onResponse(Call<UpdateCallBackResponse> call, Response<UpdateCallBackResponse> response) {
                if(response.isSuccessful()){
                    UpdateCallBackResponse responseDta = response.body();
                    Toast.makeText(Home.this,""+responseDta.getStatusMessage(),Toast.LENGTH_SHORT).show();
                }else {
                    Log.d("update_went_","wrong");
                }
            }

            @Override
            public void onFailure(Call<UpdateCallBackResponse> call, Throwable t) {
                Log.d("un_expected_","update_call_back_error");
            }
        });

    }

    private String getDate() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = df.format(c.getTime());
        Log.d("current_date_",""+formattedDate);
        return formattedDate;
    }

    private String getTime() {

        String AMORPM;
        Calendar currentTime    = Calendar.getInstance();
        System.out.println("Current time =&gt; "+currentTime.getTime());
        int hour = currentTime.get(Calendar.HOUR_OF_DAY) ;
        int minute = currentTime.get(Calendar.MINUTE);
        minute += 5;
        int AM_OR_PM = currentTime.get(Calendar.AM_PM);
        if (AM_OR_PM == Calendar.AM){
            AMORPM = "AM";
        }else {
            AMORPM = "PM";
        }
        return hour+":"+minute+":"+AMORPM;

    }




    private String getUserEmailId(String language) {
        for(UserDataForCallT callTrsnsData: dataResponseList){
            if(callTrsnsData.getUserName().equals(language)){
                Log.d("data_","found");
                return callTrsnsData.getUserEmail();
            }else {
                Log.d("data_","miss_match");
            }
        }
        return null;

    }

    private void hideCommentBox() {
        commentBoxLayot.setVisibility(View.GONE);
        othersCommentEdit.setText("");
    }

    private int getNumberOfCalls() {
        return sqlDataBase.getNoFCalls();
    }

    private String getFinalCallDuration(long callDuration) {
        return String.valueOf(callDuration);
    }

    private void initiateCallForwardService() {
        checkForCT = new Intent(this, Backgroundservices.class);
        startService(checkForCT);
    }

    public void chronoMeter()
    {
        stopWatch.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {

                String s=sharedPreferences.getString("CRONO_METER","0");

                    time=Long.parseLong(s);
                    time= SystemClock.elapsedRealtime() - chronometer.getBase();
                  //  Toast.makeText(Home.this,"time: "+time,Toast.LENGTH_SHORT).show();
                    int hours=(int) (time / 3600000);
                    int mins=(int) (time - hours*3600000)/60000;
                    int sec=(int) (time - hours*3600000 - mins*60000)/1000;
                    String hh = hours < 10 ? "0"+hours: hours+"";
                    String mm = mins < 10 ? "0"+mins: mins+"";
                    String ss = sec < 10 ? "0"+sec: sec+"";
                    chronometer.setText(hh+":"+mm+":"+ss);

            }
        });
        stopWatch.setBase(SystemClock.elapsedRealtime() + timeWhenStopped);
        stopWatch.start();
    }

    public String getDateTime()
    {
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        Date date=new Date();
        return simpleDateFormat.format(date);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View v) {
    switch (v.getId())
        {
            case R.id.snack_tea_id:
                if (LAUNCH_STATUS == true)
                {

                }
                else
                {
                    if (SNACKS_TEA_STATUS == false)
                    {
                        String teaTimeStart=breakTime();
                        timerEditor=timeShare.edit();
                        timerEditor.putString(teaStartKey,teaTimeStart);
                        timerEditor.commit();
                        timeWhenStopped = stopWatch.getBase() - SystemClock.elapsedRealtime();
                        stopWatch.stop();
                        snackTeaBreak.setBackgroundResource(R.drawable.break_back);
                        snackTeaTv.setTextColor(getResources().getColor(R.color.pay_gold));
                        SNACKS_TEA_STATUS=true;
                    }
                    else
                    {
                        int teaStart,teaEnd;
                        String teaBreakString;
                        String teaTimeEnding=breakTime();
                        timerEditor=timeShare.edit();
                        timerEditor.putString(teaEndingKey,teaTimeEnding);
                        timerEditor.commit();
                        String s=timeShare.getString(teaStartKey,"");
                        String s2=timeShare.getString(teaEndingKey,"");
                        teaStart= (int) DateTimeUtils.timeToMillis(s);
                        teaEnd= (int) DateTimeUtils.timeToMillis(s2);

                        int timeDiff=teaEnd-teaStart;
                        finalteaBreak= timeDiff+finalteaBreak;
                        teaBreakString=DateTimeUtils.millisToTime(finalteaBreak);
                        timerEditor=timeShare.edit();
                        timerEditor.putString(finalTeaBreakKey,teaBreakString);
                        timerEditor.commit();
                        Log.d("teaBreak","  "+teaBreakString);
                        stopWatch.setBase(SystemClock.elapsedRealtime() + timeWhenStopped);
                        stopWatch.start();
                        snackTeaBreak.setBackgroundResource(R.color.light_blue);
                        snackTeaTv.setTextColor(getResources().getColor(R.color.white));
                        SNACKS_TEA_STATUS=false;
                    }
                }

                break;
            case R.id.fab_csrt_btn:
                    if (CALL_STATE == 0)
                    {
                        if (NEW_CALL_STATUS == true)
                        {
                            countDownTimer1.cancel();
                        }
                        makeACall();
                    }
                    else
                    {
                        cutCurrentCall();
                    }
                break;

            case R.id.submit_btn_layout:
                mainHeadNo.setText("0000000000");
                break;
            case R.id.log_out_layout:
                if (drawer.isDrawerOpen(GravityCompat.START))
                {
                    drawer.closeDrawer(GravityCompat.START);
                }
                finish();
                break;

            case R.id.manual_call_btn_lyout:
                if (drawer.isDrawerOpen(GravityCompat.START))
                {
                    drawer.closeDrawer(GravityCompat.START);
                }
                isCanceled = true;
                showManualCallDialog();
                Toast.makeText(Home.this,"manual call",Toast.LENGTH_SHORT).show();
                break;

            case R.id.launch_id:

                if (SNACKS_TEA_STATUS ==true)
                {

                }
                else
                {
                    if (LAUNCH_STATUS == false)
                    {
                        String lunchTimeStart=breakTime();
                        timerEditor=timeShare.edit();
                        timerEditor.putString(lunchStartKey,lunchTimeStart);
                        timerEditor.commit();
                        timeWhenStopped= stopWatch.getBase() - SystemClock.elapsedRealtime();
                        stopWatch.stop();
                        launchBreak.setBackgroundResource(R.drawable.break_back);
                        launchTv.setTextColor(getResources().getColor(R.color.pay_gold));
                        LAUNCH_STATUS=true;
                    }
                    else
                    {
                        int lunchStart,lunchEnd;
                        String lunchTimeEnd=breakTime();
                        timerEditor=timeShare.edit();
                        timerEditor.putString(lunchEndingKey,lunchTimeEnd);
                        timerEditor.commit();
                        String string=timeShare.getString(lunchStartKey,"");
                        String string1=timeShare.getString(lunchEndingKey,"");

                        lunchStart=(int) DateTimeUtils.timeToMillis(string);
                        lunchEnd=(int) DateTimeUtils.timeToMillis(string1);

                        int lunchDiff=lunchEnd-lunchStart;
                        finallunchBreak=lunchDiff+finallunchBreak;

                        String lunchString=DateTimeUtils.millisToTime(finallunchBreak);
                        Log.d("lunchBreak","  "+lunchString);
                        timerEditor=timeShare.edit();
                        timerEditor.putString(finalLunchBreakKey,lunchString);
                        timerEditor.commit();

                        stopWatch.setBase(SystemClock.elapsedRealtime() + timeWhenStopped);
                        stopWatch.start();
                        launchBreak.setBackgroundResource(R.color.light_blue);
                        launchTv.setTextColor(getResources().getColor(R.color.white));
                        timerEditor.commit();
                        LAUNCH_STATUS=false;
                    }
                }
                break;

            case R.id.date_layout:
                final AlertDialog.Builder builder=new AlertDialog.Builder(this);
                builder.setCancelable(false);
                LayoutInflater layoutInflater=getLayoutInflater();
                View view=layoutInflater.inflate(R.layout.date_picker_layout,null);
                builder.setView(view);

                TextView cancelTxt,okTxt;
                final DatePicker datePicker;

                Calendar calendar=Calendar.getInstance();
                final int calYear=calendar.get(Calendar.YEAR);
                final int calMonth=calendar.get(Calendar.MONTH);
                final int calDay=calendar.get(Calendar.DAY_OF_MONTH);
                cancelTxt=(TextView)view.findViewById(R.id.cancel_txt);
                okTxt=(TextView)view.findViewById(R.id.ok_txt);
                datePicker=(DatePicker)view.findViewById(R.id.date_picker_id);
                datePicker.setMinDate(calendar.getTimeInMillis()-1000);
                final AlertDialog alertDialog1=builder.create();
                cancelTxt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dateTv.setText(null);
                        dateLayout.setBackgroundColor(getResources().getColor(R.color.light_blue));
                        alertDialog1.dismiss();
                    }
                });

                okTxt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String day = String.valueOf(datePicker.getDayOfMonth());
                        String month = String.valueOf(datePicker.getMonth()+1);
                        String year = String.valueOf(datePicker.getYear());

                        if(Integer.parseInt(day) < 10)
                            day = "0"+day;

                        if(Integer.parseInt(month) < 10)
                            month = "0"+month;

                        if(Integer.parseInt(year) >= calYear )
                        {
                                    dateTv.setText(day+"-"+month+"-"+year);
                                    dateLayout.setBackgroundColor(getResources().getColor(R.color.green));
                                    alertDialog1.dismiss();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Select Validate Date",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                alertDialog1.show();

                break;

            case R.id. time_layout:

                final AlertDialog.Builder timeBuilder=new AlertDialog.Builder(this);
                timeBuilder.setCancelable(false);
                LayoutInflater layoutInflater1=getLayoutInflater();
                View view1=layoutInflater1.inflate(R.layout.time_picker_layout,null);
                timeBuilder.setView(view1);

                TextView cancelTxtTime,okTxtTime;
                final TimePicker timePicker;

                cancelTxt=(TextView)view1.findViewById(R.id.cancel_txt_time);
                okTxt=(TextView)view1.findViewById(R.id.ok_txt_time);
                timePicker=(TimePicker)view1.findViewById(R.id.time_picker_id);

                final AlertDialog alertDialog2=timeBuilder.create();
                cancelTxt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        timeTv.setText(null);
                        timeLayout.setBackgroundColor(getResources().getColor(R.color.light_blue));
                        alertDialog2.dismiss();
                    }
                });


                okTxt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int hour = timePicker.getCurrentHour();
                        int mins = timePicker.getCurrentMinute();
                        String AM_PM="AM";
                        if(hour > 11)
                        {
                            //hour=hour-12;
                            AM_PM="PM";
                        }
                        timeTv.setText(hour + ":"+mins+":"+AM_PM);
                        timeLayout.setBackgroundColor(getResources().getColor(R.color.green));
                        alertDialog2.dismiss();
                    }
                });
                alertDialog2.show();
                break;

            case R.id.prepaid_lay_id:
                BACK_COLOR=false;
                if (BACK_COLOR == false)
                {
                    prePaidLayout.setBackgroundColor(getResources().getColor(R.color.green));
                    postPaidLayout.setBackgroundColor(getResources().getColor(R.color.light_blue));
                    prepaidOrPostpaid=prePaidTv.getText().toString();
                    BACK_COLOR=true;
                }
                else
                {
                    prePaidLayout.setBackgroundColor(getResources().getColor(R.color.light_blue));
                    BACK_COLOR=false;
                }

            break;

            case R.id.postpaid_lay_id:
                BACK_COLOR=false;
                if (BACK_COLOR == false)
                {
                    prepaidOrPostpaid=postpaidTv.getText().toString();

                    postPaidLayout.setBackgroundColor(getResources().getColor(R.color.green));
                    prePaidLayout.setBackgroundColor(getResources().getColor(R.color.light_blue));
                    BACK_COLOR=true;
                }
                else
                {
                    postPaidLayout.setBackgroundColor(getResources().getColor(R.color.light_blue));
                    BACK_COLOR=false;
                }
                break;

            case R.id.three_g_lay_id:
                G_BACK_CLOR=false;
                if (G_BACK_CLOR == false)
                {
                    threeGLayout.setBackgroundColor(getResources().getColor(R.color.green));
                    fourGLayout.setBackgroundColor(getResources().getColor(R.color.light_blue));
                    threeOrFourG=threeG.getText().toString();
                    G_BACK_CLOR=true;
                }
                else
                {
                    threeOrFourG=fourG.getText().toString();
                    threeGLayout.setBackgroundColor(getResources().getColor(R.color.light_blue));
                    G_BACK_CLOR=false;
                }
                break;

            case R.id.four_g_lay_id:
                G_BACK_CLOR=false;
                if (G_BACK_CLOR == false)
                {
                    threeOrFourG=fourG.getText().toString();
                    fourGLayout.setBackgroundColor(getResources().getColor(R.color.green));
                    threeGLayout.setBackgroundColor(getResources().getColor(R.color.light_blue));
                    G_BACK_CLOR=true;
                }
                else
                {
                    fourGLayout.setBackgroundColor(getResources().getColor(R.color.light_blue));
                    G_BACK_CLOR=false;
                }
                break;

            case R.id.submit_id:

                if(CALL_STATE == 1)
                {
                    Toast.makeText(Home.this,"Please End Call",Toast.LENGTH_SHORT).show();
                    break;
                }

                IntentFilter intentFilter=new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
                broadcastReceiver=new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        int[] type={ConnectivityManager.TYPE_MOBILE,ConnectivityManager.TYPE_WIFI};

                        if (CheckInternetConnection.isNetworkState(context,type) == true)
                        {
                            String networkString=null,amountString=null,planString=null,dateString=null,timeString=null,
                                    customerNameString=null,customerMobileNumberString=null,alternativeNumberString=null,flatNoString=null,streetNoString=null,landMarkString=null,
                                    cityNameString=null,pincodeString=null,emailId=null,callNumber=null,preOrPostString=null,threeOrFourGString=null;

                            networkString=networkSpinner.getSelectedItem().toString().trim();
                            amountString=amountText.getText().toString().trim();
                            planString=plansSpinner.getSelectedItem().toString().trim();
                            commentString=commentSpinner.getSelectedItem().toString();
                            dateString=dateTv.getText().toString().trim();
                            timeString=timeTv.getText().toString().trim();
                            customerNameString=customerName.getText().toString().trim();
                            customerMobileNumberString=mainHeadNo.getText().toString().trim();
                            preOrPostString=prepaidOrPostpaid;
                            threeOrFourGString=threeOrFourG;
                            alternativeNumberString=alternativeNumber.getText().toString().trim();
                            flatNoString=flatNo.getText().toString().trim();
                            streetNoString=streetNo.getText().toString().trim();
                            landMarkString=landMark.getText().toString().trim();
                            cityNameString=cityName.getText().toString().trim();
                            pincodeString=pincode.getText().toString().trim();
                            emailId=employeeIdHeader.getText().toString().trim();
                            callNumber=mainHeadNo.getText().toString().trim();
                            String userSpecificComment = othersCommentEdit.getText().toString();

                            if(currentCallData != null) {

                                if (currentCallData.isManualCallFlag()) {
                                    manualCallStatus = "yes";
                                } else {
                                    manualCallStatus = "No";
                                }
                                Log.d("Details", "" + networkString + "\n" + amountString + "\n" + planString + "\n" + commentString + "\n" + dateString + "\n" + timeString +
                                        "\n" + customerNameString + "\n" + customerMobileNumberString + "\n" + alternativeNumberString + "\n" + flatNoString + "\n" + streetNoString + "\n" +
                                        landMarkString + "\n" + cityNameString + "\n" + pincodeString + "\n" + emailId + "\n" + callNumber + "\n" + preOrPostString + "\n" + threeOrFourGString + "\n");

                                if (SUBMIT_COUNTER == 0) {
                                    Toast.makeText(getApplicationContext(), "Select Comment", Toast.LENGTH_SHORT).show();
                                } else if (SUBMIT_COUNTER == 1) {
                                    postSubmit(emailId, callNumber, preOrPostString, threeOrFourGString, amountString, planString, commentString, dateString, timeString, customerNameString, customerMobileNumberString, alternativeNumberString, flatNoString, streetNoString, landMarkString, cityNameString, pincodeString, networkString, manualCallStatus, userSpecificComment);
                                    resetAllDeatils();
                                } else if (SUBMIT_COUNTER == 2) {
                                    postSubmit(emailId, callNumber, preOrPostString, threeOrFourGString, amountString, planString, commentString, dateString, timeString, customerNameString, customerMobileNumberString, alternativeNumberString, flatNoString, streetNoString, landMarkString, cityNameString, pincodeString, networkString, manualCallStatus, userSpecificComment);
                                    resetAllDeatils();
                                } else if (SUBMIT_COUNTER == 3) {
                                    postSubmit(emailId, callNumber, preOrPostString, threeOrFourGString, amountString, planString, commentString, dateString, timeString, customerNameString, customerMobileNumberString, alternativeNumberString, flatNoString, streetNoString, landMarkString, cityNameString, pincodeString, networkString, manualCallStatus, userSpecificComment);
                                    resetAllDeatils();
                                } else if (SUBMIT_COUNTER == 4) {
                                    postSubmit(emailId, callNumber, preOrPostString, threeOrFourGString, amountString, planString, commentString, dateString, timeString, customerNameString, customerMobileNumberString, alternativeNumberString, flatNoString, streetNoString, landMarkString, cityNameString, pincodeString, networkString, manualCallStatus, userSpecificComment);
                                    resetAllDeatils();
                                } else if (SUBMIT_COUNTER == 5) {
                                    postSubmit(emailId, callNumber, preOrPostString, threeOrFourGString, amountString, planString, commentString, dateString, timeString, customerNameString, customerMobileNumberString, alternativeNumberString, flatNoString, streetNoString, landMarkString, cityNameString, pincodeString, networkString, manualCallStatus, userSpecificComment);
                                    resetAllDeatils();
                                } else if (SUBMIT_COUNTER == 6) {
                                    if (!networkString.isEmpty() != (networkSpinner.getSelectedItemPosition() == 0)) {
                                        if (preOrPostString != null) {
                                            if (threeOrFourGString != null || threeOrFourGString == null) {
                                                if (amountString.isEmpty() || !amountString.isEmpty()) {
                                                    if (!planString.isEmpty() != (plansSpinner.getSelectedItemPosition() == 0)) {
                                                        if (!commentString.isEmpty()) {
                                                            if (!dateString.isEmpty()) {
                                                                if (!timeString.isEmpty()) {
                                                                    if (!customerNameString.isEmpty()) {
                                                                        if (!customerMobileNumberString.isEmpty()) {
                                                                            if (!alternativeNumberString.isEmpty() && alternativeNumberString.length() == 10) {
                                                                                if (!flatNoString.isEmpty()) {
                                                                                    if (!streetNoString.isEmpty()) {
                                                                                        if (!landMarkString.isEmpty()) {
                                                                                            if (!cityNameString.isEmpty()) {
                                                                                                if (!pincodeString.isEmpty() && pincodeString.length() == 6) {
                                                                                                    if (!emailId.isEmpty()) {
                                                                                                        postSubmit(emailId, callNumber, preOrPostString, threeOrFourGString, amountString, planString, commentString, dateString, timeString, customerNameString, customerMobileNumberString, alternativeNumberString, flatNoString, streetNoString, landMarkString, cityNameString, pincodeString, networkString, manualCallStatus, userSpecificComment);
                                                                                                        resetAllDeatils();
                                                                                                    } else {
                                                                                                        Toast.makeText(Home.this, "Enter Pincode", Toast.LENGTH_SHORT).show();
                                                                                                    }
                                                                                                } else {
                                                                                                    Toast.makeText(Home.this, "Enter Pincode", Toast.LENGTH_SHORT).show();
                                                                                                }
                                                                                            } else {
                                                                                                Toast.makeText(Home.this, "Enter City", Toast.LENGTH_SHORT).show();
                                                                                            }
                                                                                        } else {
                                                                                            Toast.makeText(Home.this, "Enter Landmark", Toast.LENGTH_SHORT).show();
                                                                                        }
                                                                                    } else {
                                                                                        Toast.makeText(Home.this, "Enter Street Name", Toast.LENGTH_SHORT).show();
                                                                                    }
                                                                                } else {
                                                                                    Toast.makeText(Home.this, "Enter Flat No.", Toast.LENGTH_SHORT).show();
                                                                                }
                                                                            } else {
                                                                                Toast.makeText(Home.this, "Enter Alter Customer Contact", Toast.LENGTH_SHORT).show();
                                                                            }
                                                                        } else {
                                                                            Toast.makeText(Home.this, "Enter Customer Contact", Toast.LENGTH_SHORT).show();
                                                                        }
                                                                    } else {
                                                                        Toast.makeText(Home.this, "Enter Customer Name", Toast.LENGTH_SHORT).show();
                                                                    }
                                                                } else {
                                                                    Toast.makeText(Home.this, "Select Time", Toast.LENGTH_SHORT).show();
                                                                }
                                                            } else {
                                                                Toast.makeText(Home.this, "Select Date", Toast.LENGTH_SHORT).show();
                                                            }
                                                        } else {
                                                            Toast.makeText(Home.this, "Select Comment", Toast.LENGTH_SHORT).show();
                                                        }
                                                    } else {
                                                        Toast.makeText(Home.this, "Select Plan", Toast.LENGTH_SHORT).show();
                                                    }
                                                } else {
                                                    Toast.makeText(Home.this, "Enter amount", Toast.LENGTH_SHORT).show();
                                                }
                                            } else {
                                                Toast.makeText(Home.this, "Select 3g Or 4G amount", Toast.LENGTH_SHORT).show();
                                            }
                                        } else {
                                            Toast.makeText(Home.this, "Select PrePaid Or PostPaid", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        Toast.makeText(Home.this, "Select Network", Toast.LENGTH_SHORT).show();
                                    }

                                } else if (SUBMIT_COUNTER == 7)
                                {
                                            resetAllDeatils();
                                            postSubmit(emailId, callNumber, prepaidOrPostpaid, threeOrFourG, amountString, planString, commentString, dateString, timeString, customerNameString, customerMobileNumberString, alternativeNumberString, flatNoString, streetNoString, landMarkString, cityNameString, pincodeString, networkString, manualCallStatus, userSpecificComment);
                                }
                                else if (SUBMIT_COUNTER == 8) {

                                    if (!userSpecificComment.contentEquals("")) {
                                        if (!dateString.isEmpty()) {
                                            if (!timeString.isEmpty()) {
                                                    resetAllDeatils();
                                                    postSubmit(emailId, callNumber, prepaidOrPostpaid, threeOrFourG, amountString, planString, commentString, dateString, timeString, customerNameString, customerMobileNumberString, alternativeNumberString, flatNoString, streetNoString, landMarkString, cityNameString, pincodeString, networkString, manualCallStatus, userSpecificComment);
                                            } else {
                                                Toast.makeText(Home.this, "Select Time", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                        else {
                                            Toast.makeText(Home.this, "Select Date", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                    else {
                                        Toast.makeText(Home.this, "Please fill comment box", Toast.LENGTH_SHORT).show();
                                    }
                                } else if (SUBMIT_COUNTER == 9) {
                                    customerNameString = customerName.getText().toString().trim();
                                    if (!userSpecificComment.contentEquals("")) {
                                        if (!customerNameString.contentEquals("")) {
                                            resetAllDeatils();
                                            postSubmit(emailId, callNumber, prepaidOrPostpaid, threeOrFourG, amountString, planString, commentString, dateString, timeString, customerNameString, customerMobileNumberString, alternativeNumberString, flatNoString, streetNoString, landMarkString, cityNameString, pincodeString, networkString, manualCallStatus, userSpecificComment);
                                        } else {
                                            Toast.makeText(Home.this, "Please Enter Name", Toast.LENGTH_SHORT).show();
                                        }
                                    } else {
                                        Toast.makeText(Home.this, "Please fill comment box", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                }
                            }else {
                                Toast.makeText(Home.this,"No valid number found!",Toast.LENGTH_SHORT).show();
                            }
                            return;
                        }
                        else
                        {
                            builderSubmit=new AlertDialog.Builder(Home.this);
                            final AlertDialog alertDialog=builderSubmit.create();
                            builderSubmit.setTitle("Internet Problem");
                            builderSubmit.setMessage("Please Connect Internet Then Only Login Should Will Happen");
                            builderSubmit.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialogInterface,int id)
                                {
                                    alertDialog.dismiss();
                                }
                            });
                            builderSubmit.show();
                        }
                    }
                };

                registerReceiver(broadcastReceiver,intentFilter);

                break;

            case R.id.emp_logout:
                IntentFilter intentFilter1=new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE");
                broadcastReceiver=new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        int[] type={ConnectivityManager.TYPE_MOBILE,ConnectivityManager.TYPE_WIFI};

                        if (CheckInternetConnection.isNetworkState(context,type) == true)
                        {
                            String empId,empLeadsString,currentDateString,averageCallSec,callString,loginTimeString,logoutTimeString,leadString;
                            empId=employeeIdHeader.getText().toString();
                            empLeadsString=empLeads.getText().toString();
                            currentDateString=getDateTime();
                            String logoutTime = currentTimeFunction();
                            logoutTimeString=logoutTime;
                            leadString= empLeads.getText().toString();
                            averageCallSec=avgCalDur.getText().toString();
                            callString=noOfCallsMade.getText().toString();
                            timerEditor=timeShare.edit();
                            timerEditor.putString(logoutKey,logoutTimeString);
                            timerEditor.commit();

                            String loginFinalString,logoutFinalString,teaBreakString=null,launchBreakString=null;
                            teaBreakString=timeShare.getString(finalTeaBreakKey,"");
                            launchBreakString=timeShare.getString(finalLunchBreakKey,"");
                            loginFinalString=timeShare.getString(loginKey,"");
                            logoutFinalString=timeShare.getString(logoutKey,"");

                            Log.d("Submit",""+empId +"\n"+loginFinalString+"\n"+logoutFinalString+"\n"+currentDateString+"\n"+teaBreakString+"\n"+teaBreakString+"\n"+callString+"\n"+leadString+"\n"+averageCallSec);
                            employeeStatusFunction(empId,loginFinalString,logoutFinalString,currentDateString,teaBreakString,launchBreakString,callString,leadString,averageCallSec);
                            return;
                        }
                        else
                        {
                            builderEmp=new AlertDialog.Builder(Home.this);
                            final AlertDialog alertDialog=builderEmp.create();
                            builderEmp.setTitle("Internet Problem");
                            builderEmp.setMessage("Please Connect Internet Then Only Login Should Will Happen");
                            builderEmp.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialogInterface,int id)
                                {
                                    alertDialog.dismiss();
                                }
                            });
                            builderEmp.show();
                        }
                    }
                };

                registerReceiver(broadcastReceiver,intentFilter1);
                break;
        }

    }

    private void showManualCallDialog() {

        Intent intent=new Intent(Intent.ACTION_DIAL);
        startActivity(intent);
        mainHeadNo.setText("Manula Call");
        customerMobileNumber.setText("");
        currentCallData.setId("");
        currentCallData.setNumber("");
        currentCallData.setManualCallFlag(true);
       /* AlertDialog b= null;
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

       *//* LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.manual_call_layout, null);
       *//*

        LayoutInflater li = LayoutInflater.from(Home.this);
        View dialogView = li.inflate(R.layout.manual_call_layout, null);

        final EditText edt = (EditText) dialogView.findViewById(R.id.call_num_edt);
        dialogBuilder.setTitle("Manual Call");
        dialogBuilder.setPositiveButton("Call", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //do something with edt.getText().toString();
                String number = edt.getText().toString();
                if (!number.contentEquals("")){
                    dialog.dismiss();
                    mainHeadNo.setText(number);
                    customerMobileNumber.setText(number);
                    currentCallData.setId("");
                    currentCallData.setNumber(number);
                    currentCallData.setManualCallFlag(true);
                    initiateCall("tel:"+number);
                }else {
                    Toast.makeText(Home.this,"PLEASE ENTER NUMBER",Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialogBuilder.setView(dialogView);
        dialogBuilder.setCancelable(false);
        b = dialogBuilder.create();
        b.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        b.show();*/
    }

    private void checkForCallComment(String numberFlag) {
        ContentValues values = new ContentValues();
        values.put(SqlDataBase.NUMBER_FLAG, numberFlag);
        if (sqlDataBase.updateNumberFlag(values, currentCallData.getId()) != 0) {
            //Toast.makeText(Home.this,"number added to retry",Toast.LENGTH_SHORT).show();
            sqlDataBase.testValues();
        }
    }

    private void cutCurrentCall()
     {
        Class c = null;
        try {
            c = Class.forName(telephonicManager.getClass().getName());
            Method m = c.getDeclaredMethod("getITelephony");
            m.setAccessible(true);
            ITelephony telephonyService = (ITelephony) m.invoke(telephonicManager);
            telephonyService.endCall();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
     }


    @Override
    protected void onResume() {
        super.onResume();

        Calendar c = Calendar.getInstance();
        String s = currentTimeFunction();
        timerEditor.putString(loginKey,s);
        timerEditor.commit();

        if (CHRONOMETER == false)
        {
            chronoMeter();
            CHRONOMETER =true;
        }
        else
        {
            if (SNACKS_TEA_STATUS !=true)
            {
                stopWatch.setBase(SystemClock.elapsedRealtime() + timeWhenStopped);
                stopWatch.start();
            }
            else
            {
                if (SNACKS_TEA_STATUS !=true || LAUNCH_STATUS !=true)
                {
                    stopWatch.setBase(SystemClock.elapsedRealtime() + timeWhenStopped);
                    stopWatch.start();
                }
            }
        }

        if(currentCallData.isManualCallFlag()){
            customerMobileNumber.setEnabled(true);
            customerMobileNumber.setFocusableInTouchMode(true);
            customerMobileNumber.setFocusable(true);
            //customerMobileNumber.setKeyListener(null);
            Toast.makeText(Home.this,"enabled!",Toast.LENGTH_SHORT).show();
        }else {
            customerMobileNumber.setEnabled(false);
            customerMobileNumber.setFocusable(false);
            Toast.makeText(Home.this,"not enabled!",Toast.LENGTH_SHORT).show();
        }

        hindiList.clear();
        allList.clear();
        hindiList.add("Select");
        allList.add("Select");

        apiServices.getUsersForCallTransfer(emailIdShared).enqueue(new Callback<UsersForCTResponse>() {
            @Override
            public void onResponse(Call<UsersForCTResponse> call, Response<UsersForCTResponse> response) {
                if(response.isSuccessful()){
                    UsersForCTResponse finalResponseData = response.body();
                    if (finalResponseData.getStatusMessage().contentEquals("Success")){
                        dataResponseList = finalResponseData.getUserArrayData();
                        if(dataResponseList.size() != 0){
                            for (UserDataForCallT individualData: dataResponseList){
                                Log.d("lang_",""+individualData.getUserLang());
                                if(individualData.getUserLang().contentEquals("Hindi")){
                                    hindiList.add(individualData.getUserName());
                                }else {
                                    allList.add(individualData.getUserName());
                                }
                            }
                        }else {
                            Log.d("data_is_","null");
                        }
                    }else {
                        Log.d("response_is_","null");
                    }

                }else {
                    Log.d("tst_response_","null");
                }
            }

            @Override
            public void onFailure(Call<UsersForCTResponse> call, Throwable t) {
                //Toast.makeText(Home.this,"Fail!",Toast.LENGTH_SHORT).show();
            }
        });



    }

    @Override
    protected void onPause()
    {
        super.onPause();
        if (SNACKS_TEA_STATUS == false|| LAUNCH_STATUS == false )
        {
            timeWhenStopped= stopWatch.getBase() - SystemClock.elapsedRealtime();
            stopWatch.stop();
        }

        editor = sharedPreferences.edit();
        editor.putString("LEAD_COUNT",""+empLeads.getText().toString().trim());
        editor.putString("NO_OF_CALLS",""+noOfCallsMade.getText().toString().trim());
        editor.putString("AVG_TIME_PER_CALL",""+avgCalDur.getText().toString().trim());
        editor.putString("TEA_BREAK",timeShare.getString(finalTeaBreakKey,""));
        editor.putString("CRONO_METER", String.valueOf(time));
        editor.commit();

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        status = false;
        FIRST_TIME_COUNTER = 0;
        Log.d("restart_","fired");
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        telephonicManager.listen(phoneCallListener, PhoneStateListener.LISTEN_NONE);
        this.unregisterReceiver(phoneStateRecever);
        stopService(checkForCT);
        unregisterReceiver(receiver);
    }


    private void makeACall()
    {

        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(Home.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(Home.this,
                    Manifest.permission.CALL_PHONE)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(Home.this, new String[]{Manifest.permission.CALL_PHONE}, MY_PERMISSIONS_REQUEST_MAKE_CALLS);
                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }else {

            if(currentCallData != null){
                NEW_CALL_STATUS=true;
                String currentNumber = currentCallData.getNumber();
                if (currentNumber != null){
                    increOrDecreCallCounter(0);
                    String newCallNum = "tel:"+currentNumber;
                    //Toast.makeText(Home.this,""+newCallNum,Toast.LENGTH_LONG).show();
                    initiateCall(newCallNum);
                }else
                {
                    Toast.makeText(Home.this,"No more numbers found!",Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(Home.this,"No more numbers found!",Toast.LENGTH_SHORT).show();
            }

        }
    }

    private String getPhoneNumber() {

        //Log.d("call_data: id",currentCallData.getId()+" cal_number"+currentCallData.getNumber());
        if(currentCallData != null){

            if(commentString.equals("Network Problem")){
                checkForCallComment("2");
            }else if(commentString.equals("Not Reachable")) {
                checkForCallComment("2");
            }else {
                checkForCallComment("1");
            }

            currentCallData = sqlDataBase.getPhoneNumberFromDB();
            if(currentCallData != null){
                mainHeadNo.setText(currentCallData.getNumber());
                if(currentCallData.isForwardedCall())
                {
                    fwdCall.setVisibility(View.VISIBLE);
                    fwdCall.startAnimation(animation);
                }
                else
                {

                }
                return currentCallData.getNumber();
            }
        }else {
            Log.d("number_is_","null");
        }
        return null;
    }


    private void getCurrentPhoneNumberData() {

        currentCallData = sqlDataBase.getPhoneNumberFromDB();
        if (currentCallData != null){
            mainHeadNo.setText(currentCallData.getNumber());
            Log.d("call_data: id",currentCallData.getId()+" cal_number"+currentCallData.getNumber());
        }else {
            Toast.makeText(Home.this,"No data found!",Toast.LENGTH_SHORT).show();
        }
      /*  if(currentCallData != null){
            ContentValues values = new ContentValues();
            values.put(SqlDataBase.NUMBER_FLAG,"1");
            if (sqlDataBase.updateNumberFlag(values,currentCallData.getId()) != 0){
                sqlDataBase.testValues();
            }
        }else {
            Log.d("number_is_","null");
        }*/

    }


    private void initiateCall(String newCallNum) {

        Intent callingIntenet = new Intent(Intent.ACTION_CALL);
        callingIntenet.setData(Uri.parse(newCallNum));
        callingIntenet.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        if (ActivityCompat.checkSelfPermission(Home.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        startActivity(callingIntenet);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_MAKE_CALLS: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    if (currentCallData != null){
                        String newCallNum = "tel:"+currentCallData.getNumber();
                        initiateCall(newCallNum);
                    }else {
                        Toast.makeText(Home.this,"No data found!",Toast.LENGTH_SHORT).show();
                    }

                } else {}
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    private void giveRestAndInitialteCall(long millisInFuture,long countDownInterval) {
        CountDownTimer timer;
        isPaused = false;
        isCanceled = false;
       /* Handler restHandler = new Handler();
        Runnable callInitiater = new Runnable() {
            @Override
            public void run() {
                showConfirmationBoxNdProceed();
            }
        };
        restHandler.postDelayed(callInitiater,20000);
*/
        fabCSrtBtn.setEnabled(false);
        timer = new CountDownTimer(millisInFuture,countDownInterval){
            public void onTick(long millisUntilFinished){
                if(isCanceled){
                    cancel();
                    fabCSrtBtn.setEnabled(true);
                    Toast.makeText(Home.this,"Timer Cancelled!",Toast.LENGTH_SHORT).show();
                }else {}
            }
            public void onFinish(){
                fabCSrtBtn.setEnabled(true);
                showConfirmationBoxNdProceed();
            }
        };
        timer.start();
        Toast.makeText(Home.this,"Timer Started!",Toast.LENGTH_SHORT).show();
    }


    private void showConfirmationBoxNdProceed() {
        Log.d("fired_","pop_up");
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }

        increOrDecreCallCounter(1);
        String nextNumber = getPhoneNumber();
        if (nextNumber != null){
            String newCallNum = "tel:"+nextNumber;
            customerMobileNumber.setText(currentCallData.getNumber());
            initiateCall(newCallNum);
        }else {
            Toast.makeText(Home.this,"No more call data found!",Toast.LENGTH_SHORT).show();
        }


/*            android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(Home.this);
            alertDialog.setTitle("Call Option");

            alertDialog.setNegativeButton("New Call", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int which) {
                    increOrDecreCallCounter(1);
                    dialogInterface.dismiss();
                    String nextNumber = getPhoneNumber();
                    if (nextNumber != null){
                        String newCallNum = "tel:"+nextNumber;
                        customerMobileNumber.setText(currentCallData.getNumber());
                        initiateCall(newCallNum);
                    }else {
                        Toast.makeText(Home.this,"No more call data found!",Toast.LENGTH_SHORT).show();
                    }
                    //initiateCall(newCallNum,dialogInterface);
                }
            });

            callIniaterDialog = alertDialog.create();
            callIniaterDialog.show();*/
    }


    private void increOrDecreCallCounter(int incrOrDecreCounter) {
        int presentCounter = Integer.parseInt(noOfCallsMade.getText().toString().trim());
        //int presentCounter = getNumberOfCalls();

        if (incrOrDecreCounter == 1){
            presentCounter += 1;
            noOfCallsMade.setText(String.valueOf(presentCounter));
            calCulateAvgCallInSec(presentCounter);
        }else {

            calCulateAvgCallInSec(presentCounter);
            /*if(presentCounter != 0){
                presentCounter -= 1;

            }else {}*/
        }
    }

    private void calCulateAvgCallInSec(int presentCounter) {
        long actualSeconds = avgCallInSec/1000;
        if(presentCounter != 0 && actualSeconds != 0){
            avgCalDur.setText(String.valueOf((actualSeconds/presentCounter)+" sec"));
        }
    }

    private void clearGlobalVariables() {

        start_time_in_millsec = 0;
        end_time_in_millsec = 0;
        hours = 0;
        minutes = 0;
        seconds = 0;

    }

    public void postSubmit(final String email, final String callNumber,final String preOrPost, final String threeOrFourG, final String averageSpendMnth, final String planSold, final String comments, final String date, final String time, final String customerName, final String customerNumber, final String alternativeNumber, final String flatNo, final String streetNo, final String landMark, final String cityName, final String pincode,final String network,final String manualCallStatus,final String userSpecificComment)
    {

        final ProgressDialog loginDialog;
        loginDialog = DialogUtils.showProgressDialog(this, "Loading Please Wait.....");

            apiServices.submitData(email, callNumber,preOrPost, threeOrFourG, averageSpendMnth, planSold, comments, date, time, customerName, customerNumber, alternativeNumber, flatNo, streetNo, landMark, cityName, pincode,network,manualCallStatus,userSpecificComment).enqueue(new Callback<SubmitData>() {
                @Override
                public void onResponse(Call<SubmitData> call, Response<SubmitData> response) {
                    if (response.isSuccessful())
                    {
                        loginDialog.dismiss();
                        SubmitData responseData = response.body();
                        Toast.makeText(getApplicationContext(),""+responseData.getStatusMessage(),Toast.LENGTH_LONG).show();
                        empLeads.setText(responseData.getLeadCount());
                        customerMobileNumber.setEnabled(false);
                        currentCallData.setManualCallFlag(false);
                        giveRestAndInitialteCall(10000,1000);
                        hideCommentBox();
                    }
                    else
                    {
                        try {

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

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }


                @Override
                public void onFailure(Call<SubmitData> call, Throwable t)
                {
                    loginDialog.dismiss();
                    Log.d(TAG," "+t.getMessage());
                }
            });
    }

    public void callTransferFunction(String emailId,String number)
    {
        final ProgressDialog loginDialog;
        loginDialog = DialogUtils.showProgressDialog(this, "Loading Please wait.....");
        apiServices.callTransferData(emailId,number).enqueue(new Callback<CallTransferData>() {
            @Override
            public void onResponse(Call<CallTransferData> call, Response<CallTransferData> response) {

                if (response.isSuccessful())
                {
                    loginDialog.dismiss();
                    String s=response.body().getStatusMessage();
                    Toast.makeText(getApplicationContext(),"Call Forward "+s,Toast.LENGTH_LONG).show();
                    resetAllDeatils();
                    giveRestAndInitialteCall(10000,1000);
                }
                else
                {

                    try {
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

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

            }

            @Override
            public void onFailure(Call<CallTransferData> call, Throwable t) {
                loginDialog.dismiss();
                Log.d(TAG," "+t.getMessage());
            }
        });
    }

    public void employeeStatusFunction(String emailId,String loginTime,String logoutTime,String currentDate,String teaBreak,String launchBreak,String call,String leads,String averageCallTime)
    {
        final ProgressDialog loginDialog;
        loginDialog = DialogUtils.showProgressDialog(this, "Loading Pleasewait.....");
        apiServices.employeeStatusData(emailId,loginTime,logoutTime,currentDate,teaBreak,launchBreak,call,leads,averageCallTime).enqueue(new Callback<EmployeeStatusData>() {
            @Override
            public void onResponse(Call<EmployeeStatusData> call, Response<EmployeeStatusData> response)
            {
                if (response.isSuccessful())
                {
                    loginDialog.dismiss();
                    editor = sharedPreferences.edit();
                    editor.putString("LOGIN_STATUS","0");
                    editor.commit();
                    String s=response.body().getStatusMessage();
                    clearStatusReportFields();
                    Toast.makeText(getApplicationContext(),""+s,Toast.LENGTH_LONG).show();
                    finish();
                    //System.exit(0);
                }

                else{

                try {
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

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            }

            private void clearStatusReportFields() {
                empLeads.setText("0");
                noOfCallsMade.setText("0");
                avgCalDur.setText("0");
            }

            @Override
            public void onFailure(Call<EmployeeStatusData> call, Throwable t) {
                loginDialog.dismiss();
                Log.d(TAG,"  "+t.getMessage());
            }
        });
    }

    public String currentTimeFunction()
    {
        String loginTimeString;
        Date loginTime=new Date();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("hh:mm a");
        loginTimeString=simpleDateFormat.format(loginTime);
        return loginTimeString;
    }

    public String breakTime()
    {
        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("hh:mm:ss");
        String s=simpleDateFormat.format(calendar.getTime());
        return s;
    }

    /*public void addCallNumbers()
    {
        String numbers[]={"9791950538","9182434087","8790006364"};
        SqlDataBase sqlDataBase=new SqlDataBase(this);
        for (int i=0;i<numbers.length;i++)
        {
            sqlDataBase.addNumbers(new SqlitePojo(numbers[i]));
        }
    }
*/
    public void allContactList()
    {
        SqlDataBase sqlDataBase=new SqlDataBase(this);
        List<SqlitePojo> sqlitePojos=sqlDataBase.getAllContactNumber();

        for (SqlitePojo sqlitePojo:sqlitePojos)
        {
            Log.d("All_Contact_List","===>"+sqlitePojo.getNumber());
        }
    }



    public void resetAllDeatils()
    {
        countDownTimer1.cancel();
        relaxationTimerTextView.clearAnimation();
        fwdCall.clearAnimation();
        fwdCall.setVisibility(View.GONE);
        relaxationTimerTextView.setText(" Relaxation Time");
        customerName.getText().clear();
        customerMobileNumber.getText().clear();
        mainHeadNo.setText(null);
        mainHeadNo.setHint("Customer Number");
        alternativeNumber.getText().clear();
        flatNo.getText().clear();
        streetNo.getText().clear();
        landMark.getText().clear();
        cityName.getText().clear();
        pincode.getText().clear();
        timeTv.setText(null);
        timeTv.setHint("Time");
        timeLayout.setBackgroundColor(getResources().getColor(R.color.light_blue));
        dateTv.setText(null);
        dateTv.setHint("Date");
        dateLayout.setBackgroundColor(getResources().getColor(R.color.light_blue));
        networkSpinner.setSelection(0);
        commentSpinner.setSelection(0);
        plansSpinner.setSelection(0);
        prePaidLayout.setBackgroundColor(getResources().getColor(R.color.light_blue));
        postPaidLayout.setBackgroundColor(getResources().getColor(R.color.light_blue));
        threeGLayout.setBackgroundColor(getResources().getColor(R.color.light_blue));
        fourGLayout.setBackgroundColor(getResources().getColor(R.color.light_blue));
        amountText.getText().clear();
        amountLayout.setBackgroundColor(getResources().getColor(R.color.light_blue));
        prepaidOrPostpaid=null;
        threeOrFourG=null;
    }


    private void updateRelaxationTimer()
    {
        int seconds=(int) relaxationTimeLeft1 % 60000 /1000;
        String timeLeft;
        timeLeft = ""+ seconds;
        if (seconds < 0)
            timeLeft += seconds;
        relaxationTimerTextView.setText(timeLeft);
        Log.d("TimerChecking",""+timeLeft);
    }
}
