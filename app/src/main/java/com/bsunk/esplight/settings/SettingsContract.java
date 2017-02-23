package com.bsunk.esplight.settings;

import com.bsunk.esplight.data.model.LightModel;

import java.util.List;

/**
 * Created by Bharat on 2/23/2017.
 */

public interface SettingsContract {

    interface View {
        void showDevices(List<LightModel> devices);
    }
    interface Presenter {
        void getDeviceListFromRealm();
    }

}
