package bpo.crazygamerzz.com.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Kumar on 12/18/2017.
 */

public class NumberData {

    @SerializedName("ID")
    @Expose
    public String phnId;

    @SerializedName("PHONE_NUMBER")
    @Expose
    public String number;


    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPhnId() {
        return phnId;
    }

    public void setPhnId(String phnId) {
        this.phnId = phnId;
    }
}
