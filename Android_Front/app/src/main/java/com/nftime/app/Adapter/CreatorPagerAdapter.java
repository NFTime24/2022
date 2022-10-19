package com.nftime.app.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nftime.app.R;
import com.nftime.app.RecyclerItem.ArtistItem;

import java.util.ArrayList;

public class CreatorPagerAdapter extends RecyclerView.Adapter<CreatorPagerAdapter.ViewHolder> {
    private ArrayList<ArtistItem> todayArtist;

    @NonNull
    @Override
    public CreatorPagerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_today_artist, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CreatorPagerAdapter.ViewHolder holder, int position) {
        holder.onBind(todayArtist.get(position));
    }

    public void setTodayArtist(ArrayList<ArtistItem> todayArtist) {
        this.todayArtist = todayArtist;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return todayArtist.size();
    } // 뷰페이저2에서는 FragmentStateAdapter를 사용한다.

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView home_today_artist_profile;
        TextView home_today_artist_name;
        TextView home_today_artist_modifier;
        ImageView home_today_artist_art;
        TextView home_today_artist_art_name;
        TextView home_today_artist_art_artist;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            home_today_artist_profile = itemView.findViewById(R.id.home_today_artsit_profile);
            home_today_artist_name = itemView.findViewById(R.id.home_today_artist_name);
            home_today_artist_modifier = itemView.findViewById(R.id.home_today_artist_modifier);
            home_today_artist_art = itemView.findViewById(R.id.home_today_artist_art);
            home_today_artist_art_name = itemView.findViewById(R.id.home_today_artist_art_name);
            home_today_artist_art_artist = itemView.findViewById(R.id.home_today_artist_art_artist);
        }

        void onBind(ArtistItem item) {
            home_today_artist_profile.setImageResource(item.getProfile());
            home_today_artist_name.setText(item.getArtistName());
            home_today_artist_modifier.setText(item.getArtistModifier());
            home_today_artist_art.setImageResource(item.getArt());
            home_today_artist_art_name.setText(item.getArtName());
            home_today_artist_art_artist.setText(item.getArtistName());
        }
    }

}
