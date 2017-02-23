package com.bsunk.esplight.di.components;

import com.bsunk.esplight.di.modules.NetModule;
import com.bsunk.esplight.data.rest.DeviceAccess;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Bharat on 2/20/2017.
 */

@Singleton
@Component(modules = { NetModule.class})

public interface NetComponent {
    void inject(DeviceAccess deviceAccess);
}
