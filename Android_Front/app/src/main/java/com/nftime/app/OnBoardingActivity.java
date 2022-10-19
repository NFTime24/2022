package com.nftime.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.klipwallet.app2app.api.Klip;
import com.klipwallet.app2app.api.KlipCallback;
import com.klipwallet.app2app.api.response.KlipErrorResponse;
import com.klipwallet.app2app.api.response.KlipResponse;
import com.klipwallet.app2app.api.response.model.KlipResult;
import com.nftime.app.Adapter.OnBoardingPagerAdapter;
import com.nftime.app.actions.KlipAction;
/*import com.nftime.app.util.LoginCallback;*/
import com.nftime.app.objects.UserObj;
import com.nftime.app.util.asyncTasks.AsyncResponse;
import com.nftime.app.util.asyncTasks.CreateUserTask;
import com.nftime.app.util.asyncTasks.GetOwnedNftIdsTask;
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator;

import java.util.HashMap;

public class OnBoardingActivity extends AppCompatActivity {
    private Context context;;
    private KlipAction klipAction;
    private ViewPager2 ViewPagerOnBoardingPager;
    private OnBoardingPagerAdapter onBoardingPagerAdapter;
    private WormDotsIndicator wormDotsIndicator;
    private ImageView icon_nftime;
    private TextView tv_nftime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding);
        ViewPagerOnBoardingPager = findViewById(R.id.vp2_onboarding);
        wormDotsIndicator = findViewById(R.id.worm_dot);
        icon_nftime = findViewById(R.id.icon_nftime);
        tv_nftime = findViewById(R.id.tv_nftime);

        context = this;

        klipAction = new KlipAction(context, Klip.getInstance(this));

        MainActivity.ownedNftIds2WorkIds = new HashMap<>();
        MainActivity.ownedWorkIds = new HashMap<>();
    }

    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences pref = context.getSharedPreferences(String.valueOf(R.string.preference_file_key), Context.MODE_PRIVATE);

        boolean isSigned = pref.getBoolean("IsSigned", false);
        String loginKey = pref.getString("LoginKey", "");

        if(isSigned && !loginKey.equals("")){
            ViewPagerOnBoardingPager.setVisibility(View.INVISIBLE);
            wormDotsIndicator.setVisibility(View.INVISIBLE);
            icon_nftime.setVisibility(View.VISIBLE);
            tv_nftime.setVisibility(View.VISIBLE);

            klipAction.getResult(loginKey, new KlipCallback<KlipResponse>() {
                @Override
                public void onSuccess(KlipResponse result) {
                    KlipResult res = result.getResult();
                    MainActivity.klaytnAddress = res.getKlaytnAddress();
                    if(MainActivity.klaytnAddress != null){
                        new CreateUserTask(new AsyncResponse<UserObj>() {
                            @Override
                            public void onAsyncSuccess(UserObj result) {
                                if(result != null)
                                    MainActivity.myUserInfo = result;

                                new GetOwnedNftIdsTask(new AsyncResponse<Void>() {
                                    @Override
                                    public void onAsyncSuccess(Void result) {
                                        Intent intent = new Intent(context, MainActivity.class);
                                        startActivity(intent);

                                        finish();
                                    }
                                }).execute();
                            }
                        }).execute(MainActivity.klaytnAddress);
                    }
                }

                @Override
                public void onFail(KlipErrorResponse error) {
                    error.getException();
                }
            });
        }
        else {
            ViewPagerOnBoardingPager.setVisibility(View.VISIBLE);
            wormDotsIndicator.setVisibility(View.VISIBLE);
            icon_nftime.setVisibility(View.INVISIBLE);
            tv_nftime.setVisibility(View.INVISIBLE);

            onBoardingPagerAdapter = new OnBoardingPagerAdapter(this);
            ViewPagerOnBoardingPager.setAdapter(onBoardingPagerAdapter);

            wormDotsIndicator.setViewPager2(ViewPagerOnBoardingPager);
            wormDotsIndicator.bringToFront();

            /*if (checkOnBoardingState()) {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                // 요거는 뒤로가기 했을 때 온보딩 페이지로 다시 못넘어오게 하려고 쓴건데, 수정 중이라 주석처리 해놓을게여
                // finish();
            }*/
        }
    }

    // OnBoardingFragment3에서 Klip 로그인 버튼 누르면 onBoardingFinished()에서 온보딩 페이지 다봤다고 true로 바꿔줘요.
    // 그런데 맨 처음에는 안봤으니까 default 값으로 false해서 보여주게 됩니당!
    protected boolean checkOnBoardingState() {
        SharedPreferences onBoardingResult = getSharedPreferences("onBoardingResult", MODE_PRIVATE);
        return onBoardingResult.getBoolean("checked", false);
    }
}