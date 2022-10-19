package com.nftime.app.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.nftime.app.MainActivity;
import com.nftime.app.NftActivity;
import com.nftime.app.R;
import com.nftime.app.ItemDetailActivity;
import com.nftime.app.RecyclerItem.TodayArtItem;
import com.nftime.app.objects.NftWorkObj;
import com.nftime.app.util.ApplicationConstants;

import java.util.ArrayList;

public class TodayArtRecyclerAdapter extends RecyclerView.Adapter<TodayArtRecyclerAdapter.ViewHolder> {
    private ArrayList<NftWorkObj> todayArtItems;
    private Context context;

    public TodayArtRecyclerAdapter(Context context){
        this.context = context;
    }

    @NonNull
    @Override
    public TodayArtRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_today_art, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TodayArtRecyclerAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.onBind(todayArtItems.get(position));

        holder.img_today_art.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), NftActivity.class);
                NftWorkObj nftWorkObj = todayArtItems.get(position);

                String[] arr = nftWorkObj.filetype.split("/");
                String nftType = arr[0];
                intent.putExtra("nftType", nftType);

                intent.putExtra("work_id", nftWorkObj.work_id);
                intent.putExtra("work_price", nftWorkObj.work_price);
                intent.putExtra("filesize", nftWorkObj.filesize);
                intent.putExtra("exhibition_id", nftWorkObj.exhibition_id);
                intent.putExtra("work_name", nftWorkObj.work_name);
                intent.putExtra("description", nftWorkObj.description);
                intent.putExtra("category", nftWorkObj.category);
                intent.putExtra("filename", nftWorkObj.filename);
                intent.putExtra("filetype", nftWorkObj.filetype);
                intent.putExtra("path", nftWorkObj.path);
                intent.putExtra("thumbnail_path", nftWorkObj.thumbnail_path);
                intent.putExtra("artist_name", nftWorkObj.artist_name);
                intent.putExtra("artist_profile_path", nftWorkObj.artist_profile_path);
                intent.putExtra("artist_address", nftWorkObj.artist_address);

                v.getContext().startActivity(intent);
            }
        });
    }

    public void setTodayArtItems(ArrayList<NftWorkObj> todayArtItems) {
        this.todayArtItems = todayArtItems;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return todayArtItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img_today_art;
        TextView tv_today_art_title;
        TextView tv_today_art_artist;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            img_today_art = itemView.findViewById(R.id.today_art_img);
            tv_today_art_title = itemView.findViewById(R.id.today_art_title);
            tv_today_art_artist = itemView.findViewById(R.id.today_art_artist);
        }

        void onBind(NftWorkObj item) {
            String[] arr = item.filetype.split("/");
            String nftType = arr[0];

            String imgUrl;
            if(nftType.equals("video"))
                imgUrl = ApplicationConstants.AWS_URL + item.thumbnail_path;
            else
                imgUrl = ApplicationConstants.AWS_URL + item.path;

            Glide.with(context)
                    .load(imgUrl)
                    .centerCrop()
                    .placeholder(R.drawable.app_icon_nftime)
                    .into(img_today_art);

           /* if(MainActivity.ownedWorkIds.get(item.work_id) == null || !MainActivity.ownedWorkIds.get(item.work_id)){
                ColorMatrix matrix = new ColorMatrix();
                matrix.setSaturation(0);
                ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
                img_art.setColorFilter(filter);
            }*/

            tv_today_art_title.setText(item.work_name);
            tv_today_art_artist.setText(item.artist_name);
        }
    }
}
