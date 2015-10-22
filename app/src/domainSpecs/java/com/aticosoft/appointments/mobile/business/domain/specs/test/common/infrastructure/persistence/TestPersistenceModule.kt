package com.aticosoft.appointments.mobile.business.domain.specs.test.common.infrastructure.persistence

import com.aticosoft.appointments.mobile.business.infrastructure.persistence.PersistenceConfigurator
import com.aticosoft.appointments.mobile.business.infrastructure.persistence.PersistenceModule

/**
 * Created by rodrigo on 10/09/15.
 */
internal class TestPersistenceModule : PersistenceModule() {

    override fun providePersistenceConfigurer(services: PersistenceConfigurator.Services) = TestPersistenceConfigurator(services)
}