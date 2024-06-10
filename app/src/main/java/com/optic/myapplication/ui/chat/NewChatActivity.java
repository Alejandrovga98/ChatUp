package com.optic.myapplication.ui.chat;

import static com.optic.myapplication.ui.LogInActivity.USER_ID_KEY;
import static com.optic.myapplication.ui.LogInActivity.USER_TOKEN_KEY;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.optic.myapplication.R;
import com.optic.myapplication.data.chat.ChatWebService;
import com.optic.myapplication.data.retrofit.NetworkClient;
import com.optic.myapplication.data.user.UserWebService;
import com.optic.myapplication.models.chat.CreateChatRequest;
import com.optic.myapplication.models.chat.CreateChatResponse;
import com.optic.myapplication.models.user.UserResponse;
import com.optic.myapplication.ui.chat.adapter.UserListAdapter;

import java.util.Arrays;
import java.util.List;

import retrofit2.Response;

public class NewChatActivity extends AppCompatActivity implements UserListAdapter.OnUserItemClick {

    private RecyclerView userList;
    private ImageView backBtn;
    private UserListAdapter adapter;
    private EditText searchInput;
    private ImageView searchIcon;
    private UserWebService clienteUser;
    private ChatWebService clienteChat;
    private SharedPreferences prefs;
    private String userToken;
    private String userId;
    private List<UserResponse> unFilteredList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_new_chat);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        clienteUser = NetworkClient.getRetrofit(this).create(UserWebService.class);
        clienteChat = NetworkClient.getRetrofit(this).create(ChatWebService.class);
        initViews();
        setListeners();
    }

    private void initViews() {
        prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        userToken = prefs.getString(USER_TOKEN_KEY, "");
        userId = prefs.getString(USER_ID_KEY, "");
        userList = findViewById(R.id.user_list);
        backBtn = findViewById(R.id.back_arrow);
        searchInput = findViewById(R.id.search_view);
        searchIcon = findViewById(R.id.search_icon);
        AsyncTask.execute(() -> {
            Response<List<UserResponse>> response = clienteUser.getAllUsers(userToken).blockingGet();
            unFilteredList = response.body();
            runOnUiThread(() -> {
                if (response.isSuccessful()) {
                    adapter = new UserListAdapter(response.body(), this);
                    userList.setAdapter(adapter);
                }
            });
        });
    }

    private void setListeners() {
        backBtn.setOnClickListener(v -> {
            finish();
        });
        searchIcon.setOnClickListener(v -> {
            searchInput.setVisibility(View.VISIBLE);
            searchInput.requestFocus();
        });
        searchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence text, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence text, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable text) {
                if (text.toString().length() > 2) {
                    AsyncTask.execute(() -> {
                        Response<List<UserResponse>> response = clienteUser.searchUsers(userToken, text.toString()).blockingGet();
                        runOnUiThread(() -> {
                            if (response.isSuccessful()) {
                                adapter = new UserListAdapter(response.body(), NewChatActivity.this);
                                userList.setAdapter(adapter);
                                userList.addItemDecoration(new DividerItemDecoration(userList.getContext(), DividerItemDecoration.VERTICAL));
                            }
                        });
                    });
                } else {
                    adapter = new UserListAdapter(unFilteredList, NewChatActivity.this);
                    userList.setAdapter(adapter);
                    userList.addItemDecoration(new DividerItemDecoration(userList.getContext(), DividerItemDecoration.VERTICAL));
                }
            }
        });
    }

    @Override
    public void onClick(UserResponse user) {
        AsyncTask.execute(() -> {
            Response<CreateChatResponse> response = clienteChat.createChat(userToken, new CreateChatRequest(
                    user.getUsername(),
                    Arrays.asList(userId, user.getId()),
                    "Grupal"
            )).blockingGet();
            runOnUiThread(() -> {
                if (response.isSuccessful()) {
                    Intent responseIntent = new Intent();
                    responseIntent.putExtra("CHAT_ID", response.body().getChatId());
                    responseIntent.putExtra("CHAT_NAME", response.body().getName());
                    setResult(RESULT_OK, responseIntent);
                    finish();
                } else {
                    if (response.code() == 400) {
                        Toast.makeText(this, "Ya existe un chat con este usuario", Toast.LENGTH_LONG).show();
                    }
                }
            });
        });
    }
}