package com.example.allengotstuff.soundcloudapp.gethotsongs;

import com.example.allengotstuff.soundcloudapp.BasePresenter;
import com.example.allengotstuff.soundcloudapp.BaseView;
import com.example.allengotstuff.soundcloudapp.sortlogic.BaseSorter;

import java.util.List;

import io.reactivex.Observable;

/**
 * Created by allengotstuff on 4/5/2017.
 */

public interface HotSongContract {

    interface View<T> extends BaseView<T>{

        void showHotSongs(List hotsongs);

        void showErrorMessage(String message);

        void setLoadingIndicator(boolean active);

    }

    interface Presenter extends BasePresenter{

        void refreshHotSongs();

    }
}
