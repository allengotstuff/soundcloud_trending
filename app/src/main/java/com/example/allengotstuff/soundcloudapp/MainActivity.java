package com.example.allengotstuff.soundcloudapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.allengotstuff.soundcloudapp.data.network.ApiHelper;

import com.example.allengotstuff.soundcloudapp.databean.Track;
import com.example.allengotstuff.soundcloudapp.gethotsongs.HotSongContract;
import com.example.allengotstuff.soundcloudapp.gethotsongs.HotSongPresenter;
import com.example.allengotstuff.soundcloudapp.list.adapter.HotSongAdapter;
import com.example.allengotstuff.soundcloudapp.utils.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.schedulers.Schedulers;


public class MainActivity extends AppCompatActivity implements HotSongContract.View<HotSongContract.Presenter>{

    private static final String TAG = "MainActivity";

    private List<Track> hotTracks;
    private Executor myExecutor;

    private HotSongContract.Presenter myPresenter;
    private   ApiHelper helper;

    @BindView (R.id.myRecyclerView) RecyclerView mRecyclerView;

    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        initRecyclerView();

//        Logger.log(TAG, "begin network");
//        helper.getHotTracksObservable(myExecutor).onErrorReturnItem(hotTracks)
//                .subscribeOn(Schedulers.from(myExecutor))
//                .filter(trackList -> trackList != null & trackList.size() > 0)
//                .doOnComplete( () ->Logger.log(TAG, "complete" +hotTracks.size() )
//                ).doOnError(throwable-> Logger.log(TAG, throwable.getMessage()))
//                .subscribe(trackList -> {
//                    Logger.log(TAG, "adding one to the list, size: "+ trackList.size());
//                    hotTracks.addAll(trackList);
//                });

    }


    private void init(){
        helper = new ApiHelper(App.getHttpClient());
        myExecutor = App.getExecutor();

        myPresenter = new HotSongPresenter(this,helper,myExecutor);
        myPresenter.start();

        ButterKnife.bind(this);
    }

    private void initRecyclerView(){

        hotTracks = new ArrayList<>(150);

        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new HotSongAdapter(hotTracks);
        mRecyclerView.setAdapter(mAdapter);
    }



    @Override
    public void showHotSongs(List hotsongs) {

        if(hotTracks.size()>0)
            hotTracks.clear();

        if(hotsongs!=null && hotsongs.size()>0 )
             hotTracks.addAll(hotsongs);

        mAdapter.notifyDataSetChanged();

        Logger.log(TAG," hot songs size: "+ hotsongs.size());

        int i =0;
        while(i<80){
            Logger.log(TAG," hot songs bpm: "+ ((Track)hotsongs.get(i)).getBpm());
            i++;
        }

    }

    @Override
    public void showErrorMessage(String message) {

        Logger.log(TAG," showing error message");
    }

    @Override
    public void setLoadingIndicator(boolean active) {

    }

    @Override
    protected void onStop() {
        super.onStop();
        myPresenter.stop();
    }
}
