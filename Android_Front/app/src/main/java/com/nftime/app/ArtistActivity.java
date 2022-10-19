package com.nftime.app;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.nftime.app.Adapter.ArtistPagerAdapter;
import com.nftime.app.Adapter.TopArtsPagerAdapter;
import com.nftime.app.objects.ArtistObj;
import com.nftime.app.objects.NftWorkObj;
import com.nftime.app.util.ApplicationConstants;

import java.util.Arrays;
import java.util.List;

public class ArtistActivity extends AppCompatActivity {
    private Context context;

    private ImageView iv_artist_profile;
    private TextView tv_artist_name;

    private ViewPager2 viewPager2;
    private TabLayout tabLayout;

    private ArtistObj artistObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist);

        context = this;

        iv_artist_profile = findViewById(R.id.artsit_profile);
        tv_artist_name = findViewById(R.id.artist_name);

        viewPager2 = findViewById(R.id.viewPager2);
        tabLayout = findViewById(R.id.tabLayout);

        Intent intent = getIntent();

        artistObj = new ArtistObj(
                intent.getIntExtra("id", 0),
                intent.getIntExtra("profile_id", 0),
                intent.getStringExtra("name"),
                intent.getStringExtra("address"),
                intent.getStringExtra("path")
        );

        String imgUrl = ApplicationConstants.AWS_URL + artistObj.path;
        Glide.with(context)
                .load(imgUrl)
                .centerCrop()
                .placeholder(R.drawable.mypage_profile_picture)
                .into(iv_artist_profile);

        tv_artist_name.setText(artistObj.name);

        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#216FEB"));
        tabLayout.setTabTextColors(Color.parseColor("#000000"), Color.parseColor("#000000"));
        final List<String> tabElement = Arrays.asList("Gallery", "FanTalk");

        ArtistPagerAdapter SetupPagerAdapter = new ArtistPagerAdapter(this, artistObj); // stateAdapter 사용 시, 액티비티 -> this, 프래그먼트 -> getActivity()
        viewPager2.setAdapter(SetupPagerAdapter);

        new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                TextView tabText = new TextView(context);
                tabText.setText(tabElement.get(position));
                tabText.setGravity(Gravity.CENTER);
                tabText.setTextColor(Color.parseColor("#000000"));
                tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#216FEB"));
                Typeface typeface = getResources().getFont(R.font.pretendard_bold);
                tabText.setTypeface(typeface);
                tab.setCustomView(tabText);
            }
        }).attach();
    }
}