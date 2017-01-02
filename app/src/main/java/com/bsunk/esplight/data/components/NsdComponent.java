package com.bsunk.esplight.data.components;

import com.bsunk.esplight.data.modules.AppModule;
import com.bsunk.esplight.data.modules.NsdModule;
import com.bsunk.esplight.helper.NsdHelper;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Bharat on 12/18/2016.
 */
@Singleton
@Component(modules = {AppModule.class, NsdModule.class})
public interface NsdComponent {

    NsdHelper provideNsdHelper();

}
