package com.aticosoft.appointments.mobile.business.domain.specs.test.common.infrastructure.persistence

import com.aticosoft.appointments.mobile.business.infrastructure.persistence.PersistenceConfigurer
import com.aticosoft.appointments.mobile.business.infrastructure.persistence.PersistenceModule

/**
 * Created by rodrigo on 10/09/15.
 */
internal class TestPersistenceModule : PersistenceModule() {

    override fun providePersistenceConfigurer(services: PersistenceConfigurer.Services) = TestPersistenceConfigurer(services)
}