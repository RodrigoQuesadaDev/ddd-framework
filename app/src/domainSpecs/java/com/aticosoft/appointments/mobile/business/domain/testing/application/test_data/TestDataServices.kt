package com.aticosoft.appointments.mobile.business.domain.testing.application.test_data

import com.aticosoft.appointments.mobile.business.domain.application.common.service.*
import com.aticosoft.appointments.mobile.business.domain.model.common.Entity
import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.TestData
import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.TestDataQueries
import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.TestDataRepository
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.concurrent.thread

/**
 * Created by Rodrigo Quesada on 25/10/15.
 */
@Singleton
internal open class TestDataServices @Inject constructor(private val c: TestDataServices.Context) : ApplicationServices(c.superContext) {

    class AddData(val value: Int) : Command()

    fun execute(command: AddData) = command.execute {
        c.testDataRepository.add(TestData(c.entityContext, value))
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

    @Singleton
    class Context @Inject protected constructor(
            val superContext: ApplicationServices.Context,
            val entityContext: Entity.Context,
            val testDataRepository: TestDataRepository,
            val testDataQueries: TestDataQueries
    )
}