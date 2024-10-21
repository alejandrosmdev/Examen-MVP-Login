package com.example.repasomvp;


import com.example.repasomvp.login.beans.User;
import com.example.repasomvp.login.utils.MyData;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiService {

    static final String IP_BASE = "192.168.104.73:3000";

    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })

    @POST("/login")
    Call<MyData> login(@Body User user);

}
