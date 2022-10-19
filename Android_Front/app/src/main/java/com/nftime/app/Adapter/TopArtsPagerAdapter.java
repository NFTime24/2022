package com.nftime.app.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.nftime.app.FragmentTopArts.FragmentCategoryTopWorks;

public class TopArtsPagerAdapter extends FragmentStateAdapter { // 뷰페이저2에서는 FragmentStateAdapter를 사용한다.
    // Real Fragment Total Count
    private int mSetItemCount = 4; //프래그먼트 갯수 지정

    public TopArtsPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch(position) {
            case 0 : { return new FragmentCategoryTopWorks("digital"); }
            case 1 : { return new FragmentCategoryTopWorks("drawing"); }
            case 2 : { return new FragmentCategoryTopWorks("illust"); }
            case 3 : { return new FragmentCategoryTopWorks("fineart"); }
            default : { return new FragmentCategoryTopWorks("all"); }
        }
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        return mSetItemCount;
    }
}
