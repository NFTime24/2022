package com.nftime.app.util.asyncTasks;

import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.google.gson.Gson;
import com.nftime.app.MainActivity;
import com.nftime.app.objects.NftWorkObj;
import com.nftime.app.objects.UserObj;
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

public class GetUserTask extends AsyncTask<String, Void, UserObj> {
    public AsyncResponse<UserObj> delegate = null;

    public GetUserTask(AsyncResponse<UserObj> delegate){
        this.delegate = delegate;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected UserObj doInBackground(String... strings) {
        String address = strings[0];
        UserObj userObj = null;
        try {
            // Create URL
            URL httpEndpoint = null;
            httpEndpoint = new URL(ApplicationConstants.AWS_URL
                    + "getUserWithAddress?"
                    + "address=" + address);
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
                userObj = gson.fromJson(text, UserObj.class);

                Log.d("test", userObj.toString());
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

        return userObj;
    }

    @Override
    protected void onPostExecute(UserObj userObj) {
        super.onPostExecute(userObj);
        delegate.onAsyncSuccess(userObj);
    }
}
