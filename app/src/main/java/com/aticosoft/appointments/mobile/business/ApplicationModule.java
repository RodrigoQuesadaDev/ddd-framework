package com.aticosoft.appointments.mobile.business;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.res.AssetManager;

import com.aticosoft.appointments.mobile.business.infrastructure.annotations.ForApplication;
import com.aticosoft.appointments.mobile.business.infrastructure.persistence.PersistenceModule;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by rodrigo on 27/07/15.
 */
@Module(includes = PersistenceModule.class)
public class ApplicationModule {

    private Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    @Provides @Singleton @ForApplication Context provideApplicationContext() {
        return application;
    }

    @Provides ApplicationInfo provideApplicationInfo() {
        return application.getApplicationInfo();
    }

    @Provides AssetManager provideAssetManager() {
        return application.getAssets();
    }
}
