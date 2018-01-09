package bpo.crazygamerzz.com.pojo;

import com.google.gson.annotations.SerializedName;

/**
 * Created by kishore on 22/11/17.
 */

public class DefaultTronXError {
    @SerializedName("statusMessage")
    public String status;
    @SerializedName("errorMessage")
    public String error;

    public String getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }
}
