package com.aticosoft.appointments.mobile.business.domain.testing

import com.aticosoft.appointments.mobile.business.domain.testing.infrastructure.domain.common.TestDomainCommonModule
import com.aticosoft.appointments.mobile.business.domain.testing.infrastructure.persistence.TestPersistenceModule
import com.aticosoft.appointments.mobile.business.domain.testing.infrastructure.persistence.observer.TestEntityObserversModule
import com.aticosoft.appointments.mobile.business.infrastructure.domain.common.DomainCommonModule
import com.aticosoft.appointments.mobile.business.infrastructure.persistence.PersistenceModule
import com.aticosoft.appointments.mobile.business.infrastructure.persistence.observer.EntityObserversModule

/**
 * Created by rodrigo on 23/10/15.
 */
internal class TestApplicationModules(
        val persistenceModule: PersistenceModule = TestPersistenceModule(),
        val entityObserversModule: EntityObserversModule = TestEntityObserversModule(),
        val domainCommonModule: DomainCommonModule = TestDomainCommonModule()
) {
    fun injectUsing(component: TestApplicationComponent<*>) {
        if (entityObserversModule is TestEntityObserversModule) {
            component.inject(entityObserversModule)
        }
        if (domainCommonModule is TestDomainCommonModule) {
            component.inject(domainCommonModule)
        }
    }
}