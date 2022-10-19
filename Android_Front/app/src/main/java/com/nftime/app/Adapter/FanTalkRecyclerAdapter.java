package com.nftime.app.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.nftime.app.ItemDetailActivity;
import com.nftime.app.R;
import com.nftime.app.RecyclerItem.FanTalkItem;
import com.nftime.app.RecyclerItem.TodayArtItem;
import com.nftime.app.objects.FantalkObj;
import com.nftime.app.util.ApplicationConstants;

import java.util.ArrayList;

public class FanTalkRecyclerAdapter extends RecyclerView.Adapter<FanTalkRecyclerAdapter.ViewHolder> {
    private Context context;
    private ArrayList<FantalkObj> fanTalkItems;
    private Intent intent;

    public FanTalkRecyclerAdapter(Context context){
        this.context = context;
        fanTalkItems = new ArrayList<>();
    }

    @NonNull
    @Override
    public FanTalkRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_fan_talk, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FanTalkRecyclerAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.onBind(fanTalkItems.get(position));
    }

    public void setFanTalkItems(ArrayList<FantalkObj> FanTalkItems) {
        this.fanTalkItems = FanTalkItems;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return fanTalkItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView fan_profile_image;
        TextView fan_name;
        TextView fan_talk;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            fan_profile_image = itemView.findViewById(R.id.fan_profile_image);
            fan_name = itemView.findViewById(R.id.fan_name);
            fan_talk = itemView.findViewById(R.id.fan_talk);
        }

        void onBind(FantalkObj item) {
            String imgUrl = ApplicationConstants.AWS_URL + item.path;

            Glide.with(context)
                    .load(imgUrl)
                    .centerCrop()
                    .placeholder(R.drawable.mypage_profile_picture)
                    .into(fan_profile_image);

            fan_name.setText(item.nickname);
            fan_talk.setText(item.post_text);
        }
    }
}
