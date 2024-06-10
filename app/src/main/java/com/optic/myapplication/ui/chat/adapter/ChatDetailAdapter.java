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
import com.optic.myapplication.models.chat.ChatDetailIncomingResponse;
import com.optic.myapplication.models.chat.ChatDetailOutgoingResponse;
import com.optic.myapplication.models.chat.ChatDetailResponse;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ChatDetailAdapter extends RecyclerView.Adapter<ChatDetailAdapter.ChatDetailViewHolder> {

    private static final int VIEW_TYPE_INCOMING = 1;
    private static final int VIEW_TYPE_OUTGOING = 2;

    public ArrayList<ChatDetailResponse> chatList;

    public ChatDetailAdapter(ArrayList<ChatDetailResponse> chatList) {
        this.chatList = chatList;
    }

    @NonNull
    @Override
    public ChatDetailAdapter.ChatDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case VIEW_TYPE_INCOMING:
                View incomingMessageItem = inflater.inflate(R.layout.chat_incoming_detail_item, parent, false);
                return new ChatDetailViewHolder(incomingMessageItem);

            case VIEW_TYPE_OUTGOING:
                View outgoingMessageItem = inflater.inflate(R.layout.chat_outgoing_detail_item, parent, false);
                return new ChatDetailViewHolder(outgoingMessageItem);

            default:
                throw new IllegalArgumentException("Invalid view type");
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (chatList.get(position) instanceof ChatDetailIncomingResponse) {
            return VIEW_TYPE_INCOMING;
        } else if (chatList.get(position) instanceof ChatDetailOutgoingResponse) {
            return VIEW_TYPE_OUTGOING;
        }
        return -1;
    }

    @Override
    public void onBindViewHolder(@NonNull ChatDetailAdapter.ChatDetailViewHolder holder, int position) {
        holder.bind(chatList.get(position));
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    public class ChatDetailViewHolder extends RecyclerView.ViewHolder {
        public ChatDetailViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        protected void bind(ChatDetailResponse chatItem) {
            ImageView chatImage;
            TextView chatMessage;
            TextView chatTime;

            chatImage = itemView.findViewById(R.id.chat_item_image);
            chatMessage = itemView.findViewById(R.id.chat_item_message);
            chatTime = itemView.findViewById(R.id.chat_item_time);

            chatImage.setImageDrawable(AppCompatResources.getDrawable(itemView.getContext(), R.drawable.ic_user_placeholder));
            chatMessage.setText(chatItem.getContent());

            OffsetDateTime offsetDateTime = OffsetDateTime.parse(chatItem.getTimestamp());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            String formattedTime = offsetDateTime.format(formatter);
            chatTime.setText(formattedTime);
        }
    }
}
