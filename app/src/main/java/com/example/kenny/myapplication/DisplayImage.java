package com.example.kenny.myapplication;

/**
 * Created by kenny on 2017-10-02.
 */

import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import com.wang.avi.AVLoadingIndicatorView;
import java.io.IOException;


public class DisplayImage extends AppCompatActivity implements additionalComplete{
    Bitmap bitmapp;
    final Context context = this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_display_image);
        AVLoadingIndicatorView avi = (AVLoadingIndicatorView)findViewById(R.id.displayAVI);
        int[] ids = {R.id.author,R.id.likes,R.id.downloads,R.id.country};
        for (int i : ids) {
            TextView textData = (TextView) findViewById(i);
            textData.setVisibility(View.GONE);
        }

        PhotoView imageData = (PhotoView) findViewById(R.id.full_image);
        imageData.setAdjustViewBounds(true);
        imageData.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Intent intent = getIntent();
        String buildUrl = "https://api.unsplash.com/photos/"+intent.getStringExtra("ID")+"?client_id=a6c0389a37254f023d0c1a63b813fd63fafafb2f10d87341c63fecafd0776851";
        new getAdditionalInfo(this).execute(buildUrl);
        final String url = intent.getStringExtra("LRG");
        imageData.setBackgroundColor(Color.parseColor(intent.getStringExtra("COLOR")));
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        Picasso mPicasso = Picasso.with(this);
        mPicasso.setIndicatorsEnabled(true);
        avi.show();
        mPicasso.load(url)
                .centerCrop()
                .resize(width,height)
                .into(target);
        avi.hide();
        TextView textData = (TextView)findViewById(R.id.author);
        textData.setText(" "+intent.getStringExtra("NAME"));
        textData = (TextView)findViewById(R.id.likes);
        textData.setText(" Likes: "+Integer.toString(intent.getIntExtra("LIKES",0)));
        //get a reference to the view for pressing
        //register if for context
        imageData.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                int[] ids = {R.id.author,R.id.likes,R.id.downloads,R.id.country};
                for (int i : ids) {
                    TextView textData = (TextView) findViewById(i);
                    if (textData.getVisibility() == View.GONE) {
                        if (i == R.id.country && textData.getText().equals("")) {
                            continue;
                        } else {
                            textData.setVisibility(View.VISIBLE);
                        }
                    }
                    else{textData.setVisibility(View.GONE);}
                }
            }
        });

        com.github.clans.fab.FloatingActionButton button = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.menu_item);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                com.github.clans.fab.FloatingActionMenu menu = (com.github.clans.fab.FloatingActionMenu) findViewById(R.id.menu);
                menu.close(true);
               setBackground task = new setBackground(context);
                task.execute(bitmapp);
            }
        });
    }

    @Override
    public void onAdditionalComplete(additionalInfo addInfo) {
        TextView textData;
        textData = (TextView)findViewById(R.id.downloads);
        textData.setText(" Downloads: "+Integer.toString((addInfo.get_downloads())));
        textData = (TextView)findViewById(R.id.country);
        String tempCity = addInfo.get_city();
        String tempCountry = addInfo.get_country();
        if(tempCity==null||tempCountry==null){
            textData.setText("");
        }else{textData.setText(" Location: "+addInfo.get_city()+", "+addInfo.get_country());}

    }


    public class setBackground extends AsyncTask<Bitmap,String,String> {
        private Context context;
        public setBackground(Context context){
            this.context = context;
        }

        @Override
        protected String doInBackground(Bitmap... params) {
            try {
                Bitmap result=params[0];
                WallpaperManager wallpaperManager = WallpaperManager.getInstance(context);
                wallpaperManager.setBitmap(result);
                return "Set as Wallpaper success";
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            return "Set as Wallpaper failed";
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getApplicationContext(), result,
                    Toast.LENGTH_SHORT).show();
            //super.onPostExecute(result);
        }
    }
    private Target target = new Target() {


        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            // Bitmap is loaded, use image here
            PhotoView imageData = (PhotoView) findViewById(R.id.full_image);
            imageData.setImageBitmap(bitmap);
            bitmapp = bitmap;
        }

        @Override
        public void onBitmapFailed(Drawable errorDrawable) {

        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {

        }

    };

}