package com.example.allengotstuff.soundcloudapp;

import android.support.test.runner.AndroidJUnit4;

import com.example.allengotstuff.soundcloudapp.data.network.ApiHelper;
import com.example.allengotstuff.soundcloudapp.databean.Track;
import com.example.allengotstuff.soundcloudapp.sortlogic.CustomSorter;
import com.example.allengotstuff.soundcloudapp.utils.Logger;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

/**
 * Created by allengotstuff on 4/9/2017.
 */

@RunWith(AndroidJUnit4.class)
public class SortingLogicTest {

    public CustomSorter mySorter;
    public ApiHelper myApiHelper;
    public OkHttpClient myOkHttpClient;
    public ThreadPoolExecutor myExecutor;
    public List<Track> result;

    @Before
    public void setup() {
        mySorter = new CustomSorter();
        myOkHttpClient = App.getHttpClient();
        myApiHelper = new ApiHelper(myOkHttpClient);

        myExecutor = App.getExecutor();

        result = new ArrayList<>();

        for(int i =0; i<10; i++){
            Track track = new Track();
            track.setBpm(i);
            track.setRelease_year(i);
            track.setComment_count(i);
            track.setPlayback_count(i);
            result.add(track);
        }

    }


    @Test
    public void mostCommentSortLogic(){

        TestObserver<List<Track>> testSubscriber = new TestObserver<>();

        mySorter.sort(result, CustomSorter.SORT_CATEGOTY.BPM)
                .subscribe(testSubscriber);

        testSubscriber.assertComplete();
        testSubscriber.assertNoErrors();
        testSubscriber.assertValueCount(1);

        List<Track> result = testSubscriber.values().get(0);

        assertThat(result.get(0).getComment_count(), is(9));
        assertThat(result.get(1).getComment_count(), is(8));
        assertThat(result.get(2).getComment_count(), is(7));
        assertThat(result.get(3).getComment_count(), is(6));
        assertThat(result.get(4).getComment_count(), is(5));
        assertThat(result.get(5).getComment_count(), is(4));
        assertThat(result.get(6).getComment_count(), is(3));
        assertThat(result.get(7).getComment_count(), is(2));
        assertThat(result.get(8).getComment_count(), is(1));
        assertThat(result.get(9).getComment_count(), is(0));
    }

    @Test
    public void newReleaseSortLogic(){

        TestObserver<List<Track>> testSubscriber = new TestObserver<>();

        mySorter.sort(result, CustomSorter.SORT_CATEGOTY.BPM)
                .subscribe(testSubscriber);

        testSubscriber.assertComplete();
        testSubscriber.assertNoErrors();
        testSubscriber.assertValueCount(1);

        List<Track> result = testSubscriber.values().get(0);

        assertThat(result.get(0).getRelease_year(), is(9));
        assertThat(result.get(1).getRelease_year(), is(8));
        assertThat(result.get(2).getRelease_year(), is(7));
        assertThat(result.get(3).getRelease_year(), is(6));
        assertThat(result.get(4).getRelease_year(), is(5));
        assertThat(result.get(5).getRelease_year(), is(4));
        assertThat(result.get(6).getRelease_year(), is(3));
        assertThat(result.get(7).getRelease_year(), is(2));
        assertThat(result.get(8).getRelease_year(), is(1));
        assertThat(result.get(9).getRelease_year(), is(0));
    }

    @Test
    public void MostPlayedSortLogic(){

        TestObserver<List<Track>> testSubscriber = new TestObserver<>();

        mySorter.sort(result, CustomSorter.SORT_CATEGOTY.BPM)
                .subscribe(testSubscriber);

        testSubscriber.assertComplete();
        testSubscriber.assertNoErrors();
        testSubscriber.assertValueCount(1);

        List<Track> result = testSubscriber.values().get(0);

        assertThat(result.get(0).getPlayback_count(), is(9));
        assertThat(result.get(1).getPlayback_count(), is(8));
        assertThat(result.get(2).getPlayback_count(), is(7));
        assertThat(result.get(3).getPlayback_count(), is(6));
        assertThat(result.get(4).getPlayback_count(), is(5));
        assertThat(result.get(5).getPlayback_count(), is(4));
        assertThat(result.get(6).getPlayback_count(), is(3));
        assertThat(result.get(7).getPlayback_count(), is(2));
        assertThat(result.get(8).getPlayback_count(), is(1));
        assertThat(result.get(9).getPlayback_count(), is(0));
    }


    @Test
    public void bpmSortLogic(){

        TestObserver<List<Track>> testSubscriber = new TestObserver<>();

        mySorter.sort(result, CustomSorter.SORT_CATEGOTY.BPM)
                .subscribe(testSubscriber);

        testSubscriber.assertComplete();
        testSubscriber.assertNoErrors();
        testSubscriber.assertValueCount(1);

        List<Track> result = testSubscriber.values().get(0);

        assertThat(result.get(0).getBpm(), is(9));
        assertThat(result.get(1).getBpm(), is(8));
        assertThat(result.get(2).getBpm(), is(7));
        assertThat(result.get(3).getBpm(), is(6));
        assertThat(result.get(4).getBpm(), is(5));
        assertThat(result.get(5).getBpm(), is(4));
        assertThat(result.get(6).getBpm(), is(3));
        assertThat(result.get(7).getBpm(), is(2));
        assertThat(result.get(8).getBpm(), is(1));
        assertThat(result.get(9).getBpm(), is(0));
    }

}
