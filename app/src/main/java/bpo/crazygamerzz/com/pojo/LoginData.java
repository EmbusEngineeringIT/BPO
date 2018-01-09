package bpo.crazygamerzz.com.pojo;

import android.util.Log;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by Cheemala on 12/9/2017.
 */

public class LoginData {

    @SerializedName("ID")
    @Expose
    public String userId;

    @SerializedName("EMAIL")
    @Expose
    public String email;

    @SerializedName("USER_NAME")
    @Expose
    public String userName;

    @SerializedName("statuscode")
    @Expose
    public String statusCode;

    @SerializedName("statusMessage")
    @Expose
    public String statusMessage;

    @SerializedName("successMessage")
    @Expose
    public String successMessage;

    @SerializedName("phone_num_dump")
    @Expose
    public ArrayList<NumberData> numberDump;


    public LoginData(String userId, String email, String userName, String statusCode, String statusMessage,ArrayList<NumberData> numberDump) {
        this.userId = userId;
        this.email = email;
        this.userName = userName;
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
        this.numberDump = numberDump;
    }


    public LoginData(String statusCode,String statusMessage,String successMessage){
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
        this.successMessage = successMessage;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public ArrayList<NumberData> getNumberDump() {
        return numberDump;
    }

    public void setNumberDump(ArrayList<NumberData> numberDump)
    {
        this.numberDump = numberDump;
    }

     public void displayDump()
    {
        for(NumberData data: numberDump)
        {
            Log.d("Data: ",""+data.getPhnId());
            Log.d("Data: ",""+data.getNumber());
        }
    }

}
