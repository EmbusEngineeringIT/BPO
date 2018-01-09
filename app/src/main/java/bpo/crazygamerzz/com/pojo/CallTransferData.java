package bpo.crazygamerzz.com.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Kumar on 12/12/2017.
 */

public class CallTransferData
{

    @SerializedName("statuscode")
    @Expose
    public String statusCode;

    @SerializedName("statusMessage")
    @Expose
    public String statusMessage;

    @SerializedName("successMessage")
    @Expose
    public String successMessage;

    private String emailId;
    private String mobileNumber;

    public CallTransferData(String statusCode, String statusMessage, String successMessage, String emailId, String mobileNumber) {
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
        this.successMessage = successMessage;
        this.emailId = emailId;
        this.mobileNumber = mobileNumber;
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

    public String getSuccessMessage() {
        return successMessage;
    }

    public void setSuccessMessage(String successMessage) {
        this.successMessage = successMessage;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
}
