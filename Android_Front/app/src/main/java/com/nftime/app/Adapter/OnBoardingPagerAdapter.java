package com.nftime.app.Adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.nftime.app.OnBoardingFragment.OnBoardingFragment1;
import com.nftime.app.OnBoardingFragment.OnBoardingFragment2;
import com.nftime.app.OnBoardingFragment.OnBoardingFragment3;
import com.nftime.app.OnBoardingFragment.OnBoardingFragment4;

public class OnBoardingPagerAdapter extends FragmentStateAdapter {
    private Context context;

    // Real Fragment Total Count
    private int mSetItemCount = 4; //프래그먼트 갯수 지정

    public OnBoardingPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);

        this.context = fragmentActivity;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch(position) {
            case 0 : { return new OnBoardingFragment1(); }
            case 1 : { return new OnBoardingFragment2(); }
            case 2 : { return new OnBoardingFragment3(); }
            case 3 : { return new OnBoardingFragment4(context); }
            default : { return new OnBoardingFragment1(); }
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