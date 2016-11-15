package com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.persistable_object.transactionalaction.observationbehavior.test_data

import com.aticosoft.appointments.mobile.business.domain.model.common.entity.Entity
import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.AbstractTestData
import com.google.auto.factory.Provided
import javax.inject.Inject
import javax.inject.Singleton
import javax.jdo.annotations.PersistenceCapable

/**
 * Created by Rodrigo Quesada on 25/08/16.
 */
@PersistenceCapable
//@AutoFactory
internal class ParentSingleEntity(@Provided c: Context, value: Int) : AbstractTestData(c.entityContext, value) {

    override var value: Int = value
        set(value) {
            field = value
            child.value = value
        }

    var child: ChildSingleEntity = c.testDataChildFactory.create(value)
        private set

    @Singleton
    class Context @Inject protected constructor(
            val entityContext: Entity.Context,
            val testDataChildFactory: ChildSingleEntityFactory
    )
}

@PersistenceCapable
//@AutoFactory
internal class ChildSingleEntity(@Provided c: Context, value: Int) : AbstractTestData(c, value)

@PersistenceCapable
//@AutoFactory
internal class SampleMultiEntity(@Provided c: Context, value: Int) : AbstractTestData(c, value)

@PersistenceCapable
//@AutoFactory
internal class SampleManyUpdatesEntity(@Provided c: Context, value: Int) : AbstractTestData(c, value)

@PersistenceCapable
//@AutoFactory
internal class SampleUpdateDifferentObjectsEntity(@Provided c: Context, value: Int) : AbstractTestData(c, value)

@PersistenceCapable
//@AutoFactory
internal class SampleManyActionsSameTypeEntity(@Provided c: Context, value: Int) : AbstractTestData(c, value)

@PersistenceCapable
//@AutoFactory
internal class SampleUpdateWithPreviousValueEntity(@Provided c: Context, value: Int) : AbstractTestData(c, value)

@PersistenceCapable
//@AutoFactory
internal class SampleDefaultEntity(@Provided c: Context, value: Int) : AbstractTestData(c, value)