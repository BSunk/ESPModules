package com.bsunk.esplight.di.modules;

import com.bsunk.esplight.addModule.AddModulesContract;
import com.bsunk.esplight.util.CustomScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Bharat on 12/20/2016.
 */
@Module
public class AddModuleViewModule {
    private final AddModulesContract.View mView;

    public AddModuleViewModule(AddModulesContract.View view) {
        mView = view;
    }

    @Provides
    @CustomScope
    AddModulesContract.View providesMainScreenContractView() {
        return mView;
    }

}

