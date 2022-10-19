package com.nftime.app.util.asyncTasks;

import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.google.gson.Gson;
import com.nftime.app.MainActivity;
import com.nftime.app.objects.NftItemObj;
import com.nftime.app.objects.NftItemsObj;
import com.nftime.app.util.ApplicationConstants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class GetOwnedNftIdsTask extends AsyncTask<Void, Void, ArrayList<NftItemObj>> {
    private ArrayList<NftItemObj> nftSet;

    public AsyncResponse<Void> delegate = null;

    public GetOwnedNftIdsTask(AsyncResponse<Void> delegate){
        this.delegate = delegate;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected ArrayList<NftItemObj> doInBackground(Void... voids) {
        try {
            // Create URL
            URL httpEndpoint = null;
            httpEndpoint = new URL("https://th-api.klaytnapi.com/v2/contract"
                    + "/nft/" + ApplicationConstants.NFT_CONTRACT_ADDRESS
                    + "/owner/" + MainActivity.klaytnAddress);
            HttpURLConnection myConnection =
                    (HttpURLConnection) httpEndpoint.openConnection();

            myConnection.setRequestMethod("GET");
                /*myConnection.setUseCaches(false);
                myConnection.setDoInput(true);
                myConnection.setDoOutput(true);*/

            myConnection.setRequestProperty("x-chain-id", "8217");

            Log.d("test", "Response Code: " + String.valueOf(myConnection.getResponseCode()));

            if (myConnection.getResponseCode() == 200) {
                // Success
                // Further processing here
                InputStream responseBody = myConnection.getInputStream();

                String text = new BufferedReader(
                        new InputStreamReader(responseBody, StandardCharsets.UTF_8))
                        .lines()
                        .collect(Collectors.joining("\n"));

                Log.d("test", text);

                Gson gson = new Gson();
                NftItemsObj nftItemsObj = gson.fromJson(text, NftItemsObj.class);

                Log.d("test", nftItemsObj.toString());

                nftSet = nftItemsObj.getItemsWithAddress(MainActivity.klaytnAddress);

                myConnection.disconnect();

                return nftSet;
            } else {
                // Error handling code goes here
                InputStream responseBody = myConnection.getInputStream();

                String errStr = new BufferedReader(
                        new InputStreamReader(responseBody, StandardCharsets.UTF_8))
                        .lines()
                        .collect(Collectors.joining("\n"));

                Log.d("test", errStr);

                myConnection.disconnect();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onPostExecute(ArrayList<NftItemObj> nftSet) {
        GetOwnedWorkIdsTask getOwnedWorkIdsTask = new GetOwnedWorkIdsTask(new AsyncResponse<Void>() {
            @Override
            public void onAsyncSuccess(Void result) {
                delegate.onAsyncSuccess(null);
            }
        });
        getOwnedWorkIdsTask.execute(nftSet);
    }
}
