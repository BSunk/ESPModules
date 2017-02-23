package com.bsunk.esplight.di.components;

import com.bsunk.esplight.addModule.AddModuleFragment;
import com.bsunk.esplight.di.modules.AddModuleViewModule;
import com.bsunk.esplight.util.CustomScope;

import dagger.Component;

/**
 * Created by Bharat on 12/20/2016.
 */
@CustomScope
@Component(dependencies = NsdComponent.class, modules = AddModuleViewModule.class)
public interface  AddModuleViewComponent {

    void inject(AddModuleFragment fragment);

}
