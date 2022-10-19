package com.nftime.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.nftime.app.R;

//이거는 QR 스캔하는 activity
public class QrActivity extends AppCompatActivity {

    private IntentIntegrator qrScan;
    private TextView klipAddressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);

        klipAddressView = findViewById(R.id.klipAddressView);

        qrScan = new IntentIntegrator(this);
        qrScan.setOrientationLocked(false); // default는 세로모드/휴대폰 방향에 따라 가로, 세로 자동 변경
        qrScan.setPrompt("NFT를 minting할 사용자의 Klip 지갑을 스캔해주세요\n"); //qr 스캔 화면에 뜨는 문구
        qrScan.initiateScan();

        //qr scan 버튼
        Button qrButton = (Button) findViewById(R.id.scanQR);
        qrButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), QrActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);

        if(result != null) {
            if(result.getContents() == null) {
                klipAddressView.setText("올바르지 않은 주소입니다.");
            } else {
                //result.getContents()에 klip 주소 담김
                klipAddressView.setText("Klip 주소: " + result.getContents());
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
