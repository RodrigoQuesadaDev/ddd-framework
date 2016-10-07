package com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.valueobject.behavior.test_data

import com.aticosoft.appointments.mobile.business.domain.model.common.entity.Entity
import com.aticosoft.appointments.mobile.business.domain.model.common.valueobject.ValueObject
import com.google.auto.factory.Provided
import javax.inject.Inject
import javax.inject.Singleton
import javax.jdo.annotations.EmbeddedOnly
import javax.jdo.annotations.PersistenceCapable

/**
 * Created by Rodrigo Quesada on 06/10/16.
 */
@PersistenceCapable
//@AutoFactory
internal class EntityWithValueObject protected constructor(
        @Provided c: Context, value: Int
) : Entity(c.entityContext) {

    var valueObject = TestValueObject(value)
        private set

    @Singleton
    class Context @Inject protected constructor(
            val entityContext: Entity.Context
    )
}

@ValueObject
@EmbeddedOnly
internal class TestValueObject(var value: Int)