package com.example.kenny.myapplication;

import android.content.Context;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by kenny on 2017-12-27.
 */



public class getAdditionalInfo extends AsyncTask<String,additionalInfo,additionalInfo> {
    private Context context;

    private additionalComplete callback;

    public getAdditionalInfo(Context context){
        this.context = context;
        this.callback = (additionalComplete) context;
    }

    @Override
    protected additionalInfo doInBackground(String... params) {

        HttpURLConnection connection = null;
        JsonReader reader = null;
        URL urlConnection;
        String url = params[0];
        try {
            urlConnection = new URL(url);
            connection = (HttpURLConnection)urlConnection.openConnection();
            InputStream stream = connection.getInputStream();
            reader = new JsonReader(new InputStreamReader(stream));
            GetJson getJson = new GetJson(reader,true,null);
            return getJson.readAdditonal(reader);

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            if(connection!=null){
                connection.disconnect();
            }
            if(reader!=null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }

    @Override
    protected void onPostExecute(additionalInfo result) {
        //super.onPostExecute(result);
        callback.onAdditionalComplete(result);
    }
}

