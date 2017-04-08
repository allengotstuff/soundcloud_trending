package com.example.allengotstuff.soundcloudapp.utils;

import com.example.allengotstuff.soundcloudapp.databean.FollowingList;
import com.example.allengotstuff.soundcloudapp.databean.SoundCloudUser;
import com.example.allengotstuff.soundcloudapp.databean.Track;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * Created by allengotstuff on 4/6/2017.
 */

public class GsonParser {

    public static SoundCloudUser parseUser(String reponse){
        Gson gson = new GsonBuilder().create();
        SoundCloudUser user = gson.fromJson(reponse, SoundCloudUser.class);
        return user;
    }

    public static FollowingList parseFollower(String response){
        Gson gson = new GsonBuilder().create();
        FollowingList list = gson.fromJson(response, FollowingList.class);
        return list;
    }

    public static Track parseTrack(String response){
        Gson gson = new GsonBuilder().create();
        Track track =  gson.fromJson(response, Track.class);
        return track;
    }

    public static List<Track> parseFavoriteTracks(String response){
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(response, new TypeToken<List<Track>>(){}.getType());
    }

}
