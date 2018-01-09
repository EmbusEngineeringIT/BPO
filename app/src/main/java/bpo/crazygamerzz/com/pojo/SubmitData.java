package bpo.crazygamerzz.com.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Kumar on 12/12/2017.
 */

public class SubmitData
{
    @SerializedName("USER_NAME")
    @Expose
    private String email;

    @SerializedName("CALL_NUMBER")
    @Expose
    private String callNumber;

    @SerializedName("NETWORK_TYPE")
    @Expose
    private String preOrPost;

    @SerializedName("NETWORK_GENERATION")
    @Expose
    private String threeOrFourG;

    @SerializedName("AVG_MONEY_SPENT")
    @Expose
    private String AvgSpendMonth;

    @SerializedName("PLAN_SELECTION")
    @Expose
    private String plansHold;

    @SerializedName("COMMENT")
    @Expose
    private String comments;

    @SerializedName("FOLLOW_UP_DATE")
    @Expose
    private String date;

    @SerializedName("FOLLOW_UP_TIME")
    @Expose
    private String time;

    @SerializedName("CUSTOMER_NAME")
    @Expose
    private String customerName;

    @SerializedName("CUSTOMER_NUMBER")
    @Expose
    private String customerMobileNumber;

    @SerializedName("CUSTOMER_ALT_NUMBER")
    @Expose
    private String alternativeNumber;

    @SerializedName("ADRS_FLAT_NO")
    @Expose
    private String flatNo;

    @SerializedName("ADRS_STREET_NAME")
    private String streetNo;

    @SerializedName("ADRS_LLD_MARK")
    @Expose
    private String landMark;

    @SerializedName("ADRS_CITY")
    @Expose
    private String cityName;

    @SerializedName("ADRS_PINCODE")
    @Expose
    public String pincode;

    @SerializedName("statuscode")
    @Expose
    public String statusCode;

    @SerializedName("statusMessage")
    @Expose
    public String statusMessage;

    @SerializedName("successMessage")
    @Expose
    public String successMessage;

    @SerializedName("leadCount")
    @Expose
    public String leadCount;

    @SerializedName("NETWORKNAME")
    @Expose
    public String networkName;

    public SubmitData(String email, String statusCode, String statusMessage, String successMessage, String leadCount) {
        this.email = email;
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
        this.successMessage = successMessage;
        this.leadCount = leadCount;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCallNumber() {
        return callNumber;
    }

    public void setCallNumber(String callNumber) {
        this.callNumber = callNumber;
    }

    public String getPreOrPost() {
        return preOrPost;
    }

    public void setPreOrPost(String preOrPost) {
        this.preOrPost = preOrPost;
    }

    public String getThreeOrFourG() {
        return threeOrFourG;
    }

    public void setThreeOrFourG(String threeOrFourG) {
        this.threeOrFourG = threeOrFourG;
    }

    public String getAvgSpendMonth() {
        return AvgSpendMonth;
    }

    public void setAvgSpendMonth(String avgSpendMonth) {
        AvgSpendMonth = avgSpendMonth;
    }

    public String getPlansHold() {
        return plansHold;
    }

    public void setPlansHold(String plansHold) {
        this.plansHold = plansHold;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerMobileNumber() {
        return customerMobileNumber;
    }

    public void setCustomerMobileNumber(String customerMobileNumber) {
        this.customerMobileNumber = customerMobileNumber;
    }

    public String getAlternativeNumber() {
        return alternativeNumber;
    }

    public void setAlternativeNumber(String alternativeNumber) {
        this.alternativeNumber = alternativeNumber;
    }

    public String getFlatNo() {
        return flatNo;
    }

    public void setFlatNo(String flatNo) {
        this.flatNo = flatNo;
    }

    public String getStreetNo() {
        return streetNo;
    }

    public void setStreetNo(String streetNo) {
        this.streetNo = streetNo;
    }

    public String getLandMark() {
        return landMark;
    }

    public void setLandMark(String landMark) {
        this.landMark = landMark;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
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

    public String getLeadCount() {
        return leadCount;
    }

    public void setLeadCount(String leadCount) {
        this.leadCount = leadCount;
    }

    public String getNetworkName() {
        return networkName;
    }

    public void setNetworkName(String networkName) {
        this.networkName = networkName;
    }
}
