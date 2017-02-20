package com.bsunk.esplight.devicesList;

import android.view.View;

import com.bsunk.esplight.data.model.LightModel;

/**
 * Created by Bharat on 2/20/2017.
 */

public interface ClickListener {
    void onDeviceClick(LightModel clickedDevice, View v);
}
