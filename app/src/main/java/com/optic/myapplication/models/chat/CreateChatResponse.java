package com.optic.myapplication.models.chat;

import java.util.List;

public class CreateChatResponse {
    private String id;
    private String chatId;
    private String name;
    private List<String> members;
    private String chatType;

    public CreateChatResponse(String id, String chatId, String name, List<String> members, String chatType) {
        this.id = id;
        this.chatId = chatId;
        this.name = name;
        this.members = members;
        this.chatType = chatType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getMembers() {
        return members;
    }

    public void setMembers(List<String> members) {
        this.members = members;
    }

    public String getChatType() {
        return chatType;
    }

    public void setChatType(String chatType) {
        this.chatType = chatType;
    }
}
