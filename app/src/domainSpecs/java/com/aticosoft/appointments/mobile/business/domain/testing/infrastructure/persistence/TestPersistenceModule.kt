package com.aticosoft.appointments.mobile.business.domain.testing.infrastructure.persistence

import com.aticosoft.appointments.mobile.business.infrastructure.persistence.PersistenceConfigurator
import com.aticosoft.appointments.mobile.business.infrastructure.persistence.PersistenceModule

/**
* Created by Rodrigo Quesada on 10/09/15.
*/
internal class TestPersistenceModule : PersistenceModule() {

    override fun providePersistenceConfigurator(services: PersistenceConfigurator.Services) = TestPersistenceConfigurator(services)
}