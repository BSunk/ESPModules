package com.bsunk.esplight.devicesList;


import com.bsunk.esplight.data.model.LightModel;

import io.realm.Realm;
import io.realm.RealmQuery;

/**
 * Created by Bharat on 2/17/2017.
 */

public class DeviceListPresenter implements DeviceListContract.Presenter {

    DeviceListContract.View mView;
    Realm realm;

    DeviceListPresenter(DeviceListContract.View view) {
        mView = view;
        getDevices();
    }

    public void getDevices() {
        realm = Realm.getDefaultInstance();
        RealmQuery<LightModel> query = realm.where(LightModel.class);
        mView.showDevices(query.findAll());
    }

    public void onDestroy() {
        realm.close();
    }

}
