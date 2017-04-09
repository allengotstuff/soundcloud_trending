package com.example.allengotstuff.soundcloudapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.allengotstuff.soundcloudapp.data.network.ApiHelper;

import com.example.allengotstuff.soundcloudapp.databean.Track;
import com.example.allengotstuff.soundcloudapp.gethotsongs.HotSongContract;
import com.example.allengotstuff.soundcloudapp.gethotsongs.HotSongPresenter;
import com.example.allengotstuff.soundcloudapp.list.adapter.HotSongAdapter;
import com.example.allengotstuff.soundcloudapp.sortlogic.BaseSorter;
import com.example.allengotstuff.soundcloudapp.utils.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.schedulers.Schedulers;


public final class MainActivity extends AppCompatActivity implements HotSongContract.View<HotSongContract.Presenter>, View.OnClickListener{

    private static final String TAG = "MainActivity";

    private List<Track> hotTracks;
    private Executor myExecutor;

    private HotSongContract.Presenter myPresenter;
    private   ApiHelper helper;

    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @BindView (R.id.myRecyclerView)
    RecyclerView mRecyclerView;

    @BindView (R.id.loading_pb)
    ProgressBar myProgressbar;

    @BindView (R.id.bpm_sort_button)
    Button button_bpm;

    @BindView (R.id.most_played_sort_button)
    Button button_most_played;

    @BindView (R.id.new_relase_sort_button)
    Button button_new_relase;

    @BindView (R.id.most_commented_sort_button)
    Button button_most_comment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        init();
        initRecyclerView();
        initSortButton();

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
    }

    private void initRecyclerView(){

        hotTracks = new ArrayList<>(150);

        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new HotSongAdapter(hotTracks);
        mRecyclerView.addItemDecoration( new HotSongAdapter.ItemDecoration(20));
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initSortButton(){
        button_bpm.setOnClickListener(this);
        button_most_played.setOnClickListener(this);
        button_new_relase.setOnClickListener(this);
        button_most_comment.setOnClickListener(this);
    }

    @Override
    public void showHotSongs(List hotsongs) {

        if(hotTracks.size()>0)
            hotTracks.clear();

        if(hotsongs!=null && hotsongs.size()>0 )
             hotTracks.addAll(hotsongs);

        mAdapter.notifyDataSetChanged();

//        Logger.log(TAG," hot songs size: "+ hotsongs.size());

//        int i =0;
//        while(i<80){
//            Logger.log(TAG," hot songs bpm: "+ ((Track)hotsongs.get(i)).getBpm());
//            i++;
//        }
    }


    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.bpm_sort_button:
                ((HotSongPresenter)myPresenter).sortTracks(BaseSorter.SORT_CATEGOTY.BPM);
                break;

            case R.id.most_played_sort_button:
                ((HotSongPresenter)myPresenter).sortTracks(BaseSorter.SORT_CATEGOTY.PLAY_BACK_COUNT);
                break;

            case R.id.new_relase_sort_button:
                ((HotSongPresenter)myPresenter).sortTracks(BaseSorter.SORT_CATEGOTY.RELEASE_YEAR);
                break;

            case R.id.most_commented_sort_button:
                ((HotSongPresenter)myPresenter).sortTracks(BaseSorter.SORT_CATEGOTY.COMMENT_COUNT);
                break;
        }
    }

    @Override
    public void showErrorMessage(String message) {

        Toast.makeText(getBaseContext(),message,Toast.LENGTH_SHORT).show();
        Logger.log(TAG," showing error message");
    }

    @Override
    public void setLoadingIndicator(boolean active) {
       if(active){
           myProgressbar.setVisibility(View.VISIBLE);
       }else{
           myProgressbar.setVisibility(View.GONE);
       }
    }

    @Override
    protected void onStop() {
        super.onStop();
        myPresenter.stop();
    }


}
