package com.example.kenny.myapplication;



/**
 * Created by kenny on 2017-10-02.
 */

public class image {
    private String imageUrl;
    private String author;
    private String color;
    private String largeUrl;

    public String getImageUrl(){
        return imageUrl;
    }

    public String get_author(){
        return author;
    }

    public String get_color(){
        return color;
    }

    public String get_large(){
        return largeUrl;
    }



    public void set_ImageUrl(String imageUrl){
        this.imageUrl= imageUrl;
    }

    public void set_author(String author){
        this.author = author;
    }


    public void set_color(String color){
        this.color = color;
    }
    public void set_large(String largeUrl){
        this.largeUrl = largeUrl;
    }
}
