package com.bsunk.esplight.devicesList;

import com.bsunk.esplight.data.model.LightModel;

import io.realm.RealmResults;

/**
 * Created by Bharat on 2/17/2017.
 */

public interface DeviceListContract {
    interface View {
        void showDevices(RealmResults<LightModel> lightModels);
        void updatedData();
    }

    interface Presenter {
        void getDevices();
        void onDestroy();
    }
}
