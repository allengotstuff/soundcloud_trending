package com.example.allengotstuff.soundcloudapp.databean;

import java.util.List;

/**
 * Created by allengotstuff on 4/6/2017.
 */

public class FollowingList {

    private List<SoundCloudUser> collection;

    private String next_href;

    public List<SoundCloudUser> getCollection() {
        return collection;
    }

    public String getNext_href() {
        return next_href;
    }

    public void setCollection(List<SoundCloudUser> collection) {
        this.collection = collection;
    }

    public void setNext_href(String next_href) {
        this.next_href = next_href;
    }
}
