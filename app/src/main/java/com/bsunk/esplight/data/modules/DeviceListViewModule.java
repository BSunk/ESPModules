package com.bsunk.esplight.data.modules;

import com.bsunk.esplight.devicesList.DeviceListContract;
import com.bsunk.esplight.util.CustomScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Bharat on 12/20/2016.
 */
@Module
public class DeviceListViewModule {
    private final DeviceListContract.View mView;

    public DeviceListViewModule(DeviceListContract.View view) {
        mView = view;
    }

    @Provides
    @CustomScope
    DeviceListContract.View providesMainScreenContractView() {
        return mView;
    }

}

