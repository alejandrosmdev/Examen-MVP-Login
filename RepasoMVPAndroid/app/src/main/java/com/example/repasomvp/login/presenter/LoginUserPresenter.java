package com.example.repasomvp.login.presenter;

import com.example.repasomvp.login.ContractLoginUser;
import com.example.repasomvp.login.beans.User;
import com.example.repasomvp.login.model.LoginUserModel;

public class LoginUserPresenter implements ContractLoginUser.Presenter,
                                        ContractLoginUser.Model.OnLoginUserListener{

    private ContractLoginUser.View view;
    private ContractLoginUser.Model model;

    public LoginUserPresenter(ContractLoginUser.View view) {
        this.view = view;
        model = new LoginUserModel(this);
    }

    @Override
    public void login(User user) {
        model.loginAPI(user, this);
    }


    @Override
    public void Onfinished(User user) {
        view.successLogin(user);
    }

    @Override
    public void Onfailure(String err) {
        view.failureLogin(err);
    }
}
