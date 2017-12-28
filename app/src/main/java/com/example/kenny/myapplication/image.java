package com.example.kenny.myapplication;



/**
 * Created by kenny on 2017-10-02.
 */

public class image {
    private String imageUrl;
    private String author;
    private String color;
    private String largeUrl;
    private String id;
    private int likes;



    public String get_ImageUrl(){
        return imageUrl;
    }
    public String get_author(){
        return author;
    }
    public String get_color(){
        return color;
    }
    public String get_large(){return largeUrl;}
    public String get_id(){return id;}
    public int get_likes(){return likes;}


    public void set_ImageUrl(String imageUrl){
        this.imageUrl= imageUrl;
    }
    public void set_author(String author){
        this.author = author;
    }
    public void set_color(String color){this.color = color;}
    public void set_large(String largeUrl){
        this.largeUrl = largeUrl;
    }
    public void set_id(String id){this.id = id;}
    public void set_likes(int likes){this.likes = likes;}

}
