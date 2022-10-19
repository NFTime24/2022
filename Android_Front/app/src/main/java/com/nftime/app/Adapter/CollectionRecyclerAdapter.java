package com.nftime.app.Adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.nftime.app.R;
import com.nftime.app.CollectionActivity;
import com.nftime.app.objects.ExhibitionObj;
import com.nftime.app.util.ApplicationConstants;

import java.util.ArrayList;

public class CollectionRecyclerAdapter extends RecyclerView.Adapter<CollectionRecyclerAdapter.ViewHolder> {
    private ArrayList<ExhibitionObj> collectionItems;
    private Intent intent;

    @NonNull
    @Override
    public CollectionRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_collection, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CollectionRecyclerAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.onBind(collectionItems.get(position));

        holder.img_collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(v.getContext(), CollectionActivity.class);

                intent.putExtra("id", collectionItems.get(position).exhibition_id);
                intent.putExtra("img_path", collectionItems.get(position).path);
                intent.putExtra("title", collectionItems.get(position).exhibition_name);
                intent.putExtra("description", collectionItems.get(position).exhibition_description);

                v.getContext().startActivity(intent);
            }
        });
    }

    public void setCollectionItems(ArrayList<ExhibitionObj> collectionItems) {
        this.collectionItems = collectionItems;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return collectionItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img_collection;
        TextView tv_collection_title;
        TextView tv_collection_detail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            img_collection = itemView.findViewById(R.id.img_collection);
            tv_collection_title = itemView.findViewById(R.id.tv_collection_title);
            tv_collection_detail = itemView.findViewById(R.id.tv_collection_detail);
        }

        void onBind(ExhibitionObj item) {
            String imgUrl = ApplicationConstants.AWS_URL + item.path;
            Glide.with(itemView).load(imgUrl).into(img_collection);
            img_collection.setColorFilter(Color.parseColor("#BDBDBD"), PorterDuff.Mode.MULTIPLY);
            tv_collection_title.setText(item.exhibition_name);
            tv_collection_detail.setText(item.exhibition_description);
        }
    }
}
