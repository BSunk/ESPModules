package com.bsunk.esplight.di.components;

import com.bsunk.esplight.addModuleConfirm.AddModuleConfirmDialogFragment;
import com.bsunk.esplight.di.modules.AddModuleConfirmViewModule;
import com.bsunk.esplight.util.CustomScope;

import dagger.Component;

/**
 * Created by Bharat on 12/24/2016.
 */

@CustomScope
@Component(modules = AddModuleConfirmViewModule.class)
public interface  AddModuleConfirmViewComponent {

    void inject(AddModuleConfirmDialogFragment fragment);

}
