package com.example.allengotstuff.soundcloudapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.allengotstuff.soundcloudapp.data.network.ApiHelper;

import com.example.allengotstuff.soundcloudapp.databean.Track;
import com.example.allengotstuff.soundcloudapp.gethotsongs.HotSongContract;
import com.example.allengotstuff.soundcloudapp.gethotsongs.HotSongPresenter;
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

    @BindView (R.id.myRecyclerView) RecyclerView mRecyclerView;

    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

//        Logger.log(TAG, "begin network");
//        helper.getHotTracksObservable(myExecutor)
//                .subscribeOn(Schedulers.from(myExecutor))
//                .filter(trackList -> trackList != null & trackList.size() > 0)
//                .doOnComplete( () ->Logger.log(TAG, "complete" +hotTracks.size() )
//                )
//                .subscribe(trackList -> {
//                    Logger.log(TAG, "adding one to the list, size: "+ trackList.size());
//                    hotTracks.addAll(trackList);
//                });

    }


    private void init(){
        ApiHelper helper = new ApiHelper(App.getHttpClient());
        myExecutor = App.getExecutor();

        myPresenter = new HotSongPresenter(this,helper,myExecutor);
        myPresenter.start();

        ButterKnife.bind(this);
    }

    private void initView(){

        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
    }



    @Override
    public void showHotSongs(List hotsongs) {
        hotTracks = hotsongs;
        Logger.log(TAG," hot songs size: "+ hotsongs.size());

        int i =0;
        while(i<20){
            Logger.log(TAG," hot songs size: "+ hotsongs.get(i).toString());
            i++;
        }
    }

    @Override
    public void showErrorMessage() {

    }

    @Override
    public void setLoadingIndicator(boolean active) {

    }


}
