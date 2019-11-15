package com.vjsm.sports.adszworld;

import android.app.Application;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;

import java.util.HashMap;
import java.util.Map;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        final FirebaseRemoteConfig remoteConfig=FirebaseRemoteConfig.getInstance();
        Map<String,Object> defaultValue=new HashMap<>();
        defaultValue.put(UpdateHelper.Key_Enable,false);
        defaultValue.put(UpdateHelper.Key_Version,"1.0");
        defaultValue.put(UpdateHelper.Key_url_,"appstoreurl");
        remoteConfig.setDefaults(defaultValue);
        remoteConfig.fetch(5).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                remoteConfig.activateFetched();
            }
        });

    }
}
