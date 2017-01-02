package com.bsunk.esplight.data.modules;

import android.app.Application;

import com.bsunk.esplight.helper.NsdHelper;

import javax.inject.Singleton;
import dagger.Module;
import dagger.Provides;

/**
 * Created by Bharat on 12/18/2016.
 */

@Module
public class NsdModule {

    public NsdModule() {
    }

    @Provides
    @Singleton
    NsdHelper provideNsdHelper(Application application) {
        return new NsdHelper(application);
    }

}
