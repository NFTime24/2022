package com.nftime.app.fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.nftime.app.Adapter.CollectionRecyclerAdapter;
import com.nftime.app.Adapter.ExhibitionPagerAdapter;
import com.nftime.app.Adapter.TodayArtRecyclerAdapter;
import com.nftime.app.Adapter.TopArtsPagerAdapter;
import com.nftime.app.R;
import com.nftime.app.MainActivity;
import com.nftime.app.RecyclerItem.ExhibitionItem;
import com.nftime.app.RecyclerItem.TodayArtItem;
import com.nftime.app.objects.ExhibitionObj;
import com.nftime.app.objects.NftWorkObj;
import com.nftime.app.util.ArrayHelper;
import com.nftime.app.util.asyncTasks.AsyncResponse;
import com.nftime.app.util.asyncTasks.GetExhibitionsTask;
import com.nftime.app.util.asyncTasks.GetTodayWorksTask;
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class FragmentPageHome extends Fragment {
    private Context context;
    private Activity activity;
    private ViewGroup viewGroup;
    private ViewPager2 ViewPager2TodayArt;
    private TodayArtRecyclerAdapter todayArtRecyclerAdapter;

    private WormDotsIndicator wormDotsIndicator;
    private ViewPager2 viewPager2;
    private TabLayout tabLayout;
    private ImageView nftimeLogo;
    private TextView todayArt;
    private TextView todayArtDetail;

    private ArrayList<NftWorkObj> TodayArtItems;

    private TextView topCollection;
    private TextView topCollectionDetail;

    private Integer pagerTime;
    private Integer pagerLength;
    private Integer pagerIndex;
    private Boolean isHidden;

    public FragmentPageHome(Context context){
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_page_home, container, false);

        activity = ((Activity) context);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int flags = activity.getWindow().getDecorView().getSystemUiVisibility(); // get current flag
            flags = flags ^ View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR; // use XOR here for remove LIGHT_STATUS_BAR from flags
            activity.getWindow().getDecorView().setSystemUiVisibility(flags);
        }

        tabLayout = viewGroup.findViewById(R.id.tabLayout);
        viewPager2 = viewGroup.findViewById(R.id.viewPager2);

        nftimeLogo = viewGroup.findViewById(R.id.icon_nftime_white);
        todayArt = viewGroup.findViewById(R.id.tv_today_art);
        todayArtDetail = viewGroup.findViewById(R.id.tv_today_art_detail);

        wormDotsIndicator = viewGroup.findViewById(R.id.worm_dot);

        topCollection = viewGroup.findViewById(R.id.tv_top_collection);
        topCollectionDetail = viewGroup.findViewById(R.id.tv_top_collection_detail);

        // 한영 텍스트
        todayArt.setText("Today's ART");
        todayArtDetail.setText("Everyday New Art Works");

        topCollection.setText("Top 10 Works");
        topCollectionDetail.setText("Meet the popular works now!");

        nftimeLogo.bringToFront();
        todayArt.bringToFront();
        todayArtDetail.bringToFront();

        // 1. todayArt ViewPager2
        ViewPager2TodayArt = viewGroup.findViewById(R.id.viewPager2_today_art);

        new GetTodayWorksTask(new AsyncResponse<NftWorkObj[]>() {
            @Override
            public void onAsyncSuccess(NftWorkObj[] result) {
                TodayArtItems = ArrayHelper.toArrayList(result);
                todayArtRecyclerAdapter = new TodayArtRecyclerAdapter(context);
                todayArtRecyclerAdapter.setTodayArtItems(TodayArtItems);

                ViewPager2TodayArt.setOrientation(ViewPager2TodayArt.ORIENTATION_HORIZONTAL);
                ViewPager2TodayArt.setAdapter(todayArtRecyclerAdapter);

                isHidden = false;
                pagerIndex = ViewPager2TodayArt.getCurrentItem();
                pagerTime = 0;
                pagerLength = TodayArtItems.size();
                pagerControlLoop();

                wormDotsIndicator.setViewPager2(ViewPager2TodayArt);
                wormDotsIndicator.bringToFront();
            }
        }).execute();

        // Top 10 ART

        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#D3D3D3"));
        tabLayout.setTabTextColors(Color.parseColor("#ffffff"), Color.parseColor("#ffffff"));
        final List<String> tabElement = Arrays.asList("Digital", "Drawing", "illust", "Fine Art");

        TopArtsPagerAdapter SetupPagerAdapter = new TopArtsPagerAdapter(getActivity()); // stateAdapter 사용 시, 액티비티 -> this, 프래그먼트 -> getActivity()
        viewPager2.setAdapter(SetupPagerAdapter);

        new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                TextView tabText = new TextView(getActivity());
                tabText.setText(tabElement.get(position));
                tabText.setGravity(Gravity.CENTER);
                tabText.setTextColor(Color.parseColor("#000000"));
                Typeface typeface = getResources().getFont(R.font.pretendard_regular);
                tabText.setTypeface(typeface);
                tab.setCustomView(tabText);
            }
        }).attach();

        // Inflate the layout for this fragment

        return viewGroup;
    }

    private void pagerControlLoop(){
        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(pagerTime == 3){
                    pagerTime = 0;
                    if(pagerIndex == ViewPager2TodayArt.getCurrentItem()){
                        if(pagerIndex == pagerLength - 1)
                            ViewPager2TodayArt.setCurrentItem(0);
                        else
                            ViewPager2TodayArt.setCurrentItem(ViewPager2TodayArt.getCurrentItem() + 1);
                    }
                }
                else {
                    pagerTime += 1;
                    if(pagerIndex != ViewPager2TodayArt.getCurrentItem()){
                        pagerIndex = ViewPager2TodayArt.getCurrentItem();
                        pagerTime = 0;
                    }
                }
                if(!isHidden)
                    pagerControlLoop();
            }
        }, 1000);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

        isHidden = hidden;

        if(hidden){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                int flags = activity.getWindow().getDecorView().getSystemUiVisibility(); // get current flag
                flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;   // add LIGHT_STATUS_BAR to flag
                activity.getWindow().getDecorView().setSystemUiVisibility(flags);
            }
        }
        else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                int flags = activity.getWindow().getDecorView().getSystemUiVisibility(); // get current flag
                flags = flags ^ View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR; // use XOR here for remove LIGHT_STATUS_BAR from flags
                activity.getWindow().getDecorView().setSystemUiVisibility(flags);
            }

            pagerControlLoop();
        }
    }
}
