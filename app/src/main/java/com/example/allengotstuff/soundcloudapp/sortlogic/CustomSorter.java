package com.example.allengotstuff.soundcloudapp.sortlogic;

import com.example.allengotstuff.soundcloudapp.databean.Track;
import com.example.allengotstuff.soundcloudapp.sortlogic.comparator.BpmComparator;
import com.example.allengotstuff.soundcloudapp.sortlogic.comparator.MostCommentComparator;
import com.example.allengotstuff.soundcloudapp.sortlogic.comparator.NewReleaseComparator;
import com.example.allengotstuff.soundcloudapp.sortlogic.comparator.PlayCountComparator;
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
                Collections.sort(list,new PlayCountComparator());
                break;

            case COMMENT_COUNT:
                Collections.sort(list,new MostCommentComparator());
                break;
        }

        return Observable.just(list);
    }


}
