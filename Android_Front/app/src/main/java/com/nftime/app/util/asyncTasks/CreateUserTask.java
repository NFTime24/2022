package com.nftime.app.util.asyncTasks;

import android.content.Context;
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
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class CreateUserTask extends AsyncTask<String, Void, UserObj> {
    public AsyncResponse<UserObj> delegate = null;

    private final String boundary = "*****";
    private static final String LINE = "\r\n";
    private String charset = "UTF-8";

    public CreateUserTask(AsyncResponse<UserObj> delegate){
        this.delegate = delegate;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected UserObj doInBackground(String... strings) {
        String address = strings[0];
        try {
            // Create URL
            URL httpEndpoint = null;
            httpEndpoint = new URL(ApplicationConstants.AWS_URL
                    + "user");
            HttpURLConnection myConnection =
                    (HttpURLConnection) httpEndpoint.openConnection();

            myConnection.setUseCaches(false);
            myConnection.setRequestMethod("POST");
            myConnection.setDoOutput(true);
            myConnection.setDoInput(true);
            myConnection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);

            OutputStream outputStream = myConnection.getOutputStream();
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(outputStream, charset), true);

            HashMap<String, String> formDatas = new HashMap<>();
            formDatas.put("user_nickname", address.substring(2, 7));
            formDatas.put("user_address", address);

            for(String key : formDatas.keySet()){
                String value = formDatas.get(key);

                writer.append("--" + boundary).append(LINE);
                writer.append("Content-Disposition: form-data; name=\"" + key + "\"").append(LINE);
                writer.append("Content-Type: text/plain; charset=" + charset).append(LINE);
                writer.append(LINE);
                writer.append(value).append(LINE);
                writer.flush();
            }

            writer.flush();
            writer.append("--" + boundary + "--").append(LINE);
            writer.close();

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
                UserObj userObj = gson.fromJson(text, UserObj.class);

                if(userObj != null){
                    return userObj;
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
    protected void onPostExecute(UserObj result) {
        super.onPostExecute(result);
        delegate.onAsyncSuccess(result);
    }
}
