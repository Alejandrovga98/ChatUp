package com.optic.myapplication.models.chat;

import java.util.List;

public class CreateChatRequest {
    private String chatName;
    private List<String> members;
    private String chatType;

    public CreateChatRequest(String chatName, List<String> members, String chatType) {
        this.chatName = chatName;
        this.members = members;
        this.chatType = chatType;
    }

    public String getChatName() {
        return chatName;
    }

    public void setChatName(String chatName) {
        this.chatName = chatName;
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
