package com.optic.myapplication.ui;


import static com.optic.myapplication.ui.LogInActivity.USER_ID_KEY;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.optic.myapplication.R;
import com.optic.myapplication.ui.chat.HomeActivity;

public class MainActivity extends AppCompatActivity {

    Button signInButton;
    Button logInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        checkExistingAuthInfo();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        logInButton = findViewById(R.id.logInBTN);
        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LogInActivity.class);
                startActivity(intent);
            }
        });

        signInButton = findViewById(R.id.signInBTN);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignInActivity.class);
                startActivity(intent);
            }
        });
    }

    private void checkExistingAuthInfo() {
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        String userId = prefs.getString(USER_ID_KEY, "");
        if (!userId.isEmpty()) {
            Intent intent = new Intent(this, HomeActivity.class);
            intent.addFlags(Intent. FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }
}