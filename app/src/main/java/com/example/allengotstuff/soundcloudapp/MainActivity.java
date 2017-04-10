package com.example.allengotstuff.soundcloudapp;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
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
import com.example.allengotstuff.soundcloudapp.gethotsongs.SongDetailActivity;
import com.example.allengotstuff.soundcloudapp.list.adapter.HotSongAdapter;
import com.example.allengotstuff.soundcloudapp.list.holder.HotSongHolder;
import com.example.allengotstuff.soundcloudapp.sortlogic.BaseSorter;
import com.example.allengotstuff.soundcloudapp.utils.Constant;
import com.example.allengotstuff.soundcloudapp.utils.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.schedulers.Schedulers;


public final class MainActivity extends AppCompatActivity implements HotSongContract.View<HotSongContract.Presenter>, View.OnClickListener, HotSongAdapter.OnRecyclerViewClickListener {

    private static final String TAG = "MainActivity";

    private List<Track> hotTracks;
    private Executor myExecutor;

    private HotSongContract.Presenter myPresenter;
    private ApiHelper helper;

    private HotSongAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private AlarmManager alarmManager;
    private PendingIntent alarmIntent;

    @BindView(R.id.myRecyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.loading_pb)
    ProgressBar myProgressbar;

    @BindView(R.id.bpm_sort_button)
    Button button_bpm;

    @BindView(R.id.most_played_sort_button)
    Button button_most_played;

    @BindView(R.id.most_commented_sort_button)
    Button button_most_comment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        init();
        initRecyclerView();
        initSortButton();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setupAlarmClock();
    }

    @Override
    protected void onPause() {
        super.onPause();
        myPresenter.stop();

        if (alarmManager != null)
            alarmManager.cancel(alarmIntent);
    }



    private void init() {
        helper = new ApiHelper(App.getHttpClient());
        myExecutor = App.getExecutor();

        myPresenter = new HotSongPresenter(this, helper, myExecutor);
        myPresenter.start();

        //init alarm data
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        alarmIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
    }

    private void initRecyclerView() {

        hotTracks = new ArrayList<>(150);

        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new HotSongAdapter(hotTracks);
        mRecyclerView.addItemDecoration(new HotSongAdapter.ItemDecoration(20));
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
    }

    private void initSortButton() {
        button_bpm.setOnClickListener(this);
        button_most_played.setOnClickListener(this);
        button_most_comment.setOnClickListener(this);
    }

    @Override
    public void showHotSongs(List hotsongs) {

        if (hotTracks.size() > 0)
            hotTracks.clear();

        if (hotsongs != null && hotsongs.size() > 0)
            hotTracks.addAll(hotsongs);

        mAdapter.notifyDataSetChanged();

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.bpm_sort_button:
                ((HotSongPresenter) myPresenter).sortTracks(BaseSorter.SORT_CATEGOTY.BPM);
                break;

            case R.id.most_played_sort_button:
                ((HotSongPresenter) myPresenter).sortTracks(BaseSorter.SORT_CATEGOTY.PLAY_BACK_COUNT);
                break;

            case R.id.most_commented_sort_button:
                ((HotSongPresenter) myPresenter).sortTracks(BaseSorter.SORT_CATEGOTY.COMMENT_COUNT);
                break;
        }
    }

    @Override
    public void showErrorMessage(String message) {

        Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT).show();
        Logger.log(TAG, " showing error message");
    }

    @Override
    public void setLoadingIndicator(boolean active) {
        if (active) {
            myProgressbar.setVisibility(View.VISIBLE);
        } else {
            myProgressbar.setVisibility(View.GONE);
        }
    }




    @Override
    public void onItemClick(HotSongHolder myholder, int position) {

        Track clickTrack = hotTracks.get(position);

        Intent intent = new Intent(getBaseContext(), SongDetailActivity.class);
        intent.putExtra(Constant.DELIVERY_TRACK, clickTrack);
        startActivity(intent);
    }

    private void setupAlarmClock() {
        alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime() + 60 * 1000,
                AlarmManager.INTERVAL_FIFTEEN_MINUTES, alarmIntent);
    }

    public final class AlarmReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            myPresenter.refreshHotSongs();

        }
    }
}
