package com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_listener.test_data

import com.aticosoft.appointments.mobile.business.domain.model.common.entity.Entity
import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.AbstractTestData
import com.google.auto.factory.Provided
import javax.inject.Inject
import javax.inject.Singleton
import javax.jdo.annotations.PersistenceCapable

/**
 * Created by Rodrigo Quesada on 06/11/15.
 */
@PersistenceCapable
//@AutoFactory
internal class TestDataParent protected constructor(@Provided c: Context, value: Int, childValue: Int) : AbstractTestData(c.entityContext, value) {

    var child: TestDataChild = c.testDataChildFactory.create(childValue)
        private set

    @Singleton
    class Context @Inject protected constructor(
            val entityContext: Entity.Context,
            val testDataChildFactory: TestDataChildFactory
    )
}

@PersistenceCapable
//@AutoFactory
internal class TestDataChild protected constructor(@Provided context: Entity.Context, value: Int) : AbstractTestData(context, value)