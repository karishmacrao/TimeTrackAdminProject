package com.example.timetrackadmin;

import android.app.Application;

import com.example.timetrackadmin.repository.SharedPreferenceConfig;
import com.facebook.stetho.Stetho;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SharedPreferenceConfig.init(this);
        Stetho.initializeWithDefaults(this);
    }
}
