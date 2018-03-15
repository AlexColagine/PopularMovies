package com.example.android.popularmovies.Object;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Alessandro on 15/03/2018.
 */

public class Video implements Parcelable {

    private String id;
    private String key;
    private String name;
    private String site;
    private int size;
    private String languageCode;
    private String countryCode;
    private String type;

    public Video (String id , String key , String name , String site , int size , String languageCode , String countryCode, String type){
        this.id = id;
        this.key = key;
        this.name = name;
        this.site = site;
        this.size = size;
        this.languageCode = languageCode;
        this.countryCode = countryCode;
        this.type = type;
    }

    public String getId(){
        return id;
    }

    public String getKey(){
        return key;
    }

    public String getName(){
        return name;
    }

    public String getSite(){
        return site;
    }

    public int getSize(){
        return size;
    }

    public String getLanguageCode(){
        return languageCode;
    }

    public String getCountryCode(){
        return countryCode;
    }

    public String getType(){
        return type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.key);
        dest.writeString(this.name);
        dest.writeString(this.site);
        dest.writeInt(this.size);
        dest.writeString(this.languageCode);
        dest.writeString(this.countryCode);
        dest.writeString(this.type);
    }

    protected Video(Parcel in) {
        this.id = in.readString();
        this.key = in.readString();
        this.name = in.readString();
        this.site = in.readString();
        this.size = in.readInt();
        this.languageCode = in.readString();
        this.countryCode = in.readString();
        this.type = in.readString();
    }

    public static final Parcelable.Creator<Video> CREATOR = new Parcelable.Creator<Video>() {
        @Override
        public Video createFromParcel(Parcel source) {
            return new Video(source);
        }

        @Override
        public Video[] newArray(int size) {
            return new Video[size];
        }
    };
}
