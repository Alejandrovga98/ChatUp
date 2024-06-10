package com.optic.myapplication.ui.chat;

import static com.optic.myapplication.ui.LogInActivity.USER_ID_KEY;
import static com.optic.myapplication.ui.LogInActivity.USER_TOKEN_KEY;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.optic.myapplication.R;
import com.optic.myapplication.data.chat.ChatWebService;
import com.optic.myapplication.data.chat.ChatWebSocketService;
import com.optic.myapplication.data.retrofit.NetworkClient;
import com.optic.myapplication.models.chat.ChatDetailIncomingResponse;
import com.optic.myapplication.models.chat.ChatDetailMessageRequest;
import com.optic.myapplication.models.chat.ChatDetailOutgoingResponse;
import com.optic.myapplication.models.chat.ChatDetailResponse;
import com.optic.myapplication.ui.chat.adapter.ChatDetailAdapter;
import com.tinder.scarlet.Stream;
import com.tinder.scarlet.WebSocket;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.functions.Consumer;
import retrofit2.Response;

public class ChatDetailActivity extends AppCompatActivity {

    private ChatDetailAdapter adapter;
    private RecyclerView chatList;
    private TextView chatName;
    private EditText chatInput;
    private ImageButton sendButton;
    private String currentUserId;
    private ChatWebSocketService chatWebSocketService;

    //private List<ChatDetailResponse> chatListPlaceholder = Arrays.asList(
    //        new ChatDetailIncomingResponse("Hola", "11:54"),
    //        new ChatDetailOutgoingResponse("Hola", "12:10"),
    //        new ChatDetailIncomingResponse("Que tal", "13:43"),
    //        new ChatDetailOutgoingResponse("Bien", "14:56"),
    //        new ChatDetailOutgoingResponse("Y tu", "14:59"),
    //        new ChatDetailIncomingResponse("Bien", "17:08"),
    //        new ChatDetailOutgoingResponse("Genial", "18:12")
    //);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chat_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initViews();
        ChatWebService clienteChat = NetworkClient.getRetrofit(this).create(ChatWebService.class);
        chatWebSocketService = NetworkClient.getScarlet().create(ChatWebSocketService.class);
        AsyncTask.execute(() -> {
            SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
            String userToken = prefs.getString(USER_TOKEN_KEY, "");
            currentUserId = prefs.getString(USER_ID_KEY, "");

            Response<List<ChatDetailResponse>> response = clienteChat.getChatMessages(userToken, getIntent().getStringExtra("CHAT_ID")).blockingGet();
            runOnUiThread(() -> {
                if (response.isSuccessful() && response.body() != null) {
                    List<ChatDetailResponse> bodyList = response.body();
                    ArrayList<ChatDetailResponse> mappedList = new ArrayList<>();
                    for (int i = 0; i < bodyList.size(); i++) {
                        if (bodyList.get(i).getSender().equals(currentUserId)) {
                            mappedList.add(new ChatDetailOutgoingResponse(
                                    bodyList.get(i).getContent(),
                                    bodyList.get(i).getTimestamp(),
                                    bodyList.get(i).getSender()
                            ));
                        } else {
                            mappedList.add(new ChatDetailIncomingResponse(
                                    bodyList.get(i).getContent(),
                                    bodyList.get(i).getTimestamp(),
                                    bodyList.get(i).getSender()
                            ));
                        }
                    }
                    adapter = new ChatDetailAdapter(mappedList);
                    chatList.setAdapter(adapter);
                    chatList.smoothScrollToPosition(adapter.getItemCount());
                }
            });
        });
        setListeners();
        initObservers();
    }

    private void initViews() {
        chatName = findViewById(R.id.chat_name);
        chatList = findViewById(R.id.chat_message_list);
        chatInput = findViewById(R.id.message_input);
        sendButton = findViewById(R.id.send_btn);
        chatName.setText(getIntent().getStringExtra("CHAT_NAME"));
    }

    private void setListeners() {
        sendButton.setOnClickListener(v -> {
            if (!chatInput.getText().toString().isEmpty()) {
                Date date = new Date();
                String pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";
                SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
                String formattedDate = dateFormat.format(date);
                adapter.chatList.add(new ChatDetailOutgoingResponse(
                        chatInput.getText().toString(),
                        formattedDate,
                        currentUserId)
                );
                chatInput.setText("");
                adapter.notifyItemInserted(adapter.getItemCount()+1);
                chatList.smoothScrollToPosition(adapter.getItemCount());
                chatWebSocketService.sendMessage(
                        new ChatDetailMessageRequest(
                                getIntent().getStringExtra("CHAT_ID"),
                                chatInput.getText().toString(),
                                formattedDate,
                                currentUserId
                        )
                );
            }
        });
    }

    private void initObservers() {
        chatWebSocketService.observeTicker().start(new Stream.Observer<String>() {
            @Override
            public void onNext(String receivedMessage) {
                Log.d("asd", "onNext");

            }

            @Override
            public void onError(Throwable throwable) {
                Log.d("asd", "onError");

            }

            @Override
            public void onComplete() {
                Log.d("asd", "onComplete");

            }
        });
    }
}