package com.example.kenny.myapplication;
/**
 * Created by kenny on 2017-08-24.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.view.View;
import android.widget.ImageView;
import android.view.LayoutInflater;


import com.squareup.picasso.Picasso;

import java.util.ArrayList;




public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>{
    private ArrayList mData;

    private LayoutInflater mInflater;
    private Context mContext;

    private boolean isLoading;
    private Activity activity;
    private OnLoadMoreListener onLoadMoreListener;
    public static final String URL = "URL";
    public static final String NAME = "NAME";
    public static final String LRG = "LRG";
    public RecyclerAdapter(Context context, ArrayList data, RecyclerView recyclerView, Activity activity) {
        this.mInflater = LayoutInflater.from(context);
        //if(data.get(0).get_author())
        this.mData = data;
        this.mContext = context;
        this.activity = activity;


    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View view = mInflater.inflate(R.layout.recycler_view_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        //String animal = (String)mData.get(i);
        viewHolder.myImageView.setBackgroundColor(Color.parseColor(((image)mData.get(i)).get_color()));
        Picasso mPicasso = Picasso.with(mContext);
        mPicasso.setIndicatorsEnabled(true);
        mPicasso.load(((image)mData.get(i)).getImageUrl())
                .into(viewHolder.myImageView);

        // viewHolder.myImageView.setText(animal);
    }




    @Override
    public int getItemCount() {
        if(mData == null){
            return 0;
        }
        return mData.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView myImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            myImageView = (ImageView)itemView.findViewById(R.id.displayImage);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onItemClick(view, getAdapterPosition());
        }
    }
    public image getItem(int id) {
        return (image)mData.get(id);
    }
    public void onItemClick(View view, int position) {
        Intent intent = new Intent(mContext, DisplayImage.class);
        intent.putExtra(URL,getItem(position).getImageUrl());
        intent.putExtra(NAME,getItem(position).get_author());
        intent.putExtra(LRG,getItem(position).get_large());
        mContext.startActivity(intent);
        System.out.println(getItem(position));
    }
}
