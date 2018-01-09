package bpo.crazygamerzz.com.networkapi;

import java.util.ArrayList;

import bpo.crazygamerzz.com.pojo.CallForwardData;
import bpo.crazygamerzz.com.pojo.CallRemainderData;
import bpo.crazygamerzz.com.pojo.CallTransferData;
import bpo.crazygamerzz.com.pojo.EmployeeStatusData;
import bpo.crazygamerzz.com.pojo.LoginData;
import bpo.crazygamerzz.com.pojo.SubmitData;
import bpo.crazygamerzz.com.pojo.UpdateCallBackResponse;
import bpo.crazygamerzz.com.pojo.UserDataForCallT;
import bpo.crazygamerzz.com.pojo.UsersForCTResponse;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Cheemala on 12/9/2017.
 */

public interface APIServices {

    @POST("chkLogin")
    @FormUrlEncoded
    Call<LoginData> checkLogin(@Field("USER_EMAIL") String title, @Field("USER_PASSWORD") String body);

    @POST("submitForm")
    @FormUrlEncoded
    Call<SubmitData> submitData(@Field("USER_EMAIL") String network,
                                @Field("CALL_NUMBER") String callNumber,
                                @Field("NETWORK_TYPE") String preOrPost,
                                @Field("NETWORK_GENERATION") String threeOrFourG,
                                @Field("AVG_MONEY_SPENT") String AvgSpendMonth,
                                @Field("PLAN_SELECTION") String plansHold,
                                @Field("COMMENT") String comment,
                                @Field("FOLLOW_UP_DATE") String date,
                                @Field("FOLLOW_UP_TIME") String time,
                                @Field("CUSTOMER_NAME") String customerName,
                                @Field("CUSTOMER_NUMBER") String customerMobileNumber,
                                @Field("CUSTOMER_ALT_NUMBER") String alternativeNumber,
                                @Field("ADRS_FLAT_NO") String flatNo,
                                @Field("ADRS_STREET_NAME") String streetName,
                                @Field("ADRS_LLD_MARK") String landMark,
                                @Field("ADRS_CITY") String cityName,
                                @Field("ADRS_PINCODE") String pincode,
                                @Field("NETWORK_NAME") String networkName,
                                @Field("MANUAL_CALL_STATUS") String manualCallStatus,
                                @Field("CALL_BACK_COMMENT") String userComment);

    @POST("callForward")
    @FormUrlEncoded
    Call<CallTransferData>  callTransferData(@Field("USER_EMAIL") String emailId,@Field("FORWARDED_NUMBER") String number);

    @POST("updateStatusReport")
    @FormUrlEncoded
    Call<EmployeeStatusData> employeeStatusData(@Field("USER_EMAIL") String emailId,
                                                @Field("USER_LOGIN_TIME") String loginTime,
                                                @Field("USER_LOGOUT_TIME") String logoutTime,
                                                @Field("DATE") String currentDate,
                                                @Field("TEA_BREAK") String teaBreak,
                                                @Field("LUNCH_BREAK") String launchBreak,
                                                @Field("NO_OF_CALLS") String call,
                                                @Field("NO_OF_LEADS") String leads,
                                                @Field("AVG_TIME_PER_CALL") String averageCallTime);

    @GET("checkForFCall")
    Call<CallForwardData>  checkForCallTransfer(@Query("EMAIL") String emailId);

    @GET("checkForCallRemainder")
    Call<CallRemainderData>  checkForCallRemainder(@Query("USER_EMAIL") String emailId, @Query("CALL_BACK_DATE") String callBackDate, @Query("CALL_BACK_TIME") String callBackTime);

    @GET("getUsersForCallTransfer")
    Call<UsersForCTResponse>  getUsersForCallTransfer(@Query("USER_EMAIL") String emailId);

    @POST("updateCallRemainderData")
    @FormUrlEncoded
    Call<UpdateCallBackResponse>  updateCallRemainder(@Field("ID") String emailId, @Field("CALL_BACK_DATE") String date, @Field("CALL_BACK_TIME") String time);

}
