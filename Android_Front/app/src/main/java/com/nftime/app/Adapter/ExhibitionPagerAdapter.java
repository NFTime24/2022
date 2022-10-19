package com.nftime.app.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nftime.app.R;
import com.nftime.app.RecyclerItem.ExhibitionItem;

import java.util.ArrayList;

public class ExhibitionPagerAdapter extends RecyclerView.Adapter<ExhibitionPagerAdapter.ViewHolder> {
    private ArrayList<ExhibitionItem> exhibitionItems;

    @NonNull
    @Override
    public ExhibitionPagerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_exhibition, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBind(exhibitionItems.get(position));
    }

    public void setExhibitionItems(ArrayList<ExhibitionItem> exhibitionItems) {
        this.exhibitionItems = exhibitionItems;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return exhibitionItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView home_exhibition_img;
        TextView home_exhibition_title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            home_exhibition_img = itemView.findViewById(R.id.home_exhibition_img);
            home_exhibition_title = itemView.findViewById(R.id.tv_exhibition_title);
        }

        void onBind(ExhibitionItem item) {
            home_exhibition_img.setImageResource(item.getResourceId());
            home_exhibition_title.setText(item.getName());
        }
    }
}
