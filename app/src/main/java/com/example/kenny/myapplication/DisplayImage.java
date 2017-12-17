package com.example.kenny.myapplication;

/**
 * Created by kenny on 2017-10-02.
 */

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;
import com.wang.avi.AVLoadingIndicatorView;


public class DisplayImage extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_display_image);
        AVLoadingIndicatorView avi = (AVLoadingIndicatorView)findViewById(R.id.displayAVI);
        PhotoView imageData = (PhotoView) findViewById(R.id.full_image);
        imageData.setAdjustViewBounds(true);
        imageData.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Intent intent = getIntent();
        String url = intent.getStringExtra("LRG");
        imageData.setBackgroundColor(Color.parseColor(intent.getStringExtra("COLOR")));
        Picasso mPicasso = Picasso.with(this);
        mPicasso.setIndicatorsEnabled(true);
        avi.show();
        mPicasso.load(url)
                .fit()
                .centerCrop()
                .into(imageData);
        avi.hide();
        TextView textData = (TextView)findViewById(R.id.author);
        textData.setText(intent.getStringExtra("NAME"));
        //get a reference to the view for pressing
        //register if for context
        imageData.setOnLongClickListener(new View.OnLongClickListener() {

        @Override
        public boolean onLongClick(View v) {
            TextView textData = (TextView)findViewById(R.id.author);
            textData.setText("klmaoooo");

            return true;
        }

    });
    }
}