package com.nftime.app.Adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nftime.app.R;
import com.nftime.app.RecyclerItem.FanTalkItem;
import com.nftime.app.RecyclerItem.MyPageListItem;

import java.util.ArrayList;

public class MyPageItemRecyclerAdapter extends RecyclerView.Adapter<MyPageItemRecyclerAdapter.ViewHolder> {
    private ArrayList<MyPageListItem> myPageListItems;
    private Intent intent;

    @NonNull
    @Override
    public MyPageItemRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_mypage_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyPageItemRecyclerAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.onBind(myPageListItems.get(position));
    }

    public void setMyPageListItem(ArrayList<MyPageListItem> myPageListItems) {
        this.myPageListItems = myPageListItems;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return myPageListItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView itemIcon;
        TextView itemText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemIcon = itemView.findViewById(R.id.icon_item);
            itemText = itemView.findViewById(R.id.tv_item);
        }

        void onBind(MyPageListItem item) {
            itemIcon.setImageResource(item.getItemIcon());
            itemText.setText(item.getItemText());
        }
    }
}
