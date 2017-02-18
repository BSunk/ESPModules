package com.bsunk.esplight.addModuleConfirm;

import com.bsunk.esplight.data.model.LightModel;
import com.bsunk.esplight.data.model.mDNSModule;

/**
 * Created by Bharat on 12/24/2016.
 */

public interface AddModulesConfirmContract {
    interface View {
        void setValues(mDNSModule module);
        void setName(String name);
        void showValidation(boolean show);
        void showProgressBar(boolean show);
        void saveComplete(boolean isSaved);
        void showDuplicateMessage();
    }

    interface Presenter {

        void onItemClickModule(mDNSModule module);
        void testConnection(String ipAddress, int port);
        void saveConnection(LightModel module);
        void onStop();

    }
}
