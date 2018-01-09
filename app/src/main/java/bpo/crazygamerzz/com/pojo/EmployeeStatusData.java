package bpo.crazygamerzz.com.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Kumar on 12/12/2017.
 */

public class EmployeeStatusData
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

    public EmployeeStatusData(String statusCode, String statusMessage, String successMessage) {
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
        this.successMessage = successMessage;
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
}
