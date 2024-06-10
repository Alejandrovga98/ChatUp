package com.optic.myapplication.models.chat;

public class ChatDetailMessageRequest {
    private String chatId;
    private String content;
    private String timestamp;
    private String sender;

    public ChatDetailMessageRequest(String chatId, String content, String timestamp, String sender) {
        this.chatId = chatId;
        this.content = content;
        this.timestamp = timestamp;
        this.sender = sender;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }
}
