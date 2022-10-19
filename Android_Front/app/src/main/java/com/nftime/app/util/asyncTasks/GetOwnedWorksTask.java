package com.nftime.app.util.asyncTasks;

import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.google.gson.Gson;
import com.nftime.app.MainActivity;
import com.nftime.app.objects.NftWorkObj;
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

public class GetOwnedWorksTask extends AsyncTask<Void, Void, ArrayList<NftWorkObj>> {
    private ArrayList<NftWorkObj> workObjs;

    public AsyncResponse<ArrayList<NftWorkObj>> delegate = null;

    public GetOwnedWorksTask(AsyncResponse<ArrayList<NftWorkObj>> delegate){
        this.delegate = delegate;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected ArrayList<NftWorkObj> doInBackground(Void... voids) {
        workObjs = new ArrayList<NftWorkObj>();
        for(int key : MainActivity.ownedWorkIds.keySet()){
            try {
                // Create URL
                URL httpEndpoint = null;
                httpEndpoint = new URL(ApplicationConstants.AWS_URL
                        + "getWorkInfoWithId?"
                        + "work_id=" + key);
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

                    Log.d("test", text);

                    Gson gson = new Gson();
                    NftWorkObj nftWorkObj = gson.fromJson(text, NftWorkObj.class);

                    Log.d("test", nftWorkObj.toString());

                    if(nftWorkObj.work_id >= 0 && !nftWorkObj.work_name.equals("")){
                        workObjs.add(nftWorkObj);
                    }
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
        }

        return workObjs;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onPostExecute(ArrayList<NftWorkObj> nftSet) {
        delegate.onAsyncSuccess(nftSet);
    }
}
