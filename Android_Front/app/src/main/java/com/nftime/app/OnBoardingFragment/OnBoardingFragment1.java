package com.nftime.app.OnBoardingFragment;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nftime.app.MainActivity;
import com.nftime.app.R;

public class OnBoardingFragment1 extends Fragment {
    private ViewGroup viewGroup;
    private TextView tvOnboardingSummary;
    private TextView tvOnboardingTitle;
    private TextView tvOnboardingDetail;
    private ImageView imgKlipOnboarding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_on_boarding1, container, false);

        imgKlipOnboarding = viewGroup.findViewById(R.id.img_klip_onboarding);
        tvOnboardingSummary = viewGroup.findViewById(R.id.tv_onboarding1_summary);
        tvOnboardingTitle = viewGroup.findViewById(R.id.tv_onboarding1_title);
        tvOnboardingDetail = viewGroup.findViewById(R.id.tv_onboarding1_detail);

        tvOnboardingSummary.setText("Klip Wallet");
        tvOnboardingTitle.setText("Convenient Service\n" + "through KaKao Klip");
        tvOnboardingDetail.setText("CryptoWallet Sing up is no longer required\n" + "All you need is just KaKaoTalk!");

        tvOnboardingSummary.bringToFront();
        tvOnboardingTitle.bringToFront();
        tvOnboardingDetail.bringToFront();
        imgKlipOnboarding.bringToFront();

        return viewGroup;
    }
}