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
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by kenny on 2017-11-17.
 */

public class ImageLoadTask extends AppCompatActivity{
    private RecyclerAdapter madapter;
    private RecyclerView recyclerView;
    private ArrayList picList;
    private ImageView image;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Activity activity = this;
        int numberOfColumns = 2;
        recyclerView = (RecyclerView)findViewById(R.id.rvNumbers);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), numberOfColumns));
        RecyclerAdapter madapter = new RecyclerAdapter(getApplicationContext(), picList,recyclerView, this);
        recyclerView.setAdapter(madapter);
        Intent intent = this.getIntent();
        Bundle args = intent.getExtras();
        boolean isSearch = args.getBoolean("boolean");
        String url = args.getString("url");
        MyTaskParams params = new MyTaskParams(isSearch,url);
        ImageLoadTask task;
        task = new ImageLoadTask(getApplicationContext(),image);
        task.execute(params);
    }

    public class ImageLoadTask extends AsyncTask<MyTaskParams,ArrayList,ArrayList> {
        private Context context;
        private ImageView imageView;

        public ImageLoadTask(Context context,ImageView imageView){
            this.context = context;
            this.imageView = imageView;
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
        protected void onPostExecute(ArrayList result) {
            super.onPostExecute(result);

            if(!result.isEmpty())
            {

                madapter = new RecyclerAdapter(getApplicationContext(), result, recyclerView,(Activity)getApplicationContext());
                recyclerView.setAdapter(madapter);
            }

        }
    }

    private static class MyTaskParams {
        boolean search;

        String url;

        MyTaskParams(boolean search, String url) {
            this.search = search;
            this.url = url;

        }
    }
}
