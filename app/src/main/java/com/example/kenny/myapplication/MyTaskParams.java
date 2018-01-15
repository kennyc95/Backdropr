package com.example.kenny.myapplication;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

/**
 * Created by kenny on 2017-11-18.
 */

public class MyTaskParams {
    boolean search;
    String url;
    RecyclerAdapter madapter;
    MyTaskParams(boolean search, String url, RecyclerAdapter madapter) {
        this.search = search;
        this.url = url;
        this.madapter = madapter;

    }
}
