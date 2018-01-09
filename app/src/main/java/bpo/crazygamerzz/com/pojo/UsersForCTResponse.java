package bpo.crazygamerzz.com.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by code2 on 12/21/2017.
 */

public class UsersForCTResponse
{

    @SerializedName("statusMessage")
    @Expose
    public String statusMessage;

    @SerializedName("userData")
    @Expose
    public ArrayList<UserDataForCallT> userArrayData;


    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public ArrayList<UserDataForCallT> getUserArrayData() {
        return userArrayData;
    }

    public void setUserArrayData(ArrayList<UserDataForCallT> userArrayData) {
        this.userArrayData = userArrayData;
    }
}
