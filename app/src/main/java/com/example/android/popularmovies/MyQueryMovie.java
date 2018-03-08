package com.example.android.popularmovies;

/**
 * Created by Alessandro on 07/03/2018.
 */

public class MyQueryMovie {

    private static final MyQueryMovie instance = new MyQueryMovie();
    private String queryMovie = "popular";
    private String queryTop = "top_rated";

    private MyQueryMovie(){}

    public static MyQueryMovie getInstance(){
        return instance;
    }

    public String getValue(){
        return queryMovie;
    }

    public String getTop(){
        return queryTop;
    }

    public String setValue(String newValue){
       return queryMovie = newValue;
    }

}