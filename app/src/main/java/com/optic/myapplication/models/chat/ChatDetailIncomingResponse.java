package com.optic.myapplication.models.chat;

public class ChatDetailIncomingResponse extends ChatDetailResponse {

    public ChatDetailIncomingResponse(String message, String time, String sender) {
        super.setContent(message);
        super.setTimestamp(time);
        super.setSender(sender);
    }
}
