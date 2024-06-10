package com.optic.myapplication.models.chat;

import androidx.annotation.Nullable;

public class ChatResponse {
   private String chatId;
   private String chatName;
   private String chatType;
   @Nullable
   private LastMessage lastMessage;

    public ChatResponse(String chatId, String chatName, String chatType, LastMessage lastMessage) {
        this.chatId = chatId;
        this.chatName = chatName;
        this.chatType = chatType;
        this.lastMessage = lastMessage;
    }

    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }

    public String getChatName() {
        return chatName;
    }

    public void setChatName(String chatName) {
        this.chatName = chatName;
    }

    public String getChatType() {
        return chatType;
    }

    public void setChatType(String chatType) {
        this.chatType = chatType;
    }

    @Nullable
    public LastMessage getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(@Nullable LastMessage lastMessage) {
        this.lastMessage = lastMessage;
    }

    public class LastMessage {
        private String id;
        private String chatId;
        private String sender;
        private String senderUsername;
        private String content;
        private String timeStamp;

        public LastMessage(String id, String chatId, String sender, String senderUsername, String content, String timeStamp) {
            this.id = id;
            this.chatId = chatId;
            this.sender = sender;
            this.senderUsername = senderUsername;
            this.content = content;
            this.timeStamp = timeStamp;
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

        public String getSender() {
            return sender;
        }

        public void setSender(String sender) {
            this.sender = sender;
        }

        public String getSenderUsername() {
            return senderUsername;
        }

        public void setSenderUsername(String senderUsername) {
            this.senderUsername = senderUsername;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getTimeStamp() {
            return timeStamp;
        }

        public void setTimeStamp(String timeStamp) {
            this.timeStamp = timeStamp;
        }
    }
}
