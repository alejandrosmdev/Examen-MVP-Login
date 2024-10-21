package com.example.repasomvp.login.model;

import static com.example.repasomvp.ApiService.IP_BASE;

import com.example.repasomvp.ApiService;
import com.example.repasomvp.RetrofitCliente;
import com.example.repasomvp.login.ContractLoginUser;
import com.example.repasomvp.login.beans.User;
import com.example.repasomvp.login.presenter.LoginUserPresenter;
import com.example.repasomvp.login.utils.MyData;

import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginUserModel implements ContractLoginUser.Model {

    private LoginUserPresenter presenter;
    public LoginUserModel(LoginUserPresenter presenter){
        this.presenter = presenter;
    }


    @Override
    public void loginAPI(User user, OnLoginUserListener onLoginUserListener) {
        ApiService apiService = RetrofitCliente.getClient("http://" + IP_BASE + "").create(ApiService.class);

        Call<MyData> call = apiService.login(user);
        call.enqueue(new Callback<MyData>() {
            @Override
            public void onResponse(Call<MyData> call, Response<MyData> response) {
                if (response.isSuccessful()) {
                    MyData myData = response.body();
                    if (myData != null && myData.getUser() != null) {
                        // Ahora obtenemos el objeto usuario correctamente
                        onLoginUserListener.Onfinished(myData.getUser());
                    } else {
                        onLoginUserListener.Onfailure("Login failed: No user data received");
                    }
                } else {
                    try {
                        JSONObject jObjError = new JSONObject(response.errorBody().string());
                        onLoginUserListener.Onfailure("Login failed: " + jObjError.getString("error"));
                    } catch (Exception e) {
                        onLoginUserListener.Onfailure("Login failed: " + response.message());
                    }
                }
            }

            @Override
            public void onFailure(Call<MyData> call, Throwable t) {
                onLoginUserListener.Onfailure("Network error: " + t.getMessage());
            }
        });
    }

}
