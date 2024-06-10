package com.optic.myapplication.models.chat;

public class ChatDetailOutgoingResponse extends ChatDetailResponse {
    public ChatDetailOutgoingResponse(String message, String time, String sender) {
        super.setContent(message);
        super.setTimestamp(time);
        super.setSender(sender);
    }
}
