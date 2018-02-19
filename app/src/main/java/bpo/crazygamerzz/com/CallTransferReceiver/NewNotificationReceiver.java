package bpo.crazygamerzz.com.CallTransferReceiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;
import bpo.crazygamerzz.com.R;
import bpo.crazygamerzz.com.activities.Home;
import bpo.crazygamerzz.com.sqliteDataBase.SqlDataBase;

/**
 * Created by code2 on 12/22/2017.
 */

public class NewNotificationReceiver extends BroadcastReceiver {

    SqlDataBase dbHandler;
    NotificationCompat.Builder notificationBuilder;
    private NotificationManager notificationManager;
    Context context;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        dbHandler = new SqlDataBase(context);
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (intent.getAction().equals("com.bpo.newcallreceived")){

            Toast.makeText(context,"New Call Received",Toast.LENGTH_SHORT).show();
            String Id = intent.getExtras().getString("ID");
            String number = intent.getExtras().getString("FORWARDED_NUMBER");
            Toast.makeText(context,""+Id+" : "+number,Toast.LENGTH_SHORT).show();
            ContentValues values = new ContentValues();
            values.put(SqlDataBase.SERVER_NUMBER_ID,Id);
            values.put(SqlDataBase.CONTACT_NUMBER,number);
            values.put(SqlDataBase.NUMBER_FLAG,"3");
            addForwardedNumToDB(values,Id);
            showNotificationToNotifyUser("Forwarded Call",number+" Is added to your contact list");

        }

        if (intent.getAction().equals("com.bpo.newcall.remainder.received")){
            Toast.makeText(context,"New Call Remainder Received!",Toast.LENGTH_SHORT).show();
            String Id = intent.getExtras().getString("id");
            String userName = intent.getExtras().getString("user_name");
            String userNumber = intent.getExtras().getString("user_number");
            String userCalbckComment = intent.getExtras().getString("user_calbck_comment");
             //showNotificationToNotifyUser("Call Remainder","Would you like to call");
            sendCallBckReceivedMsg(Id,userName,userNumber,userCalbckComment);
        }

    }

    private void sendCallBckReceivedMsg(String id, String userName, String userNumber, String userCalbckComment) {

        Intent callBackRemainder = new Intent("bpo.crazygamerzz.com.action.calback.received");
        callBackRemainder.putExtra("ID",id);
        callBackRemainder.putExtra("USERNAME",userName);
        callBackRemainder.putExtra("USERNUMBER",userNumber);
        callBackRemainder.putExtra("USERCOMMENT",userCalbckComment);
        context.sendBroadcast(callBackRemainder);

    }

    private void showNotificationToNotifyUser(String notifTitle,String notifMsg) {

        notificationBuilder = new NotificationCompat.Builder(context);
        notificationBuilder.setSmallIcon(R.drawable.call_notification);
        notificationBuilder.setContentTitle(notifTitle);
        notificationBuilder.setContentText(notifMsg);
        notificationBuilder.setAutoCancel(true);
        sendNotificationText();
    }

    private void sendNotificationText() {
        Intent notificationIntent = new Intent(context, Home.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        notificationBuilder.setContentIntent(contentIntent);
        Notification notification = notificationBuilder.build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        notification.defaults |= Notification.DEFAULT_SOUND;
       /* currentNotificationID++;
        int notificationId = currentNotificationID;
        if (notificationId == Integer.MAX_VALUE - 1)
            notificationId = 0;*/
        notificationManager.notify(111, notification);
    }

    private void addForwardedNumToDB(ContentValues values,String serverId) {
        if (dbHandler.addCallForwardedNumber(values,serverId)){
            Toast.makeText(context,"Call added successfully!",Toast.LENGTH_SHORT).show();
            Log.d("tst_num_added_","to_db");
        }else {
            Toast.makeText(context,"Call adding failed!",Toast.LENGTH_SHORT).show();
            Log.d("tst_num_failed_added_","to_db");
        }
    }
}
