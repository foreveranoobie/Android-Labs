package com.ntukhpi.storozhuk.lab10;

import android.os.AsyncTask;
import android.widget.TextView;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

class RequestTask extends AsyncTask<String, String, String> {
    private String serverResponse = "";
    TextView textView;

    public RequestTask(TextView textView) {
        this.textView = textView;
    }

    @Override
    protected String doInBackground(String... strings) {
        URL url;
        HttpURLConnection urlConnection;
        serverResponse = "0";
        try {
            url = new URL("https://" + strings[0]);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(10000);
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.connect();
            int responseCode = urlConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                serverResponse = String.valueOf(readStream(urlConnection.getInputStream()).length());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return serverResponse;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        textView.setText(serverResponse + " bytes");
    }

    private String readStream(InputStream in) {
        BufferedReader reader = null;
        StringBuffer response = new StringBuffer();
        try {
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return response.toString();
    }
}