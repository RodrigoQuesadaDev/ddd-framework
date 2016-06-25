package com.aticosoft.appointments.mobile.business.domain.testing.infrastructure.persistence

import com.aticosoft.appointments.mobile.business.domain.testing.infrastructure.persistence.configuration.TestPersistenceConfiguratorModule
import com.aticosoft.appointments.mobile.business.infrastructure.persistence.PersistenceBaseModule
import dagger.Module

/**
 * Created by Rodrigo Quesada on 10/09/15.
 */
@Module(includes = arrayOf(PersistenceBaseModule::class, TestPersistenceConfiguratorModule::class))
internal class TestPersistenceModule