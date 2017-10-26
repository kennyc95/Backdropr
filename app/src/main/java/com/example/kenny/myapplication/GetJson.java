package com.example.kenny.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.JsonReader;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by kenny on 2017-10-02.
 */

public class GetJson{
    ArrayList<image> picList;
    JsonReader reader;
    boolean isSearch;
    public GetJson(JsonReader reader, boolean isSearch){

        picList = new ArrayList<image>();
        this.reader = reader;
        this.isSearch= isSearch;
    }


    public ArrayList<image> partTwo(){

        if(isSearch==false) {
            try {
                readMessageArray(reader);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(isSearch == true){

            try {
                readResultsArray(reader);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return picList;
    }
    public ArrayList readMessageArray(JsonReader reader) throws IOException {
        reader.beginArray();
        while (reader.hasNext()) {
            picList.add(readMessage(reader));
        }
        reader.endArray();
        return picList;
    }
    public ArrayList readResultsArray(JsonReader reader) throws IOException {
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            Log.e("hola4535",": "+name);
            if (name.equals("photos")) {
                readPhotos(reader);
            }else{
                reader.skipValue();
            }

        }
        reader.endObject();
        return picList;
    }
    public ArrayList readPhotos(JsonReader reader) throws IOException {
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            Log.e("hola4535",": "+name);
            if (name.equals("results")) {
                readMessageArray(reader);
            }else{
                reader.skipValue();
            }

        }
        reader.endObject();
        return picList;
    }

    public image readMessage(JsonReader reader) throws IOException {
        String text = null;
        image myImage = new image();

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("urls")) {
                readUrl(reader, myImage);
            }
            else if (name.equals("user")) {

                text = readUser(reader);
                Log.e("kenny","isSearch " + text);
                myImage.set_author(text);
            }else if(name.equals("color")){
                text = reader.nextString();
                myImage.set_color(text);
            }else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return myImage;
    }

    public void readUrl(JsonReader reader, image myImage) throws IOException {
        String text = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("small")) {
                text = reader.nextString();
                myImage.set_ImageUrl(text);
            }else if (name.equals("full")) {
                text = reader.nextString();
                myImage.set_large(text);
            }else {
                reader.skipValue();
            }
        }
        reader.endObject();
    }
    public String readUser(JsonReader reader) throws IOException {
        String text = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("name")) {
                text = reader.nextString();
            }else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return text;
    }

}

