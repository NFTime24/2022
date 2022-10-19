package com.nftime.app.FragmentTopArts;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nftime.app.Adapter.TopNftRecyclerAdapter;
import com.nftime.app.R;
import com.nftime.app.objects.NftWorkObj;
import com.nftime.app.util.ArrayHelper;
import com.nftime.app.util.asyncTasks.AsyncResponse;
import com.nftime.app.util.asyncTasks.GetTopWorksWithCategoryTask;

import java.util.ArrayList;

public class FragmentCategoryTopWorks extends Fragment {
    ViewGroup viewGroup;
    RecyclerView digitalRV;
    TopNftRecyclerAdapter topNftRecyclerAdapter;

    String category;

    public FragmentCategoryTopWorks(String category){
        this.category = category;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_1, container, false);
        digitalRV = viewGroup.findViewById(R.id.digitalRV);

        // Layout Setting
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        digitalRV.setLayoutManager(gridLayoutManager);

        new GetTopWorksWithCategoryTask(new AsyncResponse<NftWorkObj[]>() {
            @Override
            public void onAsyncSuccess(NftWorkObj[] result) {
                ArrayList<NftWorkObj> nftWorkObjs = ArrayHelper.toArrayList(result);

                topNftRecyclerAdapter = new TopNftRecyclerAdapter(getContext());
                digitalRV.setAdapter(topNftRecyclerAdapter);
                topNftRecyclerAdapter.setTopArtList(nftWorkObjs);
            }
        }).execute(category);

        return viewGroup;
    }

    @Override
    public void onResume() {
        super.onResume();

        new GetTopWorksWithCategoryTask(new AsyncResponse<NftWorkObj[]>() {
            @Override
            public void onAsyncSuccess(NftWorkObj[] result) {
                ArrayList<NftWorkObj> nftWorkObjs = ArrayHelper.toArrayList(result);

                topNftRecyclerAdapter = new TopNftRecyclerAdapter(getContext());
                digitalRV.setAdapter(topNftRecyclerAdapter);
                topNftRecyclerAdapter.setTopArtList(nftWorkObjs);
            }
        }).execute(category);
    }
}