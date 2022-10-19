package com.nftime.app.dialog;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.nftime.app.R;

public class MyWalletDlgFragment extends DialogFragment implements View.OnClickListener {
    public static final String TAG_WALLET = "wallet test";
    private TextView addressTV; //dialog 창에서 사용자 주소 보여줌
    private ImageView addressQRIV; //dialog 창에서 사용자 주소 qr로 보여줌
    private String mAddress; //사용자 주소 저장

    public MyWalletDlgFragment() {}

    public static MyWalletDlgFragment getInstance() {
        MyWalletDlgFragment myWalletDlgFragment = new MyWalletDlgFragment();
        return myWalletDlgFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dlg_mywallet, container);
        ImageView iconClose = v.findViewById(R.id.icon_close);
        iconClose.setOnClickListener(this);

        Bundle mBundle = getArguments();
        addressTV = v.findViewById(R.id.myWalletAddressTV);
        if(mBundle != null) {
            mAddress = mBundle.getString("address");;
            addressTV.setText("Wallet Address: " + mAddress); //주소 세팅
        }

        setCancelable(false); //외부 영역 클릭 시 dialog 창이 사라지지 않도록 (X버튼 눌렀을 때만 dialog 창 사라지도록)

        addressQRIV = v.findViewById(R.id.qrWallet);

        // 클레이튼 주소로 QR 만들어 줌!
        Log.d("test", "here?");
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        try {
            final BitMatrix bitMatrix = qrCodeWriter.encode(mAddress, BarcodeFormat.QR_CODE, 246,246);

            Bitmap bitmap = Bitmap.createBitmap(bitMatrix.getWidth(), bitMatrix.getHeight(), Bitmap.Config.ARGB_8888);
            for (int y = 0; y < bitMatrix.getHeight(); y++) {
                for (int x = 0; x < bitMatrix.getWidth(); x++) {
                    if (bitMatrix.get(x, y)) {
                        bitmap.setPixel(x, y, Color.BLACK);
                    }
                }
            }

            addressQRIV.setImageBitmap(bitmap); //QR image 세팅

        } catch (WriterException e) {
            e.printStackTrace();
        }

        return v;
    }

    @Override
    public void onClick(View v) { //X모양 버튼 클릭 시 실행되는 코드
        dismiss(); //창 사라짐
    }
}