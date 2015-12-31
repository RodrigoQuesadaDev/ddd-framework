package com.aticosoft.appointments.mobile.business.domain.testing.infrastructure.domain.model

import com.aticosoft.appointments.mobile.business.domain.model.common.Entity
import com.aticosoft.appointments.mobile.business.domain.testing.TestApplicationComponent
import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.TestData
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.DomainModelModule
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.entity.EntityInitializer
import com.rodrigodev.common.collection.plus
import kotlin.collections.plus

/**
 * Created by Rodrigo Quesada on 04/12/15.
 */
internal class TestDomainModelModule : DomainModelModule() {

    override fun provideEntityTypes(): Array<Class<out Entity>> = super.provideEntityTypes() +
            TestData::class.java +
            com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_listener.test_data.entityTypes +
            com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.filtering.test_data.entityTypes +
            com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.query_view.test_data.entityTypes +
            com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.entity.validation.test_data.entityTypes

    override fun provideValidators() = super.provideValidators() +
            com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.entity.validation.test_data.provideValidators()

    override fun provideEntityInitializers(entityInitializerFactory: EntityInitializer.Factory) = with(entityInitializerFactory) {
        super.provideEntityInitializers(this) +
                arrayOf(create<TestData> { inject(it) }) +
                com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_listener.test_data.provideEntityInitializers(this) +
                com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.filtering.test_data.provideEntityInitializers(this) +
                com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.query_view.test_data.provideEntityInitializers(this) +
                com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.entity.validation.test_data.provideEntityInitializers(this)
    }
}

internal interface TestEntityInjection :
        com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_listener.test_data.TestEntityInjection,
        com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.filtering.test_data.TestEntityInjection,
        com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.query_view.test_data.TestEntityInjection,
        com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.entity.validation.test_data.TestEntityInjection {

    fun inject(entity: TestData)
}

/***************************************************************************************************
 * Extensions
 **************************************************************************************************/

internal inline fun <reified E : Entity> EntityInitializer.Factory.create(noinline injectCall: TestApplicationComponent<*>.(E) -> Unit) = create(E::class.java, injectCall)