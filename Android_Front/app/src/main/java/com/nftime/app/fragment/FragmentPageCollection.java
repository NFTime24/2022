package com.nftime.app.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.nftime.app.Adapter.FamousArtistRecyclerAdapter;
import com.nftime.app.Adapter.TopNftRecyclerAdapter;
import com.nftime.app.AllWorksActivity;
import com.nftime.app.R;
import com.nftime.app.Adapter.CollectionRecyclerAdapter;
import com.nftime.app.Adapter.TopArtsPagerAdapter;
import com.nftime.app.RecyclerItem.FamousArtistItem;
import com.nftime.app.objects.ArtistObj;
import com.nftime.app.objects.NftWorkObj;
import com.nftime.app.util.ArrayHelper;
import com.nftime.app.util.asyncTasks.AsyncResponse;
import com.nftime.app.util.asyncTasks.GetNewestWorksTask;
import com.nftime.app.util.asyncTasks.GetTopArtistsTask;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FragmentPageCollection extends Fragment {
    private Context context;
    private ViewGroup viewGroup;
    private ViewPager2 viewPager2Collection;
    private CollectionRecyclerAdapter collectionRecyclerAdapter;

    private ViewPager2 viewPager2;
    private TabLayout tabLayout;

    // 최신 아트
    private RecyclerView RVtheNewestArt;
    private TopNftRecyclerAdapter topNftRecyclerAdapter;

    // 작품 둘러보기
    private TextView AllCollectionDetailMove;

    // 인기 작가
    private RecyclerView RVFamousArtist;
    private FamousArtistRecyclerAdapter famousArtistRecyclerAdapter;
    ArrayList famousArtistItems = new ArrayList();

    // UX Writing Setting
    private TextView NewestArt;
    private TextView NewestArtDetail;
    private TextView AllCollection;
    private TextView AllCollectionDetail;
    private TextView CreatorRanking;
    private TextView CreatorRankingDetail;

    public FragmentPageCollection(Context context){
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_page_collection, container, false);

        NewestArt = viewGroup.findViewById(R.id.tv_the_newest_art);
        NewestArtDetail = viewGroup.findViewById(R.id.tv_the_newest_art_detail);

        AllCollection = viewGroup.findViewById(R.id.tv_all_collection);
        AllCollectionDetail = viewGroup.findViewById(R.id.tv_all_collection_detail);
        AllCollectionDetailMove = viewGroup.findViewById(R.id.tv_all_collection_detail_move);

        CreatorRanking = viewGroup.findViewById(R.id.tv_creator_ranking);
        CreatorRankingDetail = viewGroup.findViewById(R.id.tv_creator_ranking_detail);

        tabLayout = viewGroup.findViewById(R.id.tabLayout);
        viewPager2 = viewGroup.findViewById(R.id.viewPager2);

        RVtheNewestArt = viewGroup.findViewById(R.id.rv_the_newest_art);
        RVFamousArtist = viewGroup.findViewById(R.id.rv_creator_ranking);

/*        viewPager2Collection = viewGroup.findViewById(R.id.viewPager2_Collection);*/

        // 한영 텍스트
        NewestArt.setText("The Newest ART");
        NewestArtDetail.setText("Let’s meet the newly released work");

        AllCollection.setText("Browse the Works");
        AllCollectionDetail.setText("Meet various works now!");

        CreatorRanking.setText("Popular Creators Ranking");
        CreatorRankingDetail.setText("Discover the works of popular artists");

        // 최신 아트 리사이클러 뷰
        LinearLayoutManager gridLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL,false);
        RVtheNewestArt.setLayoutManager(gridLayoutManager);

        new GetNewestWorksTask(new AsyncResponse<NftWorkObj[]>() {
            @Override
            public void onAsyncSuccess(NftWorkObj[] result) {
                ArrayList<NftWorkObj> nftWorkObjs = ArrayHelper.toArrayList(result);

                topNftRecyclerAdapter = new TopNftRecyclerAdapter(getContext());
                RVtheNewestArt.setAdapter(topNftRecyclerAdapter);
                topNftRecyclerAdapter.setTopArtList(nftWorkObjs);
            }
        }).execute();

        // 인기 작가 리사이클러 뷰
        famousArtistRecyclerAdapter = new FamousArtistRecyclerAdapter(getContext());

        RVFamousArtist.setAdapter(famousArtistRecyclerAdapter);
        RVFamousArtist.setLayoutManager(new LinearLayoutManager(getActivity()));

        new GetTopArtistsTask(new AsyncResponse<ArtistObj[]>() {
            @Override
            public void onAsyncSuccess(ArtistObj[] result) {
                ArrayList<ArtistObj> artistObjs = ArrayHelper.toArrayList(result);
                famousArtistRecyclerAdapter.setFamousArtistItems(artistObjs);
            }
        }).execute();

        // 전시회
/*        viewPager2Collection.setOffscreenPageLimit(4);
        viewPager2Collection.setClipToPadding(false);
        viewPager2Collection.setClipChildren(false);
        viewPager2Collection.setPadding(80, 0, 80, 0);

        new GetExhibitionsTask(new AsyncResponse<ExhibitionObj[]>() {
            @Override
            public void onAsyncSuccess(ExhibitionObj[] result) {
                ArrayList<ExhibitionObj> exhibitionObjs = new ArrayList<>();

                HashMap<Integer, ExhibitionObj> exhibitionObjHashMap = new HashMap<>();
                for(ExhibitionObj d : result){
                    exhibitionObjHashMap.put(d.exhibition_id, d);
                }

                exhibitionObjs.add(exhibitionObjHashMap.get(6));
                exhibitionObjs.add(exhibitionObjHashMap.get(4));
                exhibitionObjs.add(exhibitionObjHashMap.get(5));
                exhibitionObjs.add(exhibitionObjHashMap.get(7));

                collectionRecyclerAdapter = new CollectionRecyclerAdapter();
                collectionRecyclerAdapter.setCollectionItems(exhibitionObjs);
                viewPager2Collection.setOrientation(viewPager2Collection.ORIENTATION_HORIZONTAL);
                viewPager2Collection.setAdapter(collectionRecyclerAdapter);
            }
        }).execute();*/

        AllCollectionDetailMove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AllWorksActivity.class);
                v.getContext().startActivity(intent);
            }
        });

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
}