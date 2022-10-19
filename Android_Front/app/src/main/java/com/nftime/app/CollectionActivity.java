package com.nftime.app;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.nftime.app.Adapter.CollectionNftRecyclerAdapter;
import com.nftime.app.objects.NftWorkObj;
import com.nftime.app.util.ApplicationConstants;
import com.nftime.app.util.ArrayHelper;
import com.nftime.app.util.asyncTasks.AsyncResponse;
import com.nftime.app.util.asyncTasks.GetWorksInExhibitionTask;

import java.util.ArrayList;

public class CollectionActivity extends AppCompatActivity {
    private Context context;

    private Intent intent;
    private int id;
    private String img_path;
    private String title;
    private String description;
    private Button btn_ticket;

    RecyclerView rv_collection;
    CollectionNftRecyclerAdapter collectionNftRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;

        setContentView(R.layout.activity_collection);

        ImageView img_collection = findViewById(R.id.img_collection);
        TextView tv_title = findViewById(R.id.tv_collection_title);
        TextView tv_description = findViewById(R.id.tv_collection_detail);
        btn_ticket = findViewById(R.id.btn_ticket);

        btn_ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://naturelabsmall.com/order_detail.php?CD=&product_code=10000350"));
                startActivity(myIntent);
            }
        });

        intent = getIntent();

        id = intent.getIntExtra("id", 0);
        img_path = intent.getStringExtra("img_path");
        title = intent.getStringExtra("title");
        description = intent.getStringExtra("description");

        String imgUrl = ApplicationConstants.AWS_URL + img_path;
        Glide.with(this).load(imgUrl).into(img_collection);
        img_collection.setColorFilter(Color.parseColor("#BDBDBD"), PorterDuff.Mode.MULTIPLY);
        tv_title.setText(title);
        tv_description.setText(description);

        rv_collection = findViewById(R.id.rv_collection);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        rv_collection.setLayoutManager(gridLayoutManager);

        new GetWorksInExhibitionTask(new AsyncResponse<NftWorkObj[]>() {
            @Override
            public void onAsyncSuccess(NftWorkObj[] result) {
                ArrayList<NftWorkObj> nftWorkObjs = ArrayHelper.toArrayList(result);

                collectionNftRecyclerAdapter = new CollectionNftRecyclerAdapter(context, MainActivity.klaytnAddress);
                rv_collection.setAdapter(collectionNftRecyclerAdapter);
                collectionNftRecyclerAdapter.setTopArtList(nftWorkObjs);
            }
        }).execute(id);
    }
}