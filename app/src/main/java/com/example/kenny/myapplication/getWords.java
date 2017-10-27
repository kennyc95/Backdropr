package com.example.kenny.myapplication;

import android.util.JsonReader;
import android.util.Log;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by kenny on 2017-10-25.
 */

public class getWords {

    public static ArrayList readWordArray(JsonReader reader) throws IOException {
        ArrayList<String> wordList = new ArrayList<String>();
        reader.beginArray();
        while (reader.hasNext()) {
            wordList.add(readWord(reader));
        }
        reader.endArray();
        return wordList;
    }
    public static String readWord(JsonReader reader) throws IOException {
        reader.beginObject();
        String text= new String();
        while (reader.hasNext()) {
            String name = reader.nextName();
           if(name.equals("word")){
               text =reader.nextString();
               Log.e("tag" ,text);
            }else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return text;
    }
}
