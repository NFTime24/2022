package com.nftime.app.util.asyncTasks;

import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.google.gson.Gson;
import com.nftime.app.objects.ExhibitionObj;
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

public class GetExhibitionsTask extends AsyncTask<Void, Void, ExhibitionObj[]> {
    public AsyncResponse<ExhibitionObj[]> delegate = null;

    public GetExhibitionsTask(AsyncResponse<ExhibitionObj[]> delegate){
        this.delegate = delegate;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected ExhibitionObj[] doInBackground(Void... voids) {
        try {
            // Create URL
            URL httpEndpoint = null;
            httpEndpoint = new URL(ApplicationConstants.AWS_URL
                    + "exhibition");
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

                Gson gson = new Gson();
                ExhibitionObj[] exhibitionObjs = gson.fromJson(text, ExhibitionObj[].class);

                if(exhibitionObjs.length > 0){
                    return exhibitionObjs;
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

        return new ExhibitionObj[0];
    }

    @Override
    protected void onPostExecute(ExhibitionObj[] exhibitionObjs) {
        super.onPostExecute(exhibitionObjs);
        delegate.onAsyncSuccess(exhibitionObjs);
    }
}
