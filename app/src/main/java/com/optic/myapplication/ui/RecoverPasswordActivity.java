package com.optic.myapplication.ui;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
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

public class RecoverPasswordActivity extends AppCompatActivity {

    TextView recoverLogText;
    Button recoverPasswordButton;
    TextInputLayout recoverPasswordEmailInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_recover_password);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recoverLogText = findViewById(R.id.recoverlogText);
        recoverPasswordButton = findViewById(R.id.recover_password_button);
        recoverPasswordEmailInput = findViewById(R.id.recover_password_email);

        recoverLogText.setOnClickListener(v -> {
            Intent intent = new Intent(RecoverPasswordActivity.this, LogInActivity.class);
            startActivity(intent);
        });

        recoverPasswordButton.setOnClickListener(v -> {
            String emailInput = recoverPasswordEmailInput.getEditText().getText().toString();
            if (!emailInput.isEmpty()) {
                AuthWebService clienteAuth = NetworkClient.getRetrofit(this).create(AuthWebService.class);
                AsyncTask.execute(() -> {
                    clienteAuth.forgotPassword(emailInput);
                });
            }
        });
    }
}