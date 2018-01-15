package com.example.kenny.myapplication;

import android.support.v7.widget.RecyclerView;

/**
 * Created by kenny on 2018-01-14.
 */

public class getRecycler {
    static RecyclerView x ;
    public static void save_View(RecyclerView b){
        x = b;
    }
    public static RecyclerView get_View(){
        return x;
    }

}
