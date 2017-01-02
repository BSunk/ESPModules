package com.bsunk.esplight.data.components;

import com.bsunk.esplight.addModule.AddModuleFragment;
import com.bsunk.esplight.data.modules.AddModuleViewModule;
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
