package com.optic.myapplication.ui;

import static com.optic.myapplication.ui.LogInActivity.USER_ID_KEY;
import static com.optic.myapplication.ui.LogInActivity.USER_TOKEN_KEY;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.optic.myapplication.R;
import com.optic.myapplication.data.auth.AuthWebService;
import com.optic.myapplication.data.retrofit.NetworkClient;
import com.optic.myapplication.models.auth.UserLoginRequest;
import com.optic.myapplication.models.auth.UserLoginResponse;
import com.optic.myapplication.models.auth.UserRegisterRequest;
import com.optic.myapplication.ui.chat.HomeActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Response;

public class SignInActivity extends AppCompatActivity {

    TextView logTextButton;
    TextInputEditText numberInput;
    TextInputEditText usernameInput;
    TextInputEditText passwordInput;
    TextInputEditText confirmPasswordInput;
    TextInputEditText emailInput;
    Button mSingInButton;
    FirebaseAuth mAuth;
    FirebaseFirestore mFireStore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sign_in);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        numberInput = findViewById(R.id.numberInput);
        usernameInput = findViewById(R.id.usernameInput);
        passwordInput = findViewById(R.id.passwordInput);
        confirmPasswordInput = findViewById(R.id.confirmPasswordInput);
        emailInput = findViewById(R.id.emailInput);
        mSingInButton = findViewById(R.id.mSignInButton);

        mAuth = FirebaseAuth.getInstance();
        mFireStore = FirebaseFirestore.getInstance();

        mSingInButton.setOnClickListener(v -> register());


        logTextButton = findViewById(R.id.logText);
        logTextButton.setOnClickListener(v -> {
            Intent intent = new Intent(SignInActivity.this, LogInActivity.class);
            startActivity(intent);
        });

    }

    private void register() {
        String number = numberInput.getText().toString();
        String username = usernameInput.getText().toString();
        String password = passwordInput.getText().toString();
        String confirmPassword = confirmPasswordInput.getText().toString();
        String email = emailInput.getText().toString();

        if (!username.isEmpty() && !number.isEmpty() && !password.isEmpty() && !confirmPassword.isEmpty() && !email.isEmpty()){
            if (isEmailValid(email)){
                if(password.equals(confirmPassword)) {
                    if (password.length() >= 6){
                        createUser(username,email, password);
                    }
                    else {
                        Toast.makeText(this, "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show();
                    }

                }
                else {
                    Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                }
            }
            else {
                Toast.makeText(this, "El email no es valido", Toast.LENGTH_SHORT).show();
            }

        }
        else {
            Toast.makeText(this, "Faltan campos por rellenar", Toast.LENGTH_SHORT).show();
        }
    }

    private void createUser(final String username, final String email, String password){
        AsyncTask.execute(() -> {
            AuthWebService clienteUsuario = NetworkClient.getRetrofit(this).create(AuthWebService.class);
            Response<String> response = clienteUsuario.registerUser(new UserRegisterRequest(
                    username,
                    email,
                    password
            )).blockingGet();
            if (response.isSuccessful()) {
                Response<UserLoginResponse> loginResponse = clienteUsuario.loginUser(
                        new UserLoginRequest(
                                email,
                                password
                        )).blockingGet();
                if (loginResponse.isSuccessful()) {
                    SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
                    prefs.edit().putString(USER_ID_KEY, loginResponse.body().getUserId()).apply();
                    prefs.edit().putString(USER_TOKEN_KEY, "Bearer " + loginResponse.body().getJwt()).apply();
                    Intent intent = new Intent(this, HomeActivity.class);
                    intent.addFlags(Intent. FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }
        });
    }

    public boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

}