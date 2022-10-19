package com.nftime.app.OnBoardingFragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.klipwallet.app2app.api.Klip;
import com.nftime.app.MainActivity;
import com.nftime.app.OnBoardingActivity;
import com.nftime.app.R;
import com.nftime.app.actions.KlipAction;

public class OnBoardingFragment3 extends Fragment {
    private ViewGroup viewGroup;
    private TextView tvOnboardingSummary;
    private TextView tvOnboardingTitle;
    private TextView tvOnboardingDetail;
    private ImageView imageOnBoarding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_on_boarding3, container, false);

        imageOnBoarding = viewGroup.findViewById(R.id.img_klip_onboarding);

        tvOnboardingSummary = viewGroup.findViewById(R.id.tv_onboarding3_summary);
        tvOnboardingTitle = viewGroup.findViewById(R.id.tv_onboarding3_title);
        tvOnboardingDetail = viewGroup.findViewById(R.id.tv_onboarding3_detail);

        tvOnboardingSummary.setText("NFT Wallpaper");
        tvOnboardingTitle.setText("Enjoy your ART NFT\n" + "anytime, anywhere");
        tvOnboardingDetail.setText("Set your NFT as your smartphone,\n" + "tablet and laptop wallpaper!");

        tvOnboardingSummary.bringToFront();
        tvOnboardingTitle.bringToFront();
        tvOnboardingDetail.bringToFront();
        imageOnBoarding.bringToFront();

        return viewGroup;
    }

}