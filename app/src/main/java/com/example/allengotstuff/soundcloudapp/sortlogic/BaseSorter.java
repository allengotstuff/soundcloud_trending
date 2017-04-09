package com.example.allengotstuff.soundcloudapp.sortlogic;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by allengotstuff on 4/9/2017.
 */

public interface BaseSorter<T> {

     enum SORT_CATEGOTY {
        BPM, PLAY_BACK_COUNT,  COMMENT_COUNT
    }

    Observable<List<T>> sort(List<T> list, SORT_CATEGOTY category);
}
