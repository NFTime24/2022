package com.nftime.app.actions;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.google.gson.Gson;
import com.klipwallet.app2app.api.Klip;
import com.klipwallet.app2app.api.KlipCallback;
import com.klipwallet.app2app.api.request.AuthRequest;
import com.klipwallet.app2app.api.request.ContractTxRequest;
import com.klipwallet.app2app.api.request.model.BAppDeepLinkCB;
import com.klipwallet.app2app.api.request.model.BAppInfo;
import com.klipwallet.app2app.api.response.CardListResponse;
import com.klipwallet.app2app.api.response.KlipErrorResponse;
import com.klipwallet.app2app.api.response.KlipResponse;
import com.klipwallet.app2app.exception.KlipRequestException;
import com.nftime.app.R;
import com.nftime.app.dialog.CardDlgFragment;
import com.nftime.app.dialog.ContractDlgFragment;
import com.nftime.app.dialog.GetCardListDlgFragment;
import com.nftime.app.dialog.KlayDlgFragment;
import com.nftime.app.dialog.LinkDlgFragment;
import com.nftime.app.dialog.TokenDlgFragment;
import com.nftime.app.util.asyncTasks.AddNFTWithWorkIdTask;
import com.nftime.app.util.asyncTasks.AsyncResponse;

import java.util.ArrayList;

public class KlipAction {
    private Context ctx;
    private Klip klip;

    private String requestKey;

    public KlipAction(Context ctx, Klip klip) {
        this.ctx = ctx;
        this.klip = klip;
    }

    public void prepareLink(KlipCallback<KlipResponse> callback) {
        new LinkDlgFragment(klip, callback).show(((FragmentActivity)ctx).getSupportFragmentManager(), null);
    }

    public void prepareKlay(KlipCallback<KlipResponse> callback) {
        new KlayDlgFragment(klip, callback).show(((FragmentActivity)ctx).getSupportFragmentManager(), null);
    }

    public void prepareToken(KlipCallback<KlipResponse> callback) {
        new TokenDlgFragment(klip, callback).show(((FragmentActivity)ctx).getSupportFragmentManager(), null);
    }

    public void prepareCard(String userCardId, KlipCallback<KlipResponse> callback) {
        new CardDlgFragment(klip, userCardId, callback).show(((FragmentActivity)ctx).getSupportFragmentManager(), null);
    }

    public void prepareContract(KlipCallback<KlipResponse> callback) {
        new ContractDlgFragment(klip, callback).show(((FragmentActivity)ctx).getSupportFragmentManager(), null);
    }

    public void request(String requestKey) {
        try {
            klip.request(requestKey);
        } catch (KlipRequestException e) {
            Toast.makeText(ctx, "it's need to call request api first", Toast.LENGTH_LONG).show();
        }
    }

    public String getRequestKey(){
        return requestKey;
    }

    public void getResult(String requestKey, KlipCallback<KlipResponse> callback) {
        try {
            klip.getResult(requestKey, callback);
        } catch (KlipRequestException e) {
            Toast.makeText(ctx, "it's need to call request api first", Toast.LENGTH_LONG).show();
        }
    }

    public void getCardList(String userAddress, KlipCallback<CardListResponse> callback) {
        new GetCardListDlgFragment(ctx, klip, callback, userAddress).show(((FragmentActivity)ctx).getSupportFragmentManager(), null);
    }
    public void loginWithKlip(){
        String bappName = "NFTime";
        String successURL = "";
        //String successURL = "nftime://onboarding";
        String failURL = "";

        BAppInfo bAppInfo = new BAppInfo(bappName);
        if(successURL.length()!=0 || failURL.length()!=0) {
            BAppDeepLinkCB bAppCB = new BAppDeepLinkCB(successURL, failURL);
            bAppInfo.setCallback(bAppCB);
        }

        AuthRequest req = new AuthRequest();

        try {
            klip.prepare(req, bAppInfo, new KlipCallback() {
                @Override
                public void onSuccess(Object result) {
                    KlipResponse res = (KlipResponse) result;
                    requestKey = res.getRequestKey();

                    if(requestKey != null){
                        SharedPreferences pref = ctx.getSharedPreferences(String.valueOf(R.string.preference_file_key), Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putBoolean("IsSigned", true);
                        editor.putString("LoginKey", res.getRequestKey());
                        editor.apply();

                        try {
                            klip.request(requestKey);
                        } catch (KlipRequestException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFail(KlipErrorResponse error) {
                    Log.d("test", error.toString());

                    requestKey = null;
                }
            });
        } catch (KlipRequestException e) {
            e.printStackTrace();
        }
    }

    /*public void updateAccountInfo(LoginCallback loginCallback){
        SharedPreferences pref = ctx.getSharedPreferences(String.valueOf(R.string.preference_file_key), Context.MODE_PRIVATE);

        boolean isSigned = pref.getBoolean("IsSigned", false);
        String loginKey = pref.getString("LoginKey", "");

        if(isSigned)
            MainActivity.loginKey = loginKey == "" ? null : loginKey;

        if(MainActivity.loginKey != null){
            GetOwnedNftIdsTask getOwnedNftIdsTask = new GetOwnedNftIdsTask(new AsyncResponse<Void>() {
                @Override
                public void onAsyncSuccess(Void result) {
                    loginCallback.onSuccess();
                }
            });
            if(MainActivity.klaytnAddress != null){
                getOwnedNftIdsTask.execute();
            }
            else {
                if(this != null){
                    this.getResult(MainActivity.loginKey, new KlipCallback<KlipResponse>() {
                        @Override
                        public void onSuccess(KlipResponse result) {
                            KlipResult res = result.getResult();
                            MainActivity.klaytnAddress = res.getKlaytnAddress();
                            if(MainActivity.klaytnAddress != null){
                                getOwnedNftIdsTask.execute();
                            }
                        }

                        @Override
                        public void onFail(KlipErrorResponse error) {
                            Log.d("test", "MainActivity|" + error.toString());

                            if(loginCallback != null){
                                loginCallback.onFail(error.getException());
                            }
                        }
                    });
                }
            }
        }
    }*/

    public void mintWithKlip(String from, int work_id, String artist_address){
        String bappName = "NFTime";
        String successURL = "nftime://main";
        //String successURL = "";
        String failURL = "";

        BAppInfo bAppInfo = new BAppInfo(bappName);
        if(successURL.length()!=0 || failURL.length()!=0) {
            BAppDeepLinkCB bAppCB = new BAppDeepLinkCB(successURL, failURL);
            bAppInfo.setCallback(bAppCB);
        }

        new AddNFTWithWorkIdTask(new AsyncResponse<Integer>() {
            @Override
            public void onAsyncSuccess(Integer result) {
                String params = "[" + result + ",\"" + artist_address + "\"]";

                ContractTxRequest req = new ContractTxRequest.Builder()
                        .to("0xf1cB5DDF7E8E9Af429b79473c41Dd85750Faa7af")
                        .from(from)
                        .value("0")
                        .abi("{ \"inputs\": [ { \"internalType\": \"uint256\", \"name\": \"newItemId\", \"type\": \"uint256\" }, { \"internalType\": \"string\", \"name\": \"artist_address\", \"type\": \"string\" } ], \"name\": \"mintArt\", \"outputs\": [ { \"internalType\": \"uint256\", \"name\": \"\", \"type\": \"uint256\" } ], \"stateMutability\": \"nonpayable\", \"type\": \"function\" }")
                        .params(getStringToList(params))
                        .build();

                try {
                    klip.prepare(req, bAppInfo, new KlipCallback() {
                        @Override
                        public void onSuccess(Object result) {
                            KlipResponse res = (KlipResponse) result;
                            requestKey = res.getRequestKey();
                            if(requestKey != null){
                                try {
                                    klip.request(requestKey);
                                } catch (KlipRequestException e) {
                                    e.printStackTrace();
                                }
                            }
                        }

                        @Override
                        public void onFail(KlipErrorResponse error) {
                            Log.d("test", error.toString());

                            requestKey = null;
                        }
                    });
                } catch (KlipRequestException e) {
                    e.printStackTrace();
                }
            }
        }).execute(work_id);
    }

    private ArrayList<Object> getStringToList(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, ArrayList.class);
    }
}
