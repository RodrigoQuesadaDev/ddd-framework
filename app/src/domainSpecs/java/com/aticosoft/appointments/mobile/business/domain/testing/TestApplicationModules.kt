package com.aticosoft.appointments.mobile.business.domain.testing

import com.aticosoft.appointments.mobile.business.domain.testing.infrastructure.domain.common.TestDomainCommonModule
import com.aticosoft.appointments.mobile.business.domain.testing.infrastructure.persistence.TestPersistenceModule
import com.aticosoft.appointments.mobile.business.domain.testing.infrastructure.persistence.observer.TestEntityListenersModule
import com.aticosoft.appointments.mobile.business.infrastructure.domain.common.DomainCommonModule
import com.aticosoft.appointments.mobile.business.infrastructure.persistence.PersistenceModule
import com.aticosoft.appointments.mobile.business.infrastructure.persistence.observation.EntityListenersModule

/**
* Created by Rodrigo Quesada on 23/10/15.
*/
internal class TestApplicationModules(
        val persistenceModule: PersistenceModule = TestPersistenceModule(),
        val entityListenersModule: EntityListenersModule = TestEntityListenersModule(),
        val domainCommonModule: DomainCommonModule = TestDomainCommonModule()
) {
    fun injectUsing(component: TestApplicationComponent<*>) {
        if (entityListenersModule is TestEntityListenersModule) {
            component.inject(entityListenersModule)
        }
        if (domainCommonModule is TestDomainCommonModule) {
            component.inject(domainCommonModule)
        }
    }
}