package com.example.allengotstuff.soundcloudapp.databean;

/**
 * Created by allengotstuff on 4/5/2017.
 */

public class SoundCloudUser {

    private long id;

    private String username;

    //the base uri for soundcloud api request
    private String uri;

    public long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getUri() {
        return uri;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

}
