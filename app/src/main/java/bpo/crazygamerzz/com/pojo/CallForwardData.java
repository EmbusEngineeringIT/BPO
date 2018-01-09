package bpo.crazygamerzz.com.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by code2 on 12/21/2017.
 */

public class CallForwardData {

    @SerializedName("statusMessage")
    @Expose
    public String statusMessage;

    @SerializedName("Id")
    @Expose
    public String Id;

    @SerializedName("forwardedNumber")
    @Expose
    public String forwardedNumber;

    public CallForwardData(String statusMessage,String Id,String forwardedNumber){

        this.statusMessage = statusMessage;
        this.Id =Id;
        this.forwardedNumber = forwardedNumber;

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

    public void setForwardedNumber(String forwardedNumber) {
        this.forwardedNumber = forwardedNumber;
    }

    public String getForwardedNumber() {
        return forwardedNumber;
    }

}
