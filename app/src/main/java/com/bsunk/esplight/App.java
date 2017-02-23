package com.bsunk.esplight;

import android.app.Application;

import com.bsunk.esplight.di.components.DaggerNsdComponent;
import com.bsunk.esplight.di.components.NsdComponent;
import com.bsunk.esplight.di.modules.AppModule;

import io.realm.Realm;
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
