package bpo.crazygamerzz.com.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by code2 on 12/28/2017.
 */

public class CallRemainderData {
    @SerializedName("Id")
    @Expose
    public String Id;

    @SerializedName("statusMessage")
    @Expose
    public String statusMessage;

    @SerializedName("userEmail")
    @Expose
    public String userEmail;

    @SerializedName("userName")
    @Expose
    public String userName;

    @SerializedName("userContactNumber")
    @Expose
    public String userContactNumber;

    @SerializedName("userCalBackComment")
    @Expose
    public String userCalBackComment;

    public CallRemainderData(String Id,String statusMessage,String userEmail,String userName,String userContactNumber,String userCalBackComment){
        this.Id = Id;
        this.statusMessage = statusMessage;
        this.userEmail = userEmail;
        this.userName = userName;
        this.userContactNumber = userContactNumber;
        this.userCalBackComment = userCalBackComment;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserContactNumber() {
        return userContactNumber;
    }

    public void setUserContactNumber(String userContactNumber) {
        this.userContactNumber = userContactNumber;
    }

    public String getUserCalBackComment() {
        return userCalBackComment;
    }

    public void setUserCalBackComment(String userCalBackComment) {
        this.userCalBackComment = userCalBackComment;
    }

}
