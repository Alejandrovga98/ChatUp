package com.optic.myapplication.data.chat;

import com.optic.myapplication.models.chat.ChatDetailMessageRequest;
import com.tinder.scarlet.Stream;
import com.tinder.scarlet.WebSocket;
import com.tinder.scarlet.ws.Receive;
import com.tinder.scarlet.ws.Send;

import io.reactivex.Flowable;

public interface ChatWebSocketService {

    @Receive
    Stream<String> observeTicker();

    @Send
    void sendMessage(ChatDetailMessageRequest request);

    @Receive
    Flowable<String> observeMessage();
}
