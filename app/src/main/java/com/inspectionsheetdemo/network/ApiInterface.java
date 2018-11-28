package com.inspectionsheetdemo.network;



import java.util.HashMap;

import eu.thirdspaceauto.akka.hacksprint.Utils.Constants;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;


public interface ApiInterface {
    @FormUrlEncoded
    @POST(Constants.LOGIN)
    Call<ResponseBody> login(@Field("grant_type") String grant_type, @Field("client_id") String client_id,
                             @Field("username") String username, @Field("password") String password);

    @POST(Constants.REGISTER)
    Call<ResponseBody> register(@Body HashMap<String, String> userMap);

    Call<ResponseBody> loginOld(@Body HashMap<String, String> userData);

    @POST(Constants.BUS_STATUS)
    Call<ResponseBody> getLocationData(@HeaderMap HashMap<String, Object> map, @Body HashMap<String, String> busMap);

    @POST(Constants.LOCATIONDATA)
    Call<ResponseBody> sendLocationData(@HeaderMap HashMap<String, Object> map, @Body HashMap<String, Object> locationData);

    @POST(Constants.SENSORDATA)
    Call<ResponseBody> sendSensorData(@HeaderMap HashMap<String, Object> map, @Body HashMap<String, Object> sensorData);

    @POST(Constants.SENSORDATA)
    Call<ResponseBody> sendSensorDataOld(@Body HashMap<String, Object> sensorData);

    @POST(Constants.SENSORDATARAW)
    Call<ResponseBody> sendRawSensorData(@HeaderMap HashMap<String, Object> map, @Body HashMap<String, Object> sensorData);

}
