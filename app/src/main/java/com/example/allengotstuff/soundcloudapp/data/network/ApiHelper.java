package com.example.allengotstuff.soundcloudapp.data.network;


import com.example.allengotstuff.soundcloudapp.databean.FollowingList;
import com.example.allengotstuff.soundcloudapp.databean.Track;
import com.example.allengotstuff.soundcloudapp.utils.GsonParser;
import com.example.allengotstuff.soundcloudapp.utils.Logger;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executor;


import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Created by allengotstuff on 4/5/2017.
 * <p>
 * <p>
 * Target user id: 1
 */

public class ApiHelper {

    //private static final String BASE_URL = "https://api.soundcloud.com/users/1/followings?client_id=ymymfMYLuW2UgqMxUCL3L9HcQv6Igjnc&page_size=20";

    private static final String TARGET_USER_ID = "1";

    private static final String TAG = "ApiHelper";

    private static final String CLIENT_ID = "ymymfMYLuW2UgqMxUCL3L9HcQv6Igjnc";

    private static final String BASE_URL = "https://api.soundcloud.com";

    private static final String USERS = "users";

    private static final String FOLLOWINGS = "followings";

    private static final String TRACK = "tracks";

    private static final String FAVORITES = "favorites";

    private OkHttpClient myHttpClient;

    public ApiHelper(OkHttpClient client) {
        myHttpClient = client;
    }


    /**
     *  network call to get target user's 20 most recent following users
     * @return  a observable of sting in json format
     */
    public Observable<String> getTargetFollowers() {

        return Observable.create(subscriber -> {

            HttpUrl.Builder urlBuilder = HttpUrl.parse(BASE_URL).newBuilder();
            urlBuilder.addEncodedPathSegment(USERS);
            //target user is the first
            urlBuilder.addEncodedPathSegments(TARGET_USER_ID);
            urlBuilder.addEncodedPathSegments(FOLLOWINGS);
            urlBuilder.addEncodedQueryParameter("client_id", CLIENT_ID);
            urlBuilder.addEncodedQueryParameter("page_size", "20");

            String url = urlBuilder.build().toString();
            Logger.log(TAG, url);

            Request request = new Request.Builder()
                    .url(url)
                    .build();
            try {

                Response response = myHttpClient.newCall(request).execute();
                if (response !=null & response.isSuccessful()) {
                    String data = response.body().string();
                    subscriber.onNext(data);
                }

            } catch (IOException  e) {

                subscriber.onError(e);

            }finally {
                subscriber.onComplete();
            }
        });
    }


    /**
     * retrieve a single user detail by providing its url
     * @param userId particular user's id
     * @return a observable of sting in json format
     */
    public Observable<String> getTargetUserInfo(long userId) {
        return Observable.create(subscriber ->
                {

                    HttpUrl.Builder urlBuilder = HttpUrl.parse(BASE_URL).newBuilder();
                    urlBuilder.addEncodedPathSegment("users");
                    urlBuilder.addEncodedPathSegments(String.valueOf(userId));
                    urlBuilder.addEncodedQueryParameter("client_id", CLIENT_ID);

                    String url = urlBuilder.build().toString();

                    Logger.log(TAG, url);


                    Request request = new Request.Builder()
                            .url(url)
                            .build();
                    try {

                        Response response = myHttpClient.newCall(request).execute();
                        if (response.isSuccessful()) {
                            String data = response.body().string();
                            subscriber.onNext(data);
                        }

                    } catch (IOException e) {
                        // notify error e.getMessage()
                        subscriber.onError(e);
                    }finally {
                        subscriber.onComplete();
                    }
                }
        );
    }



    /**
     *  network call to get particular track by id
     *
     * @param id particular track's id
     * @return a observable of sting in json format
     */
    public Observable<String> getTrack(long id) {

        return Observable.create(subscriber -> {

            HttpUrl.Builder urlBuilder = HttpUrl.parse(BASE_URL).newBuilder();
            urlBuilder.addEncodedPathSegment(TRACK);
            urlBuilder.addEncodedPathSegments(String.valueOf(id));
            urlBuilder.addEncodedQueryParameter("client_id", CLIENT_ID);

            String url = urlBuilder.build().toString();

            Logger.log(TAG, url);

            Request request = new Request.Builder()
                    .url(url)
                    .build();
            try {

                Response response = myHttpClient.newCall(request).execute();
                if (response.isSuccessful()) {
                    String data = response.body().string();
                    subscriber.onNext(data);
                }

            } catch (IOException e) {
                // notify error e.getMessage()
                subscriber.onError(e);
            }finally {
                subscriber.onComplete();
            }

        });
    }


    /**
     *  network call to get particular track by id
     *
     * @param userId particular user's id
     * @return a observable of sting in json format
     */
    public Observable<String> getFavoriotTracksByUser(long userId) {

        return Observable.create(subscriber -> {

            HttpUrl.Builder urlBuilder = HttpUrl.parse(BASE_URL).newBuilder();
            urlBuilder.addEncodedPathSegment(USERS);
            urlBuilder.addEncodedPathSegments(String.valueOf(userId));
            urlBuilder.addEncodedPathSegment(FAVORITES);
            urlBuilder.addEncodedQueryParameter("client_id", CLIENT_ID);

            String url = urlBuilder.build().toString();

            Logger.log(TAG, url);

            Request request = new Request.Builder()
                    .url(url)
                    .build();
            try {

                Response response = myHttpClient.newCall(request).execute();
                if (response.isSuccessful()) {
                    String data = response.body().string();
                    subscriber.onNext(data);
                }

            } catch (IOException e) {
                // notify error e.getMessage()
                subscriber.onError(e);
            }finally {
                subscriber.onComplete();
            }

        });
    }


    /**
     *  This is the main api call that glue together the logic to retrieve the hot songs list
     * @param executor
     * @return target user's most recent 20 followers's favorite tracks. Result unsorted, raw data.
     */
    public Observable<List<Track>> getHotTracksObservable(Executor executor){

        // get the target's user's lastest following users.
        Observable<FollowingList> lastestFollowers = getTargetFollowers()
                .map(response -> GsonParser.parseFollower(response));


        // get each follow users favorite track list. retrieve each user's favorite track list concurrently.
          Observable<List<Track>> followersFavoriteTracks = lastestFollowers.flatMap(followingList -> Observable.fromIterable(followingList.getCollection()))
                .flatMap(SoundCloudUser -> Observable.just(SoundCloudUser.getId()))
                .flatMap(userId -> getFavoriotTracksByUser(userId).map(json -> GsonParser.parseFavoriteTracks(json)).subscribeOn(Schedulers.from(executor)));

        return followersFavoriteTracks;

    }


}
