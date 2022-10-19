package com.nftime.app.OnBoardingFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nftime.app.R;

public class OnBoardingFragment2 extends Fragment {
    private ViewGroup viewGroup;
    private TextView tvOnboardingSummary;
    private TextView tvOnboardingTitle;
    private TextView tvOnboardingDetail;
    private ImageView imgKlipOnboarding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_on_boarding2, container, false);

        imgKlipOnboarding = viewGroup.findViewById(R.id.img_klip_onboarding);
        tvOnboardingSummary = viewGroup.findViewById(R.id.tv_onboarding2_summary);
        tvOnboardingTitle = viewGroup.findViewById(R.id.tv_onboarding2_title);
        tvOnboardingDetail = viewGroup.findViewById(R.id.tv_onboarding2_detail);

        tvOnboardingSummary.setText("Easy Payment");
        tvOnboardingTitle.setText("Easy NFT purchase\n" + "with mobile phone");
        tvOnboardingDetail.setText("Buy NFT in leagl currency\n" + "without crypto!");

        tvOnboardingSummary.bringToFront();
        tvOnboardingTitle.bringToFront();
        tvOnboardingDetail.bringToFront();
        imgKlipOnboarding.bringToFront();

        return viewGroup;
    }
}