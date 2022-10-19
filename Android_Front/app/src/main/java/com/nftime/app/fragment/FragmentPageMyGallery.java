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

public class FragmentPageMyGallery extends Fragment {
    private Context context;

    ViewGroup viewGroup;

    ImageView profilePicture;
    TextView profileName;
    ImageView btnReload;
    TextView txMyGallery;
    TextView txMyGalleryDetail;

    // recyclerView
    MyNftRecyclerAdapter myNFTRecyclerAdapter;
    RecyclerView rvMyNft;

    // Dialog Window

    public FragmentPageMyGallery(Context context){
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("Login", "MyPage onCreateView");

        setHasOptionsMenu(true);
        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_page_my_gallery, container, false);
        rvMyNft = viewGroup.findViewById(R.id.rv_mynft);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        rvMyNft.setLayoutManager(gridLayoutManager);

        Log.d("test", "FragmentPageMyPage" + "onCreateView");
        return viewGroup;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("Login", "MyPage onViewCreated");

        profilePicture = view.findViewById(R.id.mypage_profile_picture);
        profileName = view.findViewById(R.id.mypage_user_name);
        btnReload = view.findViewById(R.id.btn_reload);
        txMyGallery = view.findViewById(R.id.mypage_mygallery);
        /*        txMyGalleryDetail = view.findViewById(R.id.mypage_mygallery_detail);*/

        btnReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnReload.setEnabled(false);
                Toast Reload = Toast.makeText(context, "Updating NFT. Please wait a moment.", Toast.LENGTH_SHORT);
                Reload.show();
                myNFTRecyclerAdapter = new MyNftRecyclerAdapter(context, MainActivity.klaytnAddress);
                myNFTRecyclerAdapter.setTopArtList(new ArrayList<NftWorkObj>());
                rvMyNft.setAdapter(myNFTRecyclerAdapter);
                if(MainActivity.klaytnAddress != null){
                    new GetOwnedNftIdsTask(new AsyncResponse<Void>() {
                        @Override
                        public void onAsyncSuccess(Void result) {
                            new GetOwnedWorksTask(new AsyncResponse<ArrayList<NftWorkObj>>() {
                                @Override
                                public void onAsyncSuccess(ArrayList<NftWorkObj> result) {
                                    myNFTRecyclerAdapter.setTopArtList(result);
                                    rvMyNft.setAdapter(myNFTRecyclerAdapter);
                                    btnReload.setEnabled(true);
                                }
                            }).execute();
                        }
                    }).execute();
                }
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

        new GetUserTask(new AsyncResponse<UserObj>() {
            @Override
            public void onAsyncSuccess(UserObj result) {
                if(result != null){
                    Glide.with(context)
                            .load(ApplicationConstants.AWS_URL + result.path)
                            .centerCrop()
                            .placeholder(R.drawable.mypage_profile_picture)
                            .into(profilePicture);
                    profileName.setText(result.nickname);
                }
            }
        }).execute(MainActivity.klaytnAddress);

        new GetOwnedWorksTask(new AsyncResponse<ArrayList<NftWorkObj>>() {
            @Override
            public void onAsyncSuccess(ArrayList<NftWorkObj> result) {
                myNFTRecyclerAdapter = new MyNftRecyclerAdapter(context, MainActivity.klaytnAddress);
                myNFTRecyclerAdapter.setTopArtList(result);
                rvMyNft.setAdapter(myNFTRecyclerAdapter);
            }
        }).execute();
    }
}

