package com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.filtering.test_data

import com.aticosoft.appointments.mobile.business.domain.application.common.service.ApplicationServices
import com.aticosoft.appointments.mobile.business.domain.model.common.entity.EntityRepository
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 14/11/15.
 */
@Singleton
internal class TestDataParentServices @Inject protected constructor(
        private val testDataParentFactory: TestDataParentFactory,
        private val testDataRepository: EntityRepository<TestDataParent>,
        private val testDataQueries: TestDataParentQueries
) : ApplicationServices() {

    class AddData(val parentValue: Int, val childValue: Int) : Command()

    fun execute(command: AddData) = command.execute {
        testDataRepository.add(testDataParentFactory.create(parentValue, childValue))
    }

    class RemoveData(val value: Int) : Command()

    fun execute(command: RemoveData) = command.execute {
        testDataRepository.find(testDataQueries.valueIs(value))!!.let { data ->
            testDataRepository.remove(data)
        }
    }

    class ChangeData(val currentValue: Int, val targetValue: Int) : Command()

    fun execute(command: ChangeData) = command.execute {
        testDataRepository.find(testDataQueries.valueIs(currentValue))!!.let { data ->
            data.value = targetValue
        }
    }

    class ChangeChild(val parentValue: Int, val newChildValue: Int) : Command()

    fun execute(command: ChangeChild) = command.execute {
        val parent = testDataRepository.find(testDataQueries.valueIs(parentValue))!!
        parent.child.value = newChildValue
    }
}