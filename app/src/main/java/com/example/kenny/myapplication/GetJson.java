package com.example.kenny.myapplication;


import android.util.JsonReader;
import android.util.JsonToken;
import java.io.IOException;
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
            if (name.equals("photos")) {readPhotos(reader);}
            else{reader.skipValue();}
        }
        reader.endObject();
        return picList;
    }
    public ArrayList readPhotos(JsonReader reader) throws IOException {
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("results")) {readMessageArray(reader);}
            else{reader.skipValue();}
        }
        reader.endObject();
        return picList;
    }
    public image readMessage(JsonReader reader) throws IOException {
        image myImage = new image();
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();

            if (name.equals("urls")) {readUrl(reader, myImage);}
            else if (name.equals("user")) {myImage.set_author(readUser(reader));}
            else if(name.equals("color")){myImage.set_color(reader.nextString());}
            else if(name.equals("likes")){myImage.set_likes(reader.nextInt());}
            else if(name.equals("id")){myImage.set_id(reader.nextString());}
            else {reader.skipValue();}
        }
        reader.endObject();

        return myImage;
    }

    public void readUrl(JsonReader reader, image myImage) throws IOException {
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("small")) {myImage.set_ImageUrl(reader.nextString());}
            else if (name.equals("full")) {myImage.set_large(reader.nextString());}
            else {reader.skipValue();}
        }
        reader.endObject();

    }
    public String readUser(JsonReader reader) throws IOException {
        String text = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("name")) {text = reader.nextString();}
            else{reader.skipValue();}
        }
        reader.endObject();
        return text;
    }
    public additionalInfo readAdditonal(JsonReader reader) throws IOException {
        additionalInfo aInfo = new additionalInfo();
        String list[];
        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("downloads")) {aInfo.set_downloads(reader.nextInt());}
            else if(name.equals("location")){list = getLocation(reader);aInfo.set_city(list[0]);aInfo.set_country(list[1]);}
            else{reader.skipValue();}
        }
        reader.endObject();

        return aInfo;
    }
    public String[] getLocation(JsonReader reader) throws IOException{
        reader.beginObject();
        String list[] = new String[2];

        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("city")&&reader.peek()!= JsonToken.NULL) {
                    list[0] = reader.nextString();
            }
            else if (name.equals("country")&&reader.peek()!=JsonToken.NULL) {
                list[1]= reader.nextString();
            } else {
                reader.skipValue();
            }

        }
        reader.endObject();
        return list;
    }
}

