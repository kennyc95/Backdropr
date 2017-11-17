package com.example.kenny.myapplication;

/**
 * Created by kenny on 2017-10-02.
 */

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;


public class DisplayImage extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_image);
        PhotoView imageData = (PhotoView) findViewById(R.id.full_image);
        imageData.setAdjustViewBounds(true);
        imageData.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Intent intent = getIntent();
        String url = intent.getStringExtra("LRG");
        imageData.setBackgroundColor(Color.parseColor(intent.getStringExtra("COLOR")));
        Picasso mPicasso = Picasso.with(this);
        mPicasso.setIndicatorsEnabled(true);
        mPicasso.load(url)
                .into(imageData);
        TextView textData = (TextView)findViewById(R.id.author);
        textData.setText(intent.getStringExtra("NAME"));
        imageData.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                return true;
            }
        });
    }
}