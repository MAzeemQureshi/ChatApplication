package com.example.chatapplication;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder> {

    private Context context;
    private List<MessageModel> messageModelList;
    private String currentUserId; // Add a field to store the current user ID

    public MessageAdapter(Context context) {
        this.context = context;
        this.messageModelList = new ArrayList<>();
        this.currentUserId = FirebaseAuth.getInstance().getUid(); // Initialize the current user ID
    }

    public void add(MessageModel messageModel) {
        messageModelList.add(messageModel);
        notifyDataSetChanged();
    }

    public void clear() {
        messageModelList.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        MessageModel messageModel = messageModelList.get(position);
        holder.msg.setText(messageModel.getMessage());

        if (messageModel.getSenderId().equals(currentUserId)) {
            holder.msg.setBackground(context.getResources().getDrawable(R.drawable.message_text_background_sender));
            holder.main.setGravity(Gravity.END);
        } else {
            holder.msg.setBackground(context.getResources().getDrawable(R.drawable.message_text_background_receiver));
            holder.main.setGravity(Gravity.START);
        }
    }

    @Override
    public int getItemCount() {
        return messageModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView msg;
        private LinearLayout main;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            msg = itemView.findViewById(R.id.message);
            main = itemView.findViewById(R.id.mainMessageLayout);
        }
    }
}