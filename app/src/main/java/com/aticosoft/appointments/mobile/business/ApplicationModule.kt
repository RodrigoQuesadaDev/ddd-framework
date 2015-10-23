package com.aticosoft.appointments.mobile.business

import android.app.Application
import android.content.Context
import com.aticosoft.appointments.mobile.business.infrastructure.annotations.ForApplication
import com.aticosoft.appointments.mobile.business.infrastructure.domain.DomainModule
import com.aticosoft.appointments.mobile.business.infrastructure.persistence.PersistenceModule
import dagger.Module
import dagger.Provides
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by rodrigo on 10/09/15.
 */
@Module(includes = arrayOf(DomainModule::class, PersistenceModule::class))
/*internal*/ class ApplicationModule(private val application: Application) {

    @Provides @Singleton @ForApplication fun provideApplicationContext(): Context = application

    @Provides fun provideApplicationInfo() = application.applicationInfo

    @Provides fun provideAssetManager() = application.assets

    @Singleton
    class PostInitializer @Inject constructor(private val persisPostInitializer: PersistenceModule.PostInitializer) : ModulePostInitializer {

        override fun init() {
            persisPostInitializer.init()
        }
    }
}