package com.example.allengotstuff.soundcloudapp.gethotsongs;

import android.support.annotation.NonNull;

import com.example.allengotstuff.soundcloudapp.App;
import com.example.allengotstuff.soundcloudapp.data.network.ApiHelper;
import com.example.allengotstuff.soundcloudapp.databean.SoundCloudUser;
import com.example.allengotstuff.soundcloudapp.databean.Track;
import com.example.allengotstuff.soundcloudapp.utils.GsonParser;
import com.example.allengotstuff.soundcloudapp.utils.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

/**
 * Created by allengotstuff on 4/6/2017.
 */

public class HotSongPresenter implements HotSongContract.Presenter {

    private static final String TAG = "HotSongPresenter";

    private HotSongContract.View myView;

    private ApiHelper myApiHelper;

    private Executor myExecutor;

    private List<Track> hotTracks;


    public HotSongPresenter(@NonNull HotSongContract.View view, @NonNull ApiHelper apiHelper, @NonNull Executor executor) {

        myView = checkNotNull(view);
        myApiHelper = checkNotNull(apiHelper);
        myExecutor = checkNotNull(executor);

        hotTracks = new ArrayList<>(150);
    }

    @Override
    public void start() {
        refreshHotSongs();
    }

    @Override
    public void refreshHotSongs() {

        myView.setLoadingIndicator(true);

        if(hotTracks.size()>0){
            hotTracks.clear();
        }

        myApiHelper.getHotTracksObservable(myExecutor)
                .subscribeOn(Schedulers.from(myExecutor))
                .filter(trackList -> trackList != null & trackList.size() > 0)
                .doOnComplete(() -> {
                            Logger.log(TAG, "complete");
                            myView.showHotSongs(hotTracks);
                            myView.setLoadingIndicator(false);})
                .doOnError(message -> {
                            myView.showErrorMessage();
                            myView.setLoadingIndicator(false);})
                .subscribe(trackList -> {
                    Logger.log(TAG, "adding one to the list, size: " + trackList.size());
                    hotTracks.addAll(trackList);
                });

    }


}
