package com.example.kenny.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.JsonReader;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import com.example.kenny.myapplication.MainActivity;
import com.google.android.gms.tasks.Task;

/**
 * Created by kenny on 2017-11-17.
 */


public class ImageLoadTask extends AsyncTask<MyTaskParams,ArrayList<image>,ArrayList<image>> {
    private Context context;
    private TaskComplete callback;

    public ImageLoadTask(Context context){
        this.context = context;
        this.callback = (TaskComplete) context;
    }

    @Override
    protected ArrayList doInBackground(MyTaskParams... params) {

        HttpURLConnection connection = null;
        JsonReader reader = null;
        URL urlConnection;
        ArrayList jsonUrl = new ArrayList(0);
        boolean isSearch = params[0].search;
        String url = params[0].url;
        try {
            urlConnection = new URL(url);
            connection = (HttpURLConnection)urlConnection.openConnection();
            connection.setReadTimeout(10000);
            InputStream stream = connection.getInputStream();
            reader = new JsonReader(new InputStreamReader(stream));
            GetJson getJson = new GetJson(reader,isSearch);
            return getJson.partTwo();

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
    protected void onPostExecute(ArrayList<image> result) {
        //super.onPostExecute(result);
        //if(result!=null){
            callback.onTaskComplete(result);
        //}
//
    }


}

