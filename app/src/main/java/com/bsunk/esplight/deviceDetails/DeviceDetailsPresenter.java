package com.bsunk.esplight.deviceDetails;

import com.bsunk.esplight.data.model.LightModel;

import io.realm.Realm;

/**
 * Created by Bharat on 2/21/2017.
 */

public class DeviceDetailsPresenter implements DeviceDetailsContract.Presenter {


    private DeviceDetailsContract.View mView;

    DeviceDetailsPresenter(DeviceDetailsContract.View view) {
        mView=view;
    }

    public void getRealmObject(String chipID) {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(realm1 -> {
            LightModel lightModel = realm.where(LightModel.class).equalTo("chipID", chipID).findFirst();
            if(lightModel!=null) {
                mView.setCurrentDeviceObject(lightModel);
                mView.setTitle(lightModel.getName());
            }
        });
    }

}
