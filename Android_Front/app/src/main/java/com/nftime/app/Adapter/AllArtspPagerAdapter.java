package com.nftime.app.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.nftime.app.FragmentTopArts.FragmentCategoryTopWorks;

public class AllArtspPagerAdapter extends FragmentStateAdapter {
    private int mSetItemCount = 4;

    public AllArtspPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
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
