package com.example.kenny.myapplication;

/**
 * Created by kenny on 2017-10-02.
 */


import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;


import com.jsibbold.zoomage.ZoomageView;
import com.squareup.picasso.Picasso;

import static com.example.kenny.myapplication.RecyclerAdapter.LRG;
import static com.example.kenny.myapplication.RecyclerAdapter.URL;
import static com.example.kenny.myapplication.RecyclerAdapter.NAME;
import static java.security.AccessController.getContext;

public class DisplayImage extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_display_image);
        ZoomageView imageData = (ZoomageView) findViewById(R.id.full_image);
        Intent intent = getIntent();
        String url = intent.getStringExtra(LRG);
        Picasso mPicasso = Picasso.with(this);
        mPicasso.setIndicatorsEnabled(true);
        mPicasso.load(url)
                .into(imageData);
        TextView textData = (TextView)findViewById(R.id.author);
        textData.setText(intent.getStringExtra(NAME));
    }
}