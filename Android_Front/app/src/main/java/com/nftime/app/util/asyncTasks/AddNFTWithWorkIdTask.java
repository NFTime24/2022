package com.nftime.app.util.asyncTasks;

import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

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

public class AddNFTWithWorkIdTask extends AsyncTask<Integer, Void, Integer> {
    public AsyncResponse<Integer> delegate = null;

    public AddNFTWithWorkIdTask(AsyncResponse<Integer> delegate){
        this.delegate = delegate;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected Integer doInBackground(Integer... integers) {
        int work_id = integers[0];
        try {
            // Create URL
            URL httpEndpoint = null;
            httpEndpoint = new URL(ApplicationConstants.AWS_URL
                    + "addNFTWithWorkId?"
                    + "work_id=" + work_id);
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

                return Integer.decode(text);
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
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        delegate.onAsyncSuccess(integer);
    }
}
