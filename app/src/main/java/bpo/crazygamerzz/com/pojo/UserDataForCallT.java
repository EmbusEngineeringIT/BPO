package bpo.crazygamerzz.com.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by code2 on 12/21/2017.
 */

public class UserDataForCallT {

    @SerializedName("Id")
    @Expose
    public String Id;

    @SerializedName("userName")
    @Expose
    public String userName;

    @SerializedName("userEmail")
    @Expose
    public String userEmail;

    @SerializedName("userLang")
    @Expose
    public String userLang;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserLang() {
        return userLang;
    }

    public void setUserLang(String userLang) {
        this.userLang = userLang;
    }

}
