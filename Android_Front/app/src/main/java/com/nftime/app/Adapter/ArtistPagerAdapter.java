package com.nftime.app.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.nftime.app.FragmentTopArts.FragmentArtistWorks;
import com.nftime.app.FragmentTopArts.FragmentCategoryTopWorks;
import com.nftime.app.fragment.FragmentFanTalk;
import com.nftime.app.objects.ArtistObj;

public class ArtistPagerAdapter extends FragmentStateAdapter {
    // Real Fragment Total Count
    private int mSetItemCount = 2; //프래그먼트 갯수 지정
    private ArtistObj artistObj;

    public ArtistPagerAdapter(@NonNull FragmentActivity fragmentActivity, ArtistObj artistObj) {
        super(fragmentActivity);

        this.artistObj = artistObj;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch(position) {
            case 0 : { return new FragmentArtistWorks(artistObj.id); }
            case 1 : { return new FragmentFanTalk(artistObj); }
            default : { return new FragmentArtistWorks(artistObj.id); }
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
