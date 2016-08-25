package com.aticosoft.appointments.mobile.business.domain.testing.application.test_data

import com.aticosoft.appointments.mobile.business.domain.application.common.service.ApplicationServices
import com.aticosoft.appointments.mobile.business.domain.model.common.entity.EntityRepository
import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.TestData
import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.TestDataFactory
import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.TestDataQueries
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 25/10/15.
 */
@Singleton
internal open class TestDataServices @Inject protected constructor() : ApplicationServices() {

    protected lateinit var m: InjectedMembers

    class AddData(val value: Int) : Command()

    fun execute(command: AddData) = command.execute {
        m.testDataRepository.add(m.testDataFactory.create(value))
    }

    class RemoveData(val value: Int) : Command()

    fun execute(command: RemoveData) = command.execute {
        m.testDataRepository.find(m.testDataQueries.valueIs(value))!!.let { data ->
            m.testDataRepository.remove(data)
        }
    }

    class ChangeData(val currentValue: Int, val targetValue: Int) : Command()

    fun execute(command: ChangeData) = command.execute {
        m.testDataRepository.find(m.testDataQueries.valueIs(currentValue))!!.let { data ->
            data.value = targetValue
        }
    }

    class UseDependency(val value: Int) : Command()

    fun execute(command: UseDependency) = command.execute {
        m.testDataRepository.find(m.testDataQueries.valueIs(value))!!.let { data ->
            data.useDependency()
        }
    }

    //region Injection
    @Inject
    protected fun inject(injectedMembers: InjectedMembers) {
        m = injectedMembers
    }

    protected class InjectedMembers @Inject protected constructor(
            val testDataFactory: TestDataFactory,
            val testDataRepository: EntityRepository<TestData>,
            val testDataQueries: TestDataQueries
    )
    //endregion
}