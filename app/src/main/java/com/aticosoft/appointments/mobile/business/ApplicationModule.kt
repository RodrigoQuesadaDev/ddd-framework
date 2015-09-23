package com.aticosoft.appointments.mobile.business

import android.app.Application
import android.content.Context
import com.aticosoft.appointments.mobile.business.infrastructure.annotations.ForApplication
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.DomainModelModule
import com.aticosoft.appointments.mobile.business.infrastructure.persistence.PersistenceModule
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by rodrigo on 10/09/15.
 */
@Module(includes = arrayOf(DomainModelModule::class, PersistenceModule::class))
/*internal*/ class ApplicationModule(private val application: Application) {

    @Provides @Singleton @ForApplication fun provideApplicationContext(): Context = application

    @Provides fun provideApplicationInfo() = application.applicationInfo

    @Provides fun provideAssetManager() = application.assets
}