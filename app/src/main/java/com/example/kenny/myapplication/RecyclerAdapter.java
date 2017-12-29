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
    public RecyclerAdapter(Context context, ArrayList data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.mContext = context;



    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        View view = mInflater.inflate(R.layout.recycler_view_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        viewHolder.myImageView.setBackgroundColor(Color.parseColor(((image)mData.get(i)).get_color()));
        Picasso mPicasso = Picasso.with(mContext);
        mPicasso.load(((image)mData.get(i)).get_ImageUrl())
                .into(viewHolder.myImageView);
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
        intent.putExtra("URL",getItem(position).get_ImageUrl());
        intent.putExtra("NAME",getItem(position).get_author());
        intent.putExtra("LRG",getItem(position).get_large());
        intent.putExtra("LIKES",getItem(position).get_likes());
        intent.putExtra("COLOR",getItem(position).get_color());
        intent.putExtra("ID",getItem(position).get_id());
        mContext.startActivity(intent);
    }

}
