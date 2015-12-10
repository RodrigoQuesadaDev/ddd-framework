package com.aticosoft.appointments.mobile.business

import com.aticosoft.appointments.mobile.business.infrastructure.domain.DomainModule
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.DomainModelModule
import com.aticosoft.appointments.mobile.business.infrastructure.persistence.PersistenceModule
import dagger.Module
import dagger.Provides
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 10/09/15.
 */
@Module(includes = arrayOf(DomainModule::class, PersistenceModule::class))
/*internal*/ class ApplicationModule(private val application: Application<*>) {

    @Provides @Singleton fun provideApplication(): Application<*> = application

    @Provides fun provideApplicationInfo() = application.applicationInfo

    @Provides fun provideAssetManager() = application.assets

    @Singleton
    class PostInitializer @Inject protected constructor(
            private val persistencePostInitializer: PersistenceModule.PostInitializer,
            private val domainModelPostInitializer: DomainModelModule.PostInitializer
    ) : ModulePostInitializer {

        override fun init() {
            arrayOf(persistencePostInitializer, domainModelPostInitializer).forEach { it.init() }
        }
    }
}

interface ModulePostInitializer {
    fun init()
}