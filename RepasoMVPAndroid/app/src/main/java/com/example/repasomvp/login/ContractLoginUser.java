package com.example.repasomvp.login;

import com.example.repasomvp.login.beans.User;

public interface ContractLoginUser {
    interface View{
        void successLogin(User user);
        void failureLogin(String err);
    }

    interface Presenter{
        void login(User user);
    }

    interface Model{
        interface OnLoginUserListener{
            void Onfinished(User user);
            void Onfailure(String err);
        }

        void loginAPI(User user, OnLoginUserListener onLoginUserListener);
    }
}
