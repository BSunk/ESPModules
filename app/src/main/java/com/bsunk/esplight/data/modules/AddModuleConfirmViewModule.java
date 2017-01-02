package com.bsunk.esplight.data.modules;
import com.bsunk.esplight.addModuleConfirm.AddModulesConfirmContract;
import com.bsunk.esplight.util.CustomScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Bharat on 12/24/2016.
 */

@Module
public class AddModuleConfirmViewModule  {
    private final AddModulesConfirmContract.View mView;

    public AddModuleConfirmViewModule(AddModulesConfirmContract.View view) {
        mView = view;
    }

    @Provides
    @CustomScope
    AddModulesConfirmContract.View providesAddModulesConfirmContractView() {
        return mView;
    }

}
