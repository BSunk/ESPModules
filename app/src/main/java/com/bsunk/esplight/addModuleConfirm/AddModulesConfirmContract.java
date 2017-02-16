package com.bsunk.esplight.addModuleConfirm;

import com.bsunk.esplight.data.model.mDNSModule;

/**
 * Created by Bharat on 12/24/2016.
 */

public interface AddModulesConfirmContract {
    interface View {
        void setValues(mDNSModule module);
        void setName(String name);
    }

    interface Presenter {

        void onItemClickModule(mDNSModule module);
        void testConnection(String ipAddress, int port);

    }
}
