package com.example.android.popularmovies.Object;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Alessandro on 18/02/2018.
 */

public class Movie implements Parcelable {

    private String title;
    private String image;
    private String plot;
    private double rating;
    private String date;
    private String language;
    private String backdrop_image;


    public Movie ( String title, String image, String plot, double rating, String date , String language , String backdrop_image){
        this.title = title;
        this.image = image;
        this.plot = plot;
        this.rating = rating;
        this.date = date;
        this.language = language;
        this.backdrop_image = backdrop_image;
    }

    public String getTitle(){
        return title;
    }

    public String getImage(){
        return image;
    }

    public String getPlot(){
        return plot;
    }

    public double getRating(){
        return rating;
    }

    public String getDate(){
        return date;
    }

    public String getLanguage(){
        return language;
    }

    public String getBackdrop(){
        return backdrop_image;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.image);
        dest.writeString(this.plot);
        dest.writeDouble(this.rating);
        dest.writeString(this.date);
        dest.writeString(this.language);
        dest.writeString(this.backdrop_image);
    }

    protected Movie(Parcel in) {
        this.title = in.readString();
        this.image = in.readString();
        this.plot = in.readString();
        this.rating = in.readDouble();
        this.date = in.readString();
        this.language = in.readString();
        this.backdrop_image = in.readString();
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
