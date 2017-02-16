package com.bsunk.esplight.addModuleConfirm;

import com.bsunk.esplight.data.model.mDNSModule;

import javax.inject.Inject;

/**
 * Created by Bharat on 12/24/2016.
 */

public class AddModulesConfirmPresenter implements AddModulesConfirmContract.Presenter {

    AddModulesConfirmContract.View mView;

    @Inject
    AddModulesConfirmPresenter(AddModulesConfirmContract.View view) {
        mView = view;

    }

    public void onItemClickModule(mDNSModule module) {
        mView.setValues(module);
    }

    public void testConnection(String ipAddress, int port) {

    }

}
