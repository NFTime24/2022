package com.nftime.app;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.klipwallet.app2app.api.Klip;
import com.klipwallet.app2app.api.KlipCallback;
import com.klipwallet.app2app.api.response.CardListResponse;
import com.klipwallet.app2app.api.response.KlipErrorResponse;
import com.klipwallet.app2app.api.response.KlipResponse;
import com.klipwallet.app2app.api.response.model.KlipResult;
import com.nftime.app.R;
import com.nftime.app.actions.KlipAction;
import com.nftime.app.util.JsonHelper;

//NFT 발급할 직원이 Klip 계정으로 로그인할 때 실행되는 activity
public class LoginActivity extends FragmentActivity {
    private Context ctx;

    private String[] list = {
            "prepare (Link)",
            "request",
            "getResult",
    };

    private ListView listView;
    private TextView resView;

    private String requestKey;
    private String userAddress;
    private String userCardId;

    private KlipAction klipAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ctx = this;

        resView = findViewById(R.id.resInfo);
        listView = findViewById(R.id.listView);
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                list
        );

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(OnItemClickListener);

        klipAction = new KlipAction(ctx, Klip.getInstance(ctx));
    }

    private AdapterView.OnItemClickListener OnItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String name = (String)parent.getItemAtPosition(position);

            switch (name) {
                case "request":
                    klipAction.prepareLink(klipCallback);
                    klipAction.request(requestKey);
                    break;
                case "getResult":
                    klipAction.getResult(requestKey, klipCallback);
                    break;
                default:
                    break;
            }
        }
    };

    private KlipCallback klipCallback = new KlipCallback<KlipResponse>() {
        @Override
        public void onSuccess(final KlipResponse res) {
            String out = JsonHelper.toPrettyFormat(res.toString());
            resView.setText(out);

            // save request key
            String resultKey = res.getRequestKey();
            if (resultKey != null)
                requestKey = resultKey;

            // save user address
            KlipResult result = res.getResult();
            if (result != null) {
                userAddress = result.getKlaytnAddress();
            }
        }
        @Override
        public void onFail(final KlipErrorResponse res) {
            resView.setText(res.toString());

            // reset request key
            requestKey = null;
        }
    };

    private KlipCallback cardListCallback = new KlipCallback<CardListResponse>() {
        @Override
        public void onSuccess(final CardListResponse res) {
            String out = JsonHelper.toPrettyFormat(res.toString());
            resView.setText(out);
        }
        @Override
        public void onFail(final KlipErrorResponse res) {
//            String out = JsonHelper.toPrettyFormat(res.toJson());
            resView.setText(res.toString());
        }
    };
}
