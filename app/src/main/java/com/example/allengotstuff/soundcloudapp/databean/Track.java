package com.example.allengotstuff.soundcloudapp.databean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by allengotstuff on 4/6/2017.
 */

public class Track implements Parcelable{

    private long id;

    private String title;

    private String uri;

    private String artwork_url;

    private String description;

    private String genre;

    private int bpm;

    private int playback_count;

    private int comment_count;

    private int release_year;

    public Track (){
//        id=0L;
//        title="";
//        uri="";
//        artwork_url ="";
//        description="";
//        genre = "dddddddddddddddddddddddddddddddd";
//        bpm=0;
//        playback_count=0;
//        comment_count=0;
//        release_year=0;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getUri() {
        return uri;
    }

    public String getArtwork_url() {
        return artwork_url;
    }

    public String getDescription() {
        return description;
    }

    public String getGenre() {
        return genre;
    }

    public int getBpm() {
        return bpm;
    }

    public int getPlayback_count() {
        return playback_count;
    }

    public int getComment_count() {
        return comment_count;
    }

    public int getRelease_year() {
        return release_year;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public void setArtwork_url(String artwork_url) {
        this.artwork_url = artwork_url;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setBpm(int bpm) {
        this.bpm = bpm;
    }

    public void setPlayback_count(int playback_count) {
        this.playback_count = playback_count;
    }

    public void setComment_count(int comment_count) {
        this.comment_count = comment_count;
    }

    public void setRelease_year(int release_year) {
        this.release_year = release_year;
    }

    @Override
    public String toString() {
        return "Id: "+ id + "\n" + " Title: " + title + "\n" + " URI: " + uri + "\n" + " Art_Work: "+ artwork_url + "\n"
                + " Description: " + description  + "\n"+ " Gene: "+ genre  + "\n" + " BPM: " + bpm
                + "\n"+ " PlayBack_Count: " + playback_count + "\n" + " Comment Count: " + comment_count
                + "\n" + " Release Year: " + release_year;
    }


    ////////////////////////////////////////////////////////////parceable///////////////////////////////////

    public void writeToParcel(Parcel out, int flags) {
        out.writeLong(id);
        out.writeString(title);
        out.writeString(uri);
        out.writeString(artwork_url);
        out.writeString(description);
        out.writeString(genre);
        out.writeInt(bpm);
        out.writeInt(playback_count);
        out.writeInt(comment_count);
        out.writeInt(release_year);
    }

    private Track(Parcel in) {
        id = in.readLong();
        title = in.readString();
        uri = in.readString();
        artwork_url = in.readString();
        description = in.readString();
        genre = in.readString();
        bpm = in.readInt();
        playback_count = in.readInt();
        comment_count = in.readInt();
        release_year = in.readInt();
    }

    public int describeContents() {
        return 0;
    }


    public static final Parcelable.Creator<Track> CREATOR
            = new Parcelable.Creator<Track>() {
        public Track createFromParcel(Parcel in) {
            return new Track(in);
        }

        public Track[] newArray(int size) {
            return new Track[size];
        }
    };



}
