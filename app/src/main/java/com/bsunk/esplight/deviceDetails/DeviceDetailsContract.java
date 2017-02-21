package com.bsunk.esplight.deviceDetails;

import com.bsunk.esplight.data.model.LightModel;

/**
 * Created by Bharat on 2/21/2017.
 */

public interface DeviceDetailsContract {

    interface View {
        void setCurrentDeviceObject(LightModel lightModel);
        void setTitle(String title);
    }

    interface Presenter {
        void getRealmObject(String chipID);
    }

}
