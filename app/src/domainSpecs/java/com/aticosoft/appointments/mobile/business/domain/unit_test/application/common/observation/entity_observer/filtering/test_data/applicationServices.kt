package com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.filtering.test_data

import com.aticosoft.appointments.mobile.business.domain.application.common.ApplicationServices
import com.aticosoft.appointments.mobile.business.domain.model.common.Entity
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 14/11/15.
 */
@Singleton
internal class TestDataParentServices @Inject constructor(private val c: TestDataParentServices.Context) : ApplicationServices(c.superContext) {

    class AddData(val parentValue: Int, val childValue: Int) : Command()

    fun execute(command: AddData) = command.execute {
        c.testDataRepository.add(TestDataParent(c.entityContext, parentValue, childValue))
    }

    class RemoveData(val value: Int) : Command()

    fun execute(command: RemoveData) = command.execute {
        c.testDataRepository.find(c.testDataQueries.valueIs(value))?.let { data ->
            c.testDataRepository.remove(data)
        }
    }

    class ChangeData(val currentValue: Int, val  targetValue: Int) : Command()

    fun execute(command: ChangeData) = command.execute {
        c.testDataRepository.find(c.testDataQueries.valueIs(currentValue))?.let { data ->
            data.value = targetValue
        }
    }

    class ChangeChild(val parentValue: Int, val newChildValue: Int) : Command()

    fun execute(command: ChangeChild) = command.execute {
        val parent = c.testDataRepository.find(c.testDataQueries.valueIs(parentValue))!!
        parent.child.value = newChildValue
    }

    @Singleton
    protected class Context @Inject constructor(
            val superContext: ApplicationServices.Context,
            val entityContext: Entity.Context,
            val testDataRepository: TestDataParentRepository,
            val testDataQueries: TestDataParentQueries
    )
}