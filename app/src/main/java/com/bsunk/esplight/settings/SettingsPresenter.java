package com.bsunk.esplight.settings;

import com.bsunk.esplight.data.model.LightModel;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmQuery;

/**
 * Created by Bharat on 2/23/2017.
 */

public class SettingsPresenter implements SettingsContract.Presenter {

    private SettingsContract.View mView;

    SettingsPresenter(SettingsContract.View view) {
        mView=view;
    }

    public void getDeviceListFromRealm() {
        RealmQuery<LightModel> query = Realm.getDefaultInstance().where(LightModel.class);
        List<LightModel> devices = query.findAll();
        mView.showDevices(devices);
    }

}
