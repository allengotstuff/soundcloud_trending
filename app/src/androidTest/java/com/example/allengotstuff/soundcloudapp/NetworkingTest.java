package com.example.allengotstuff.soundcloudapp;

import android.os.Parcel;
import android.support.test.filters.LargeTest;
import android.support.test.filters.MediumTest;
import android.support.test.runner.AndroidJUnit4;

import com.example.allengotstuff.soundcloudapp.data.network.ApiHelper;
import com.example.allengotstuff.soundcloudapp.databean.FollowingList;
import com.example.allengotstuff.soundcloudapp.databean.SoundCloudUser;
import com.example.allengotstuff.soundcloudapp.databean.Track;
import com.example.allengotstuff.soundcloudapp.utils.GsonParser;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;


import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.TestObserver;

import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;


import static java.util.concurrent.TimeUnit.SECONDS;
import static org.hamcrest.Matchers.isEmptyOrNullString;

import static org.hamcrest.Matchers.iterableWithSize;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.notNullValue;

import static org.junit.Assert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Created by allengotstuff on 4/6/2017.
 */

@RunWith(AndroidJUnit4.class)
public class NetworkingTest {

    public ApiHelper myApiHelper;
    public OkHttpClient myOkHttpClient;
    public ThreadPoolExecutor myExecutor;

    @Before
    public void createLogHistory() {

        myOkHttpClient = App.getHttpClient();
        myApiHelper = new ApiHelper(myOkHttpClient);

        myExecutor = App.getExecutor();

    }


    /**
     * testing network call on target users's followers in json string
     */
    @Test
    @MediumTest
    public void retrieveFollowersInString() {
        TestObserver<String> testSubscriber = new TestObserver<>();

        myApiHelper.getTargetFollowers()
                .subscribeOn(Schedulers.from(myExecutor))
                .subscribe(testSubscriber);

        testSubscriber.awaitTerminalEvent(3, SECONDS);
        testSubscriber.assertComplete();
        testSubscriber.assertNoErrors();
        testSubscriber.assertValueCount(1);

        assertThat(testSubscriber.values().get(0), not(isEmptyOrNullString()));
    }


    /**
     * 1. retrieve target user's following list in String
     * <p>
     * (isolation)2. parse json string to Followinglist & check if the followinglist contains 20 users
     */
    @Test
    @MediumTest
    public void convertFollowUserGson() {

        TestObserver<FollowingList> testSubscriber = new TestObserver<>();

        myApiHelper.getTargetFollowers()
                .subscribeOn(Schedulers.from(myExecutor))
                .map(response -> GsonParser.parseFollower(response))
                .subscribe(testSubscriber);

        testSubscriber.awaitTerminalEvent(3, SECONDS);
        testSubscriber.assertComplete();
        testSubscriber.assertNoErrors();
        testSubscriber.assertValueCount(1);

        FollowingList followeingList = testSubscriber.values().get(0);

        assertThat(followeingList, notNullValue());
        // only request 20 followings of target user.
        assertThat(followeingList.getCollection().size(), is(20));
        assertThat(followeingList.getNext_href(), not(isEmptyOrNullString()));
    }

    /**
     * network call to retrieve particular user base on user id, and get result in string.
     */
    @Test
    @MediumTest
    public void retrieveUserInforInString() {

        TestObserver<String> testSubscriber = new TestObserver<>();

        myApiHelper.getTargetUserInfo(1)
                .subscribeOn(Schedulers.from(myExecutor))
                .subscribe(testSubscriber);

        testSubscriber.awaitTerminalEvent(3, SECONDS);
        testSubscriber.assertComplete();
        testSubscriber.assertNoErrors();
        testSubscriber.assertValueCount(1);


        assertThat(testSubscriber.values().get(0), not(isEmptyOrNullString()));

    }


    /**
     * 1. retrieve target users json string
     * <p>
     * (isolation) 2. parse it to SoundCloudUser object using Gson
     */
    @Test
    @MediumTest
    public void convertUserGson() {
        TestObserver<SoundCloudUser> testSubscriber = new TestObserver<>();

        myApiHelper.getTargetUserInfo(1)
                .subscribeOn(Schedulers.from(myExecutor))
                .map(response -> GsonParser.parseUser(response))
                .subscribe(testSubscriber);

        testSubscriber.awaitTerminalEvent(3, SECONDS);
        testSubscriber.assertComplete();
        testSubscriber.assertNoErrors();
        testSubscriber.assertValueCount(1);

        SoundCloudUser user = testSubscriber.values().get(0);


        assertThat(user, notNullValue());

        assertThat(user.getId(), is(1L));

        assertThat(user.getUsername(), not(isEmptyOrNullString()));

        assertThat(user.getUri(), not(isEmptyOrNullString()));

    }


    /**
     * network call to retrieve track infor in sting
     */
    @Test
    @MediumTest
    public void retrieveTrack() {
        TestObserver<String> testSubscriber = new TestObserver<>();


        //try track id 13158665 as example
        myApiHelper.getTrack(13158665)
                .subscribeOn(Schedulers.from(myExecutor))
                .subscribe(testSubscriber);

        testSubscriber.awaitTerminalEvent(3, SECONDS);
        testSubscriber.assertComplete();
        testSubscriber.assertNoErrors();
        testSubscriber.assertValueCount(1);

        assertThat(testSubscriber.values().get(0), not(isEmptyOrNullString()));

    }


    /**
     * parse json string to Track
     */
    @MediumTest
    @Test
    public void parseTrackGson() {

        TestObserver<Track> testSubscriber = new TestObserver<>();

        //try track id 13158665 as example
        myApiHelper.getTrack(13158665)
                .subscribeOn(Schedulers.from(myExecutor))
                .map(response -> GsonParser.parseTrack(response))
                .subscribe(testSubscriber);

        testSubscriber.awaitTerminalEvent(3, SECONDS);
        testSubscriber.assertComplete();
        testSubscriber.assertNoErrors();
        testSubscriber.assertValueCount(1);

        Track track = testSubscriber.values().get(0);

        assertThat(track.getId(), is(13158665l));
        assertThat(track.getUri(), not(isEmptyOrNullString()));
//        assertThat(track.getArtwork_url(), not(isEmptyOrNullString()));
//        assertThat(track.getBpm(), not(0));
//        assertThat(track.getComment_count(), not(0));
//        assertThat(track.getDescription(), not(isEmptyOrNullString()));
//        assertThat(track.getGenre(), not(isEmptyOrNullString()));
//        assertThat(track.getPlayback_count(), not(0));
//        assertThat(track.getTitle(), not(isEmptyOrNullString()));

    }

    @MediumTest
    @Test
    public void getUserFavoriteTracks() {

        TestObserver<String> testSubscriber = new TestObserver<>();

        //try track id 13158665 as example
        myApiHelper.getFavoriotTracksByUser(1)
                .subscribeOn(Schedulers.from(myExecutor))
                .subscribe(testSubscriber);

        testSubscriber.awaitTerminalEvent(3, SECONDS);
        testSubscriber.assertComplete();
        testSubscriber.assertNoErrors();
        testSubscriber.assertValueCount(1);

        assertThat(testSubscriber.values().get(0), not(isEmptyOrNullString()));

    }

    @MediumTest
    @Test
    public void parseFavoriteTracksGson() {

        TestObserver<List<Track>> testSubscriber = new TestObserver<>();

        myApiHelper.getFavoriotTracksByUser(1)
                .subscribeOn(Schedulers.from(myExecutor))
                .map(response -> GsonParser.parseFavoriteTracks(response))
                .subscribe(testSubscriber);

        testSubscriber.awaitTerminalEvent(3, SECONDS);
        testSubscriber.assertComplete();
        testSubscriber.assertNoErrors();
        testSubscriber.assertValueCount(1);


        assertThat(testSubscriber.values().get(0).size(), not(0));

    }


    /**
     * testing the core networking logic to retrieve target user's 20 lastest followers' favorite songs
     */
    @LargeTest
    @Test
    public void getHotTracks() {

        TestObserver<List<Track>> testSubscriber = new TestObserver<>();
        myApiHelper.getHotTracksObservable(myExecutor)
                .subscribeOn(Schedulers.from(myExecutor))
                .subscribe(testSubscriber);


        testSubscriber.awaitTerminalEvent(3, SECONDS);

        testSubscriber.assertComplete();
        testSubscriber.assertNoErrors();
        testSubscriber.assertValueCount(20);


        assertThat(testSubscriber.values().size(), is(20));

    }


    @Test
    public void parceable_success(){
        Track track = new Track();
        track.setId(999);
        track.setTitle("dsfsdf");
        track.setUri("www.sdfsdf");
        track.setArtwork_url("sdfsdfsadf.png");
        track.setDescription("sdfsdfsdf");
        track.setGenre("sdfsdf");
        track.setBpm(1230123);
        track.setPlayback_count(12);
        track.setComment_count(122343);
        track.setRelease_year(9921);

        Parcel parcel = Parcel.obtain();
        track.writeToParcel(parcel, track.describeContents());
        parcel.setDataPosition(0);

        Track createdFromParcel = Track.CREATOR.createFromParcel(parcel);

        assertThat(createdFromParcel.getId(), is(track.getId()));
        assertThat(createdFromParcel.getTitle(), is(track.getTitle()));

        assertThat(createdFromParcel.getUri(), is(track.getUri()));
        assertThat(createdFromParcel.getArtwork_url(), is(track.getArtwork_url()));

        assertThat(createdFromParcel.getDescription(), is(track.getDescription()));
        assertThat(createdFromParcel.getGenre(), is(track.getGenre()));
        assertThat(createdFromParcel.getBpm(), is(track.getBpm()));
        assertThat(createdFromParcel.getPlayback_count(), is(track.getPlayback_count()));
        assertThat(createdFromParcel.getComment_count(), is(track.getComment_count()));
        assertThat(createdFromParcel.getRelease_year(), is(track.getRelease_year()));



    }

}
