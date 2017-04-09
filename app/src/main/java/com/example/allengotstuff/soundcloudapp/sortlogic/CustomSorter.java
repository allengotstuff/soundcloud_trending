package com.example.allengotstuff.soundcloudapp.sortlogic;

import com.example.allengotstuff.soundcloudapp.databean.Track;
import com.example.allengotstuff.soundcloudapp.sortlogic.comparator.BpmComparator;
import com.example.allengotstuff.soundcloudapp.utils.Logger;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by allengotstuff on 4/9/2017.
 */

public class CustomSorter implements BaseSorter<Track> {

    public CustomSorter() {}


    @Override
    public Observable<List<Track>> sort(List<Track> list, SORT_CATEGOTY categoty) {
        switch (categoty) {
            case BPM:
                Collections.sort(list,new BpmComparator());
                break;

            case PLAY_BACK_COUNT:

                break;

            case RELEASE_YEAR:

                break;


            case COMMENT_COUNT:

                break;
        }

        return Observable.just(list);
    }


}
