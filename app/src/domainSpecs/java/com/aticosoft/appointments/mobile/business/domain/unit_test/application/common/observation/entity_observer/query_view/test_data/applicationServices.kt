package com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.query_view.test_data

import com.aticosoft.appointments.mobile.business.domain.application.common.ApplicationServices
import com.aticosoft.appointments.mobile.business.domain.model.common.Entity
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 15/11/15.
 */
@Singleton
internal class TestDataParentServices @Inject constructor(private val c: TestDataParentServices.Context) : ApplicationServices(c.superContext) {

    class AddData(val parentValue: Int, val child1: Int? = null, val child2: Int? = null, val grandChild1: Int? = null, val grandChild2: Int? = null) : Command()

    fun execute(command: AddData) = command.execute {
        c.testDataRepository.add(TestDataParent(
                c.entityContext,
                parentValue
                //child1?.let { TestDataChild(child1) }))
        ))
    }

    @Singleton
    protected class Context @Inject constructor(
            val superContext: ApplicationServices.Context,
            val entityContext: Entity.Context,
            val testDataRepository: TestDataParentRepository,
            val testDataQueries: TestDataParentQueries
    )
}