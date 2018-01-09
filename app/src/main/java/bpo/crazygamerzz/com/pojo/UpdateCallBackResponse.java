package bpo.crazygamerzz.com.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Cheemala on 12/11/2017.
 */

public class UpdateCallBackResponse {

    @SerializedName("statusCode")
    @Expose
    public String statusCode;

    @SerializedName("statusMessage")
    @Expose
    public String statusMessage;

    public UpdateCallBackResponse(String statusCode,String statusMessage){
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
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
}

