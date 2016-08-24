package com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.query_view.test_data

import com.aticosoft.appointments.mobile.business.domain.model.common.entity.Entity
import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.AbstractTestData
import com.google.auto.factory.Provided
import javax.jdo.annotations.EmbeddedOnly
import javax.jdo.annotations.PersistenceCapable

/**
 * Created by Rodrigo Quesada on 15/11/15.
 */
@PersistenceCapable
//@AutoFactory
internal class TestDataParent protected constructor(
        @Provided context: Entity.Context,
        value: Int,
        var embedded1: TestDataSimpleEmbedded? = null,
        var embedded2: TestDataComplexEmbedded? = null,
        var child1: TestDataChild? = null,
        var child2: TestDataChild? = null
) : AbstractTestData(context, value)

@PersistenceCapable
//@AutoFactory
internal class TestDataChild protected constructor(
        @Provided context: Entity.Context,
        value: Int,
        var grandChild1: TestDataGrandChild? = null,
        var grandChild2: TestDataGrandChild? = null
) : AbstractTestData(context, value)

@PersistenceCapable
//@AutoFactory
internal class TestDataGrandChild protected constructor(
        @Provided context: Entity.Context,
        value: Int,
        var embedded1: TestDataSimpleEmbedded? = null,
        var embedded2: TestDataComplexEmbedded? = null
) : AbstractTestData(context, value)

@EmbeddedOnly
internal class TestDataComplexEmbedded(
        var value: Int,
        var nestedEmbedded: TestDataSimpleEmbedded? = null
)

@EmbeddedOnly
internal class TestDataSimpleEmbedded(
        var value: Int
)