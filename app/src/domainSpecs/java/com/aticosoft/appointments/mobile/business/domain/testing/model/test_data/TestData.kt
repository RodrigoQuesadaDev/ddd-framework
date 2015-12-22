package com.aticosoft.appointments.mobile.business.domain.testing.model.test_data

import com.aticosoft.appointments.mobile.business.domain.model.common.Entity
import com.google.auto.factory.Provided
import javax.inject.Inject
import javax.inject.Singleton
import javax.jdo.annotations.PersistenceCapable

/**
 * Created by Rodrigo Quesada on 24/10/15.
 */
@PersistenceCapable
//@AutoFactory
internal open class TestData protected constructor(@Provided c: TestData.Context, value: Int) : AbstractTestData(c.entityContext, value) {

    @Transient var c: Context? = c
        @Inject protected set

    fun useDependency() {
        c!!.testDependency.doSomething()
    }

    @Singleton
    class Context @Inject protected constructor(
            val entityContext: Entity.Context,
            val testDependency: TestDependency
    )
}

internal abstract class AbstractTestData(context: Entity.Context, var value: Int) : Entity(context)

@Singleton
internal class TestDependency @Inject protected constructor() {

    fun doSomething() = Math.random()
}