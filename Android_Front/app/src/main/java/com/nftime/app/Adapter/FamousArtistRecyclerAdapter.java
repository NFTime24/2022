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
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.nftime.app.ArtistActivity;
import com.nftime.app.ItemDetailActivity;
import com.nftime.app.R;
import com.nftime.app.RecyclerItem.FamousArtistItem;
import com.nftime.app.objects.ArtistObj;
import com.nftime.app.util.ApplicationConstants;

import java.util.ArrayList;

public class FamousArtistRecyclerAdapter extends RecyclerView.Adapter<FamousArtistRecyclerAdapter.ViewHolder> {
    Context context;

    private ArrayList<ArtistObj> famousArtistItems;
    private Intent intent;

    public FamousArtistRecyclerAdapter(Context context){
        this.context = context;

        famousArtistItems = new ArrayList<>();
    }

    @NonNull
    @Override
    public FamousArtistRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_famous_artist, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        ArtistObj artistObj = famousArtistItems.get(position);
        holder.onBind(artistObj, position);

        holder.artist_cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(v.getContext(), ArtistActivity.class);

                intent.putExtra("id", artistObj.id);
                intent.putExtra("profile_id", artistObj.profile_id);
                intent.putExtra("name", artistObj.name);
                intent.putExtra("address", artistObj.address);
                intent.putExtra("path", artistObj.path);

                v.getContext().startActivity(intent);
            }
        });
    }

    public void setFamousArtistItems(ArrayList<ArtistObj> famousArtistItems) {
        this.famousArtistItems = famousArtistItems;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return famousArtistItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView famous_artist_ranking;
        ImageView famous_artist_profile;
        TextView famous_artsit_name;
        CardView artist_cardview;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            famous_artist_ranking = itemView.findViewById(R.id.famous_artist_ranking);
            famous_artist_profile = itemView.findViewById(R.id.famous_artist_profile);
            famous_artsit_name = itemView.findViewById(R.id.famous_artist_name);
            artist_cardview = itemView.findViewById(R.id.Artist_cardView);
        }

        void onBind(ArtistObj item, int position) {
            famous_artist_ranking.setText(String.valueOf(position + 1));
            String imgUrl = ApplicationConstants.AWS_URL + item.path;

            Glide.with(context)
                    .load(imgUrl)
                    .centerCrop()
                    .placeholder(R.drawable.mypage_profile_picture)
                    .into(famous_artist_profile);
            famous_artsit_name.setText(item.name);
        }
    }
}
