package com.bsunk.esplight.data.components;

import com.bsunk.esplight.data.modules.DeviceListViewModule;
import com.bsunk.esplight.devicesList.DeviceListFragment;
import com.bsunk.esplight.util.CustomScope;

import dagger.Component;

/**
 * Created by Bharat on 12/20/2016.
 */
@CustomScope
@Component(modules = DeviceListViewModule.class)
public interface DeviceListViewComponent {

    void inject(DeviceListFragment fragment);

}
