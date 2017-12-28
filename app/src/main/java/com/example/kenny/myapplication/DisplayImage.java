package com.example.kenny.myapplication;

/**
 * Created by kenny on 2017-10-02.
 */

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import com.github.chrisbanes.photoview.PhotoView;
import com.squareup.picasso.Picasso;
import com.wang.avi.AVLoadingIndicatorView;


public class DisplayImage extends AppCompatActivity implements additionalComplete{
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
        String url = intent.getStringExtra("LRG");
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
                .into(imageData);
        avi.hide();
        TextView textData = (TextView)findViewById(R.id.author);
        textData.setText(intent.getStringExtra("NAME"));
        textData = (TextView)findViewById(R.id.likes);
        textData.setText("Likes:    "+Integer.toString(intent.getIntExtra("LIKES",0)));


        registerForContextMenu(findViewById(R.id.full_image));

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
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo){
        super.onCreateContextMenu(menu, v, menuInfo);
        AdapterView.AdapterContextMenuInfo info  =  (AdapterView.AdapterContextMenuInfo) menuInfo;
            menu.setHeaderTitle("Whjats up g");
            menu.add(0, 1, 0, "Set As Wallpaper");
            menu.add(0, 2, 0, "Set As Lockscreen");
        super.onCreateContextMenu(menu, v, menuInfo);

    }

    @Override
    public void onAdditionalComplete(additionalInfo addInfo) {
        TextView textData;
        textData = (TextView)findViewById(R.id.downloads);
        textData.setText("Downloads:    "+Integer.toString((addInfo.get_downloads())));
        textData = (TextView)findViewById(R.id.country);
        String tempCity = addInfo.get_city();
        String tempCountry = addInfo.get_country();
        if(tempCity==null||tempCountry==null){
            textData.setText("");
        }else{textData.setText("Country:    "+addInfo.get_city()+", "+addInfo.get_country());}

    }
}