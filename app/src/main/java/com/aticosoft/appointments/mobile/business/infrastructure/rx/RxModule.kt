package com.aticosoft.appointments.mobile.business.infrastructure.rx

import com.aticosoft.appointments.mobile.business.domain.infrastructure.rx.RxSchedulers
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by rodrigo on 21/10/15.
 */
@Module
/*internal*/ open class RxModule {

    @Provides @Singleton
    open fun provideRxSchedulers(): RxSchedulers = RxSchedulersImpl()
}