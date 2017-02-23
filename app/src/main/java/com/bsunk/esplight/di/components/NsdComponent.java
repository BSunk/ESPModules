package com.bsunk.esplight.di.components;

import com.bsunk.esplight.di.modules.AppModule;
import com.bsunk.esplight.di.modules.NsdModule;
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
