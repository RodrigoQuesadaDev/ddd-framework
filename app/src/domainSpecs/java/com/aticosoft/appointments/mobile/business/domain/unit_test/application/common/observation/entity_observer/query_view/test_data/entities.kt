package com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.query_view.test_data

import com.aticosoft.appointments.mobile.business.domain.model.common.Entity
import com.aticosoft.appointments.mobile.business.domain.testing.infrastructure.domain.model.create
import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.AbstractTestData
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.entity.EntityInjector
import com.google.auto.factory.Provided
import javax.jdo.annotations.PersistenceCapable

/**
 * Created by Rodrigo Quesada on 15/11/15.
 */
@PersistenceCapable
//@AutoFactory
internal class TestDataParent protected constructor(@Provided context: Entity.Context, value: Int, var child1: TestDataChild? = null, var child2: TestDataChild? = null) : AbstractTestData(context, value)

@PersistenceCapable
//@AutoFactory
internal class TestDataChild protected constructor(@Provided context: Entity.Context, value: Int, var grandChild1: TestDataGrandChild? = null, var grandChild2: TestDataGrandChild? = null) : AbstractTestData(context, value)

@PersistenceCapable
//@AutoFactory
internal class TestDataGrandChild protected constructor(@Provided context: Entity.Context, value: Int) : AbstractTestData(context, value)

val entityTypes: Array<Class<out Entity>> = arrayOf(TestDataParent::class.java, TestDataChild::class.java, TestDataGrandChild::class.java)

internal fun provideEntityInjectors(entityInjectorFactory: EntityInjector.Factory) = with(entityInjectorFactory) {
    arrayOf(
            create<TestDataParent> { inject(it) },
            create<TestDataChild> { inject(it) },
            create<TestDataGrandChild> { inject(it) }
    )
}

internal interface TestEntityInjection {

    fun inject(entity: TestDataParent)
    fun inject(entity: TestDataChild)
    fun inject(entity: TestDataGrandChild)
}