package com.example.allengotstuff.soundcloudapp.utils;

import android.util.Log;

import com.example.allengotstuff.soundcloudapp.BuildConfig;

/**
 * Created by allengotstuff on 4/6/2017.
 */

public class Logger {

    public static void log(String file, String message){
        if(BuildConfig.DEBUG){
            Log.i(file,message);
        }
    }
}
