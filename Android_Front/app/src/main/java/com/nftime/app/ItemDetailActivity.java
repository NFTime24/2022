package com.nftime.app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.nftime.app.R;

public class ItemDetailActivity extends AppCompatActivity {
    private Intent intent;
    private int resourceId;
    private String title;
    private String name;
    private int price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        ImageView img_art = findViewById(R.id.img_art);
        TextView tv_name = findViewById(R.id.tv_name);
        TextView tv_title = findViewById(R.id.tv_title);
        TextView tv_creator = findViewById(R.id.tv_creator);
        TextView tv_price = findViewById(R.id.tv_price);

        intent = getIntent();
        resourceId = intent.getIntExtra("img", 0);
        title = intent.getStringExtra("title");
        name = intent.getStringExtra("name");
        price = intent.getIntExtra("price",0);

        img_art.setImageResource(resourceId);
        Glide.with(this).load(resourceId).into(img_art);
        tv_title.setText(title);
        tv_name.setText(name);
        tv_creator.setText(name);
        tv_price.setText(Integer.toString(price));
    }
}