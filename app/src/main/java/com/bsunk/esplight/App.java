package com.bsunk.esplight;

import android.app.Application;
import android.support.annotation.NonNull;

import com.bsunk.esplight.data.components.DaggerNsdComponent;
import com.bsunk.esplight.data.components.NsdComponent;
import com.bsunk.esplight.data.modules.AppModule;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import timber.log.Timber;

/**
 * Created by Bharat on 12/18/2016.
 */

public class App extends Application {
    NsdComponent nsdComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);

        if(BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

        nsdComponent = DaggerNsdComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public NsdComponent getNsdComponent() {
        return nsdComponent;
    }

}
