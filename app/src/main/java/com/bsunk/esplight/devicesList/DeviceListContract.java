package com.bsunk.esplight.devicesList;

import com.bsunk.esplight.data.model.LightModel;

import io.realm.RealmResults;

/**
 * Created by Bharat on 2/17/2017.
 */

public interface DeviceListContract {
    interface View {
        void showDevices(RealmResults<LightModel> lightModels);
    }

    interface Presenter {
        void getDevices();
        void onDestroy();
        void setBrightness(final String ip, final String port, final int brightness, final String chipID);
        void setPower(final String ip, final String port, final int value, final String chipID);
        void setPattern(final String ip, final String port, final int value, final String chipID);
        void setSolidColor(final String ip, final String port, final int r, final int g, final int b, final String chipID);
        void updateData();
    }
}
