package com.bsunk.esplight.addModule;

import com.bsunk.esplight.data.model.mDNSModule;

/**
 * Created by Bharat on 12/15/2016.
 */

public interface AddModulesContract {

    interface View {
        void initializeList();
        void addModuleToList(mDNSModule module);
        void resetList();
        void startSearchAnimation(boolean isTrue);
        void showEmptyMessage(boolean isTrue);
    }

    interface Presenter {
        void onDestroy();
        void startDiscoveryTimeOut();
    }

}
