package com.nftime.app.util.asyncTasks;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.google.gson.Gson;
import com.nftime.app.MainActivity;
import com.nftime.app.R;
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
import java.util.stream.Collectors;

public class MintWithWorkIdTask extends AsyncTask<Integer, Void, String> {
    private Context context;
    public AsyncResponse<String> delegate = null;

    public MintWithWorkIdTask(Context context, AsyncResponse<String> delegate){
        this.context = context;
        this.delegate = delegate;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected String doInBackground(Integer... integers) {
        int workId = integers[0];
        try {
            // Create URL
            URL httpEndpoint = null;
            httpEndpoint = new URL(ApplicationConstants.AWS_URL
                    + "mintToAddr?"
                    + "address=" + MainActivity.klaytnAddress + "&"
                    + "work_id=" + workId);
            HttpURLConnection myConnection =
                    (HttpURLConnection) httpEndpoint.openConnection();

            myConnection.setRequestMethod("GET");

            int resCode = myConnection.getResponseCode();

            if (resCode == 200 || resCode == 201) {
                // Success
                // Further processing here
                InputStream responseBody = myConnection.getInputStream();

                String text = new BufferedReader(
                        new InputStreamReader(responseBody, StandardCharsets.UTF_8))
                        .lines()
                        .collect(Collectors.joining("\n"));

                if(text != null){
                    return text;
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

        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        delegate.onAsyncSuccess(result);
    }
}
