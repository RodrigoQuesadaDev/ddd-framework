package com.aticosoft.appointments.mobile.business.test.common.infrastructure.persistence

import com.aticosoft.appointments.mobile.business.infrastructure.persistence.PersistenceConfigurer
import com.aticosoft.appointments.mobile.business.infrastructure.persistence.PersistenceModule

/**
 * Created by rodrigo on 10/09/15.
 */
class TestPersistenceModule : PersistenceModule() {

    override fun providePersistenceConfigurer(services: PersistenceConfigurer.Services) = TestPersistenceConfigurer(services)
}