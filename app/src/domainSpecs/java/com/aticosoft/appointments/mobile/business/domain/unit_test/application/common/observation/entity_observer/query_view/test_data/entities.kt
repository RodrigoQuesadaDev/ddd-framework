package com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.query_view.test_data

import com.aticosoft.appointments.mobile.business.domain.model.common.Entity
import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.AbstractTestData
import javax.jdo.annotations.PersistenceCapable

/**
 * Created by Rodrigo Quesada on 15/11/15.
 */
@PersistenceCapable
internal class TestDataParent(context: Entity.Context, value: Int, var child1: TestDataChild? = null, var child2: TestDataChild? = null) : AbstractTestData(context, value)

@PersistenceCapable
internal class TestDataChild(context: Entity.Context, value: Int, var grandChild1: TestDataGrandChild? = null, var grandChild2: TestDataGrandChild? = null) : AbstractTestData(context, value)

@PersistenceCapable
internal class TestDataGrandChild(context: Entity.Context, value: Int) : AbstractTestData(context, value)

val entityTypes: Array<Class<out Entity>> = arrayOf(TestDataParent::class.java, TestDataChild::class.java, TestDataGrandChild::class.java)