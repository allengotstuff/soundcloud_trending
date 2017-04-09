package com.example.allengotstuff.soundcloudapp.gethotsongs;

import android.support.annotation.NonNull;

import com.example.allengotstuff.soundcloudapp.data.network.ApiHelper;
import com.example.allengotstuff.soundcloudapp.databean.Track;
import com.example.allengotstuff.soundcloudapp.sortlogic.BaseSorter;
import com.example.allengotstuff.soundcloudapp.sortlogic.CustomSorter;
import com.example.allengotstuff.soundcloudapp.utils.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
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

    private Disposable hotTracksDispos;

    private BaseSorter mySorter;



    public HotSongPresenter(@NonNull HotSongContract.View view, @NonNull ApiHelper apiHelper, @NonNull Executor executor) {

        myView = checkNotNull(view);
        myApiHelper = checkNotNull(apiHelper);
        myExecutor = checkNotNull(executor);

        hotTracks = new ArrayList<>(150);

        mySorter = new CustomSorter();

    }

    @Override
    public void start() {
        refreshHotSongs();
    }

    @Override
    public void stop() {
        if(hotTracksDispos!=null & !hotTracksDispos.isDisposed()){
            hotTracksDispos.dispose();
        }
    }

    @Override
    public void refreshHotSongs() {

        myView.setLoadingIndicator(true);

        if(hotTracks.size()>0){
            hotTracks.clear();
        }

        hotTracksDispos = myApiHelper.getHotTracksObservable(myExecutor).onErrorResumeNext(throwable -> {

            if(hotTracks.size()==0){
                myView.showErrorMessage("error: didn't receive any data");
            }else{
                myView.showErrorMessage("error: some data is lost");
            }
            return Observable.just(hotTracks);
                }).subscribeOn(Schedulers.from(myExecutor))
                .filter(trackList -> trackList != null & trackList.size() > 0)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete(
                        // do the sorting logic when finish retrieve all the tracks from 20 followers
                        () ->sortTracks(BaseSorter.SORT_CATEGOTY.BPM)
                )
                .subscribe(trackList -> {
                    Logger.log(TAG, "adding one to the list, size: " + trackList.size());
                    hotTracks.addAll(trackList);
                });
    }


    public void sortTracks(BaseSorter.SORT_CATEGOTY categoty) {
        //do a sorting when sequence is finished
        mySorter.sort(hotTracks,categoty)
                .subscribeOn(Schedulers.from(myExecutor))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(finalList ->{
                    Logger.log(TAG, "complete");
                    myView.showHotSongs((List)finalList);
                    myView.setLoadingIndicator(false);
                });
    }


}
