package com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.query_view.test_data

import com.aticosoft.appointments.mobile.business.domain.model.common.Entity
import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.AbstractTestData
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