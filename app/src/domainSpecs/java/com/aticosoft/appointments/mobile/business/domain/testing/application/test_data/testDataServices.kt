package com.aticosoft.appointments.mobile.business.domain.testing.application.test_data

import com.aticosoft.appointments.mobile.business.domain.application.common.ApplicationServices
import com.aticosoft.appointments.mobile.business.domain.model.common.Entity
import com.aticosoft.appointments.mobile.business.domain.model.common.Repository
import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.*
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 25/10/15.
 */
internal abstract class TestDataServicesBase<T : AbstractTestData>(private val c: TestDataServicesBase.Context<T>) : ApplicationServices(c.superContext) {

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

    protected interface Context<T : AbstractTestData> {
        val superContext: ApplicationServices.Context
        val testDataRepository: Repository<T>
        val testDataQueries: AbstractTestDataQueries<T>
    }
}

@Singleton
internal class TestDataServices @Inject constructor(private val c: TestDataServices.Context) : TestDataServicesBase<TestData>(c) {

    class AddData(val value: Int) : Command()

    fun execute(command: AddData) = command.execute {
        c.testDataRepository.add(TestData(c.entityContext, value))
    }

    @Singleton
    protected class Context @Inject constructor(
            override val superContext: ApplicationServices.Context,
            val entityContext: Entity.Context,
            override val testDataRepository: TestDataRepository,
            override val testDataQueries: TestDataQueries
    ) : TestDataServicesBase.Context<TestData>
}

@Singleton
internal class TestDataParentServices @Inject constructor(private val c: TestDataParentServices.Context) : TestDataServicesBase<TestDataParent>(c) {

    class AddData(val parentValue: Int, val childValue: Int) : Command()

    fun execute(command: AddData) = command.execute {
        c.testDataRepository.add(TestDataParent(c.entityContext, parentValue, childValue))
    }

    class ChangeChild(val parentValue: Int, val newChildValue: Int) : Command()

    fun execute(command: ChangeChild) = command.execute {
        val parent = c.testDataRepository.find(c.testDataQueries.valueIs(parentValue))!!
        parent.child.value = newChildValue
    }

    @Singleton
    protected class Context @Inject constructor(
            override val superContext: ApplicationServices.Context,
            val entityContext: Entity.Context,
            override val testDataRepository: TestDataParentRepository,
            override val testDataQueries: TestDataParentQueries
    ) : TestDataServicesBase.Context<TestDataParent>
}