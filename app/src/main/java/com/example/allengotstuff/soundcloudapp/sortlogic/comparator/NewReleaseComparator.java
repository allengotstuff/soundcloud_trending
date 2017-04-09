package com.example.allengotstuff.soundcloudapp.sortlogic.comparator;

import com.example.allengotstuff.soundcloudapp.databean.Track;

import java.util.Comparator;

/**
 * Created by allengotstuff on 4/9/2017.
 */

public class NewReleaseComparator  implements Comparator<Track> {

    @Override
    public int compare(Track o1, Track o2) {
        int bpm_1 = o1.getRelease_year();
        int bpm_2 = o2.getRelease_year();
        return bpm_2 -bpm_1;
    }
}
