package com.aticosoft.appointments.mobile.business.domain.testing.infrastructure.domain.model

import com.aticosoft.appointments.mobile.business.domain.model.common.Entity
import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.TestData
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.DomainModelModule

/**
 * Created by Rodrigo Quesada on 04/12/15.
 */
internal class TestDomainModelModule : DomainModelModule() {

    override fun provideEntityTypes(): Array<Class<out Entity>> = super.provideEntityTypes() +
            TestData::class.java +
            com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.filtering.test_data.entityTypes +
            com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.query_view.test_data.entityTypes
}