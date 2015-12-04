package com.aticosoft.appointments.mobile.business.domain.testing

import com.aticosoft.appointments.mobile.business.domain.testing.infrastructure.domain.common.TestDomainCommonModule
import com.aticosoft.appointments.mobile.business.domain.testing.infrastructure.domain.model.TestDomainModelModule
import com.aticosoft.appointments.mobile.business.domain.testing.infrastructure.persistence.TestPersistenceModule
import com.aticosoft.appointments.mobile.business.domain.testing.infrastructure.persistence.observer.TestQueryViewsModule
import com.aticosoft.appointments.mobile.business.infrastructure.domain.common.DomainCommonModule
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.DomainModelModule
import com.aticosoft.appointments.mobile.business.infrastructure.persistence.PersistenceModule
import com.aticosoft.appointments.mobile.business.infrastructure.persistence.observation.QueryViewsModule

/**
 * Created by Rodrigo Quesada on 23/10/15.
 */
internal class TestApplicationModules(
        val persistenceModule: PersistenceModule = TestPersistenceModule(),
        val queryViewsModule: QueryViewsModule = TestQueryViewsModule(),
        val domainModelModule: DomainModelModule = TestDomainModelModule(),
        val domainCommonModule: DomainCommonModule = TestDomainCommonModule()
) {
    fun injectUsing(component: TestApplicationComponent<*>) {
        if (domainCommonModule is TestDomainCommonModule) {
            component.inject(domainCommonModule)
        }
    }
}