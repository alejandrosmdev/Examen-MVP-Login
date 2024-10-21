package com.example.repasomvp.login.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.repasomvp.R;
import com.example.repasomvp.login.ContractLoginUser;
import com.example.repasomvp.login.beans.User;
import com.example.repasomvp.login.presenter.LoginUserPresenter;

public class LoginUserM extends AppCompatActivity implements ContractLoginUser.View {

    private EditText edtEmail;
    private EditText edtPassword;
    private Button btnLogin;

    private LoginUserPresenter presenter = new LoginUserPresenter(this);

    //PATRON SINGLETON
    private static LoginUserM mainActivity = null;
    public static LoginUserM getInstance(){
        return mainActivity;
    }
    //FIN

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_user_m);
        mainActivity = this;
        initComponents();
    }
    private void initComponents() {
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = new User(edtEmail.getText().toString(), edtPassword.getText().toString());
                presenter.login(user);
            }
        });
    }

    @Override
    public void successLogin(User user) {
        Toast.makeText(this, "Login successful: " + user.getEmail(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void failureLogin(String error) {
        Toast.makeText(this, "Login failed: " + error, Toast.LENGTH_SHORT).show();
    }
}