package com.optic.myapplication.ui.chat;

import static com.optic.myapplication.ui.LogInActivity.USER_ID_KEY;
import static com.optic.myapplication.ui.LogInActivity.USER_TOKEN_KEY;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.optic.myapplication.R;
import com.optic.myapplication.data.chat.ChatWebService;
import com.optic.myapplication.data.retrofit.NetworkClient;
import com.optic.myapplication.models.chat.ChatResponse;
import com.optic.myapplication.ui.chat.adapter.ChatAdapter;

import java.util.List;

import retrofit2.Response;

public class HomeActivity extends AppCompatActivity implements ChatAdapter.OnChatItemClick {

    private RecyclerView chatList;
    private ImageView backBtn;
    private ChatAdapter adapter;
    private EditText searchInput;
    private ImageView searchIcon;
    private ChatWebService clienteChat;
    private FloatingActionButton newChatBtn;
    private SharedPreferences prefs;
    private List<ChatResponse> unFilteredList;
    private String userToken;
    private String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        clienteChat = NetworkClient.getRetrofit(this).create(ChatWebService.class);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initViews();
        setListeners();
    }

    private void initViews() {
        chatList = findViewById(R.id.chat_list);
        backBtn = findViewById(R.id.back_arrow);
        searchInput = findViewById(R.id.search_view);
        searchIcon = findViewById(R.id.search_icon);
        newChatBtn = findViewById(R.id.new_chat_btn);
        prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        userId = prefs.getString(USER_ID_KEY, "");
        getChats();
    }

    private void setListeners() {
        backBtn.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Seguro que quieres cerrar sesión?");
            builder.setPositiveButton("Si", (dialogInterface, i) -> {
                prefs.edit().remove(USER_ID_KEY).apply();
                prefs.edit().remove(USER_TOKEN_KEY).apply();
                finish();
            });
            builder.setNegativeButton("No", null);
            AlertDialog dialog = builder.create();
            dialog.show();
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
               //if (text.toString().length() > 2) {
               //    AsyncTask.execute(() -> {
               //        Response<List<ChatResponse>> response = clienteChat.getChatsByName(userToken, text.toString()).blockingGet();
               //        runOnUiThread(() -> {
               //            if (response.isSuccessful()) {
               //                adapter = new ChatAdapter(response.body(), HomeActivity.this);
               //                chatList.setAdapter(adapter);
               //                chatList.addItemDecoration(new DividerItemDecoration(chatList.getContext(), DividerItemDecoration.VERTICAL));
               //            }
               //        });
               //    });
               //} else {
               //    adapter = new ChatAdapter(unFilteredList, HomeActivity.this);
               //    chatList.setAdapter(adapter);
               //    chatList.addItemDecoration(new DividerItemDecoration(chatList.getContext(), DividerItemDecoration.VERTICAL));
               //}
            }
        });
        newChatBtn.setOnClickListener(v -> {
            Intent intent = new Intent(this, NewChatActivity.class);
            startActivityForResult(intent, 123);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            getChats();
            String newChatId = data.getStringExtra("CHAT_ID");
            String newChatName = data.getStringExtra("CHAT_NAME");
            goToChatDetail(newChatId, newChatName);
        }
    }

    @Override
    public void onClick(ChatResponse chat) {
        goToChatDetail(chat.getChatId(), chat.getChatName());
    }

    private void goToChatDetail(String chatId, String chatName) {
        Intent intent = new Intent(this, ChatDetailActivity.class);
        intent.putExtra("CHAT_ID", chatId);
        intent.putExtra("CHAT_NAME", chatName);
        startActivity(intent);
    }

    private void getChats() {
        if (!userId.isEmpty()) {
            AsyncTask.execute(() -> {
                userToken = prefs.getString(USER_TOKEN_KEY, "");
                Response<List<ChatResponse>> response = clienteChat.getChats(
                        userToken,
                        userId
                ).blockingGet();
                unFilteredList = response.body();
                runOnUiThread(() -> {
                    if (response.isSuccessful()) {
                        adapter = new ChatAdapter(response.body(), this);
                        chatList.setAdapter(adapter);
                        chatList.addItemDecoration(new DividerItemDecoration(chatList.getContext(), DividerItemDecoration.VERTICAL));
                    }
                });
            });
        }
    }
}