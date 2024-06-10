package com.optic.myapplication.ui.chat.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.recyclerview.widget.RecyclerView;

import com.optic.myapplication.R;
import com.optic.myapplication.models.chat.ChatResponse;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;


public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {

    public interface OnChatItemClick {
        void onClick(ChatResponse chat);
    }

    private OnChatItemClick listener;

    public List<ChatResponse> chatList;

    public ChatAdapter(List<ChatResponse> chatList, OnChatItemClick listener) {
        this.chatList = chatList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item, parent, false);
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        holder.bind(chatList.get(position));
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    public class ChatViewHolder extends RecyclerView.ViewHolder {
        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        protected void bind(ChatResponse chatItem) {
            ImageView chatImage;
            TextView chatName;
            TextView chatTime;

            chatImage = itemView.findViewById(R.id.chat_image);
            chatName = itemView.findViewById(R.id.chat_name);
            chatTime = itemView.findViewById(R.id.chat_time);

            chatImage.setImageDrawable(AppCompatResources.getDrawable(itemView.getContext(), R.drawable.ic_user_placeholder));
            chatName.setText(chatItem.getChatName());
            if (chatItem.getLastMessage() != null && chatItem.getLastMessage().getTimeStamp() != null) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
                LocalDateTime dateTime = LocalDateTime.parse(chatItem.getLastMessage().getTimeStamp(), formatter);
                DateTimeFormatter horaFormatter = DateTimeFormatter.ofPattern("HH:mm");
                chatTime.setText(dateTime.format(horaFormatter));
            }

            itemView.setOnClickListener(v -> {
                listener.onClick(chatItem);
            });
        }
    }
}
