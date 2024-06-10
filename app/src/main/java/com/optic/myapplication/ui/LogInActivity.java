package com.optic.myapplication.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputLayout;
import com.optic.myapplication.R;
import com.optic.myapplication.data.auth.AuthWebService;
import com.optic.myapplication.data.retrofit.NetworkClient;
import com.optic.myapplication.models.auth.UserLoginRequest;
import com.optic.myapplication.models.auth.UserLoginResponse;
import com.optic.myapplication.ui.chat.HomeActivity;

import retrofit2.Response;

public class LogInActivity extends AppCompatActivity {

    public static final String USER_ID_KEY = "USER_ID";
    public static final String USER_TOKEN_KEY = "USER_TOKEN";

    TextView passText;
    TextView signUpText;
    Button loginButtton;
    TextInputLayout emailInput;
    TextInputLayout passwordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_log_in);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        loginButtton = findViewById(R.id.login_btn);
        emailInput = findViewById(R.id.input_email);
        passwordInput = findViewById(R.id.input_password);
        signUpText = findViewById(R.id.signUpText);
        signUpText.setOnClickListener(v -> {
            Intent intent = new Intent(LogInActivity.this, SignInActivity.class);
            startActivity(intent);
        });

        passText = findViewById(R.id.passText);
        passText.setOnClickListener(v -> {
            Intent intent = new Intent(LogInActivity.this, RecoverPasswordActivity.class);
            startActivity(intent);
        });
        loginButtton.setOnClickListener(v -> {
            String email = emailInput.getEditText().getText().toString();
            String password = passwordInput.getEditText().getText().toString();
            AuthWebService clienteUsuario = NetworkClient.getRetrofit(this).create(AuthWebService.class);
            AsyncTask.execute(() -> {
                Response<UserLoginResponse> response = clienteUsuario.loginUser(
                        new UserLoginRequest(
                                email,
                                password
                        )).blockingGet();
                if (response.isSuccessful()) {
                    SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
                    prefs.edit().putString(USER_ID_KEY, response.body().getUserId()).apply();
                    prefs.edit().putString(USER_TOKEN_KEY, "Bearer " + response.body().getJwt()).apply();
                    Log.d("SAVING USER ID", response.body().getUserId());
                    Intent intent = new Intent(this, HomeActivity.class);
                    intent.addFlags(Intent. FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            });
        });

    }
}