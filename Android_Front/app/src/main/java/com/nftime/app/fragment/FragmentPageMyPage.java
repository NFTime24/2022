package com.nftime.app.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.nftime.app.OnBoardingActivity;
import com.nftime.app.R;
import com.nftime.app.Adapter.MyNftRecyclerAdapter;
import com.nftime.app.MainActivity;
import com.nftime.app.dialog.MyWalletDlgFragment;
import com.nftime.app.objects.NftWorkObj;
import com.nftime.app.objects.UserObj;
import com.nftime.app.util.ApplicationConstants;
import com.nftime.app.util.asyncTasks.AsyncResponse;
import com.nftime.app.util.asyncTasks.GetOwnedNftIdsTask;
import com.nftime.app.util.asyncTasks.GetOwnedWorksTask;
import com.nftime.app.util.asyncTasks.GetUserTask;

import java.util.ArrayList;

public class FragmentPageMyPage extends Fragment {
    private Context context;

    ViewGroup viewGroup;

    ImageView profilePicture;
    TextView profileName;
    Button btnMyWallet;
    ConstraintLayout btnLogout;

    public FragmentPageMyPage(Context context){
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("Login", "MyPage onCreateView");

        setHasOptionsMenu(true);
        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_page_mypage, container, false);

        Log.d("test", "FragmentPageMyPage" + "onCreateView");
        return viewGroup;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("Login", "MyPage onViewCreated");

        profilePicture = view.findViewById(R.id.mypage_profile_picture);
        profileName = view.findViewById(R.id.mypage_user_name);
        btnMyWallet = view.findViewById(R.id.btn_mywallet);
        btnLogout = view.findViewById(R.id.btn_logout);

        btnMyWallet.setOnClickListener(onMywalletClickListener);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = context.getSharedPreferences(String.valueOf(R.string.preference_file_key), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putBoolean("IsSigned", false);
                editor.putString("LoginKey", "");
                editor.apply();

                Intent intent = new Intent(context, OnBoardingActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        Log.d("test", "FragmentPageMyPage" + "onViewCreated");
    }

    // OnStart는 화면이 Background로 갔다가 다시 돌아올 때도 실행됩니다.
    // Klip에서 인증정보를 받고 돌아왔을 때, 한번 더 동작시키기 위해서 사용.
    @Override
    public void onStart() {
        super.onStart();
        Log.d("Login", "MyPage onStart");

        loginSetting();
    }

    private final View.OnClickListener onMywalletClickListener = v -> {
        MyWalletDlgFragment myWalletDlgFragment = MyWalletDlgFragment.getInstance();

        //klaytn 주소 전달
        Bundle bundle = new Bundle();
        bundle.putString("address", MainActivity.klaytnAddress);
        myWalletDlgFragment.setArguments(bundle);

        myWalletDlgFragment.show(getActivity().getSupportFragmentManager(), MyWalletDlgFragment.TAG_WALLET);
    };

    private void loginSetting() {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        try {
            final BitMatrix bitMatrix = qrCodeWriter.encode(MainActivity.klaytnAddress, BarcodeFormat.QR_CODE, 246,246);

            Bitmap bitmap = Bitmap.createBitmap(bitMatrix.getWidth(), bitMatrix.getHeight(), Bitmap.Config.ARGB_8888);
            for (int y = 0; y < bitMatrix.getHeight(); y++) {
                for (int x = 0; x < bitMatrix.getWidth(); x++) {
                    if (bitMatrix.get(x, y)) {
                        bitmap.setPixel(x, y, Color.BLACK);
                    }
                }
            }

        } catch (WriterException e) {
            e.printStackTrace();
        }

        Glide.with(context)
                .load(ApplicationConstants.AWS_URL + MainActivity.myUserInfo.path)
                .centerCrop()
                .placeholder(R.drawable.mypage_profile_picture)
                .into(profilePicture);
        profileName.setText(MainActivity.myUserInfo.nickname);
    }
}

