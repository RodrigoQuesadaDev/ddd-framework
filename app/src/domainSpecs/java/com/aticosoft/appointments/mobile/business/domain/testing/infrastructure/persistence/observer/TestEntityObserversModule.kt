package com.aticosoft.appointments.mobile.business.domain.testing.infrastructure.persistence.observer

import com.aticosoft.appointments.mobile.business.domain.application.common.observation.EntityObserver
import com.aticosoft.appointments.mobile.business.domain.testing.application.test_data.TestDataObserver
import com.aticosoft.appointments.mobile.business.infrastructure.persistence.observer.EntityObserversModule
import javax.inject.Inject
import javax.inject.Provider

/**
 * Created by rodrigo on 27/10/15.
 */
internal class TestEntityObserversModule : EntityObserversModule() {

    @Inject protected lateinit var testDataObserverProvider: Provider<TestDataObserver>

    override fun provideEntityObservers(entityObserversContainer: EntityObserversContainer): Array<EntityObserver<*>> {
        return super.provideEntityObservers(entityObserversContainer) + testDataObserverProvider.get()
    }
}