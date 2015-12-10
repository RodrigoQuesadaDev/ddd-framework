package com.aticosoft.appointments.mobile.business.domain.testing.application.test_data

import com.aticosoft.appointments.mobile.business.domain.application.common.service.ApplicationServices
import com.aticosoft.appointments.mobile.business.domain.testing.application.test_data.TestDataServices.Context
import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.TestDataFactory
import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.TestDataQueries
import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.TestDataRepository
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 25/10/15.
 */
@Singleton
internal open class TestDataServices @Inject constructor(private val c: Context) : ApplicationServices(c.superContext) {

    class AddData(val value: Int) : Command()

    fun execute(command: AddData) = command.execute {
        c.testDataRepository.add(c.testDataFactory.create(value))
    }

    class RemoveData(val value: Int) : Command()

    fun execute(command: RemoveData) = command.execute {
        c.testDataRepository.find(c.testDataQueries.valueIs(value))!!.let { data ->
            c.testDataRepository.remove(data)
        }
    }

    class ChangeData(val currentValue: Int, val  targetValue: Int) : Command()

    fun execute(command: ChangeData) = command.execute {
        c.testDataRepository.find(c.testDataQueries.valueIs(currentValue))!!.let { data ->
            data.value = targetValue
        }
    }

    class UseDependency(val value: Int) : Command()

    fun execute(command: UseDependency) = command.execute {
        c.testDataRepository.find(c.testDataQueries.valueIs(value))!!.let { data ->
            data.useDependency()
        }
    }

    class Context @Inject protected constructor(
            val superContext: ApplicationServices.Context,
            val testDataFactory: TestDataFactory,
            val testDataRepository: TestDataRepository,
            val testDataQueries: TestDataQueries
    )
}