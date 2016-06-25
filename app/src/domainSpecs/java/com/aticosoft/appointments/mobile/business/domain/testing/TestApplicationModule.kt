package com.aticosoft.appointments.mobile.business.domain.testing

import com.aticosoft.appointments.mobile.business.ApplicationBaseModule
import com.aticosoft.appointments.mobile.business.domain.testing.infrastructure.domain.TestDomainModule
import com.aticosoft.appointments.mobile.business.domain.testing.infrastructure.persistence.TestPersistenceModule
import com.rodrigodev.common.rx.testing.TestRxModule
import dagger.Module

/**
 * Created by Rodrigo Quesada on 29/10/15.
 */
@Module(includes = arrayOf(ApplicationBaseModule::class, TestDomainModule::class, TestPersistenceModule::class, TestRxModule::class))
internal class TestApplicationModule