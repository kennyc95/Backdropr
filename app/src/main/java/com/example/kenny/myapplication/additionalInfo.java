package com.example.kenny.myapplication;

import android.util.Log;

/**
 * Created by kenny on 2017-12-27.
 */

public class additionalInfo {
    private int downloads;
    private String city=null;
    private String country=null;

    public String get_city(){return city;}
    public String get_country(){return country;}
    public int get_downloads(){return downloads;}
    public void set_downloads(int downloads){this.downloads = downloads;
        Log.e("fuck",Integer.toString(downloads));}
    public void set_city(String city){this.city = city;}
    public void set_country(String country){this.country = country;}
}
