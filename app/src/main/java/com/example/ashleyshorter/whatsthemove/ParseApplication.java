package com.example.ashleyshorter.whatsthemove;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        ParseObject.registerSubclass(com.example.ashleyshorter.whatsthemove.Post.class);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("wtm-sas")
                .clientKey("sas123")
                .server("https://wtm-sas.herokuapp.com/parse").build());
    }
}
