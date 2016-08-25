package com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_listener.test_data

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
        private val testDataParentRepository: EntityRepository<TestDataParent>,
        private val testDataParentQueries: TestDataParentQueries
) : ApplicationServices() {

    class AddData(val parentValue: Int, val childValue: Int) : Command()

    fun execute(command: AddData) = command.execute {
        testDataParentRepository.add(testDataParentFactory.create(parentValue, childValue))
    }

    class RemoveData(val value: Int) : Command()

    fun execute(command: RemoveData) = command.execute {
        testDataParentRepository.find(testDataParentQueries.valueIs(value))!!.let { data ->
            testDataParentRepository.remove(data)
        }
    }

    class ChangeData(val currentValue: Int, val targetValue: Int) : Command()

    fun execute(command: ChangeData) = command.execute {
        testDataParentRepository.find(testDataParentQueries.valueIs(currentValue))!!.let { data ->
            data.value = targetValue
            data.child.value = targetValue
        }
    }

    class ChangeChild(val parentValue: Int, val newChildValue: Int) : Command()

    fun execute(command: ChangeChild) = command.execute {
        val parent = testDataParentRepository.find(testDataParentQueries.valueIs(parentValue))!!
        parent.child.value = newChildValue
    }
}