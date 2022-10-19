package com.nftime.app.OnBoardingFragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.klipwallet.app2app.api.Klip;
import com.nftime.app.R;
import com.nftime.app.actions.KlipAction;

public class OnBoardingFragment4 extends Fragment {
    private Context context;

    private ViewGroup viewGroup;
    private Button btn;
    private TextView tvOnboardingSummary;
    private TextView tvOnboardingTitle;
    private TextView tvOnboardingDetail;
    private ImageView imageOnBoarding;

    private KlipAction klipAction;

    public OnBoardingFragment4 (Context context) {
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_on_boarding4, container, false);

        btn = viewGroup.findViewById(R.id.login_button);
        imageOnBoarding = viewGroup.findViewById(R.id.img_klip_onboarding);

        tvOnboardingSummary = viewGroup.findViewById(R.id.tv_onboarding3_summary);
        tvOnboardingTitle = viewGroup.findViewById(R.id.tv_onboarding3_title);
        tvOnboardingDetail = viewGroup.findViewById(R.id.tv_onboarding3_detail);

        tvOnboardingSummary.setText("NFT Fandom");
        tvOnboardingTitle.setText("Communicate with\n" + "Your favorite artist");
        tvOnboardingDetail.setText("Buy NFT and enter the fandom.\n" + "You can particpate in offline exhibitions and fan meeting events!");
        btn.setText("Sing in with Klip");

        tvOnboardingSummary.bringToFront();
        tvOnboardingTitle.bringToFront();
        tvOnboardingDetail.bringToFront();
        imageOnBoarding.bringToFront();

        klipAction = new KlipAction(context, Klip.getInstance(context));

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //OnBoardingFinished();

                klipAction.loginWithKlip();
            }
        });

        return viewGroup;
    }

    protected void OnBoardingFinished() {
        SharedPreferences onBoardingResult = this.getActivity().getSharedPreferences("onBoardingResult", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = onBoardingResult.edit();
        editor.putBoolean("checked", true);
        editor.commit();
    }
}