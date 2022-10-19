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

import com.bumptech.glide.Glide;
import com.nftime.app.R;
import com.nftime.app.ItemDetailActivity;
import com.nftime.app.RecyclerItem.ArtItem;

import java.util.ArrayList;

public class ArtRecyclerAdapter extends RecyclerView.Adapter<ArtRecyclerAdapter.ViewHolder> {
    private ArrayList<ArtItem> topArtList;
    private Intent intent;

    @NonNull
    @Override
    public ArtRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_artitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArtRecyclerAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.onBind(topArtList.get(position));

        holder.img_art.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(v.getContext(), ItemDetailActivity.class);
                intent.putExtra("img", topArtList.get(position).getResourceId());
                intent.putExtra("title", topArtList.get(position).getTitle());
                intent.putExtra("name", topArtList.get(position).getName());
                intent.putExtra("price", topArtList.get(position).getPrice());
                v.getContext().startActivity(intent);
            }
        });
    }

    public void setTopArtList(ArrayList<ArtItem> list) {
        this.topArtList = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return topArtList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img_art;
        TextView tv_title;
        TextView tv_name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            img_art = itemView.findViewById(R.id.img_art);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_name = itemView.findViewById(R.id.tv_name);

        }

        void onBind(ArtItem item) {
            img_art.setImageResource(item.getResourceId());
            Glide.with(itemView).load(item.getResourceId()).into(img_art);
            tv_title.setText(item.getTitle());
            tv_name.setText(item.getName());
        }
    }
}
