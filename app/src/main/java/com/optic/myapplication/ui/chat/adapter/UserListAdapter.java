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
import com.optic.myapplication.models.user.UserResponse;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.UserViewHolder> {

    public interface OnUserItemClick {
        void onClick(UserResponse user);
    }

    private UserListAdapter.OnUserItemClick listener;

    public List<UserResponse> userList;

    public UserListAdapter(List<UserResponse> userList, UserListAdapter.OnUserItemClick listener) {
        this.userList = userList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item, parent, false);
        return new UserListAdapter.UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserListAdapter.UserViewHolder holder, int position) {
        holder.bind(userList.get(position));
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        protected void bind(UserResponse userItem) {
            ImageView userImage;
            TextView userName;

            userImage = itemView.findViewById(R.id.chat_image);
            userName = itemView.findViewById(R.id.chat_name);

            userImage.setImageDrawable(AppCompatResources.getDrawable(itemView.getContext(), R.drawable.ic_user_placeholder));
            userName.setText(userItem.getUsername());

            itemView.setOnClickListener(v -> {
                listener.onClick(userItem);
            });
        }
    }
}