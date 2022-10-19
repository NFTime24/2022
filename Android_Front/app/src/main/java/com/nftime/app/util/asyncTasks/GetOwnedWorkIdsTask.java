package com.nftime.app.util.asyncTasks;

import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.nftime.app.MainActivity;
import com.nftime.app.objects.NftItemObj;
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

public class GetOwnedWorkIdsTask extends AsyncTask<ArrayList<NftItemObj>, Void, Void> {
    private ArrayList<NftItemObj> nftSet;

    public AsyncResponse<Void> delegate = null;

    public GetOwnedWorkIdsTask(AsyncResponse<Void> delegate){
        this.delegate = delegate;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected Void doInBackground(ArrayList<NftItemObj> ... arrayLists) {
        this.nftSet = arrayLists[0];
        nftSet.forEach((v) -> {
            try {
                // Create URL
                URL httpEndpoint = null;
                httpEndpoint = new URL(ApplicationConstants.AWS_URL
                        + "getWorkIdWithNftId?"
                        + "nft_id=" + Integer.decode(v.tokenId));
                HttpURLConnection myConnection =
                        (HttpURLConnection) httpEndpoint.openConnection();

                myConnection.setRequestMethod("GET");

                Log.d("test", "Response Code: " + String.valueOf(myConnection.getResponseCode()));

                int resCode = myConnection.getResponseCode();

                if (resCode == 200 || resCode == 201) {
                    // Success
                    // Further processing here
                    InputStream responseBody = myConnection.getInputStream();

                    String text = new BufferedReader(
                            new InputStreamReader(responseBody, StandardCharsets.UTF_8))
                            .lines()
                            .collect(Collectors.joining("\n"));

                    int tokenId_decimal = Integer.decode(v.tokenId);
                    int workId_decimal = Integer.decode(text);

                    MainActivity.ownedNftIds2WorkIds.put(tokenId_decimal, workId_decimal);
                    MainActivity.ownedWorkIds.put(workId_decimal, true);
                } else {
                    // Error handling code goes here
                    InputStream responseBody = myConnection.getInputStream();

                    String errStr = new BufferedReader(
                            new InputStreamReader(responseBody, StandardCharsets.UTF_8))
                            .lines()
                            .collect(Collectors.joining("\n"));

                    Log.d("test", errStr);
                }

                myConnection.disconnect();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        return null;
    }

    @Override
    protected void onPostExecute(Void unused) {
        super.onPostExecute(unused);
        delegate.onAsyncSuccess(null);
    }
}
