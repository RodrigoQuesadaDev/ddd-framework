package com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.query_view.test_data

import com.aticosoft.appointments.mobile.business.domain.application.common.service.ApplicationServices
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 15/11/15.
 */
@Singleton
internal class TestDataParentServices @Inject constructor(private val c: TestDataParentServices.Context) : ApplicationServices(c.superContext) {

    class AddData(val parentValue: Int, val child1: AddData.Child?, val child2: AddData.Child?
    ) : Command() {
        class Child(val value: Int, val grandChild1: Int?, val grandChild2: Int?)
    }

    fun execute(command: AddData) = command.execute {
        c.testDataRepository.add(c.testDataParentFactory.create(
                parentValue,
                child1?.let { child ->
                    c.testDataChildFactory.create(
                            child.value,
                            child.grandChild1?.let { c.testDataGrandChildFactory.create(it) },
                            child.grandChild2?.let { c.testDataGrandChildFactory.create(it) }
                    )
                },
                child2?.let { child ->
                    c.testDataChildFactory.create(
                            child.value,
                            child.grandChild1?.let { c.testDataGrandChildFactory.create(it) },
                            child.grandChild2?.let { c.testDataGrandChildFactory.create(it) }
                    )
                }
        ))
    }

    class IncrementAllChild1() : Command()

    fun execute(command: IncrementAllChild1) = command.execute {
        val allParents = c.testDataRepository.find(c.testDataQueries.all())
        allParents.forEach { p ->
            p.child1?.let { ++it.value }
        }
    }

    class IncrementAllChild2GrandChild1() : Command()

    fun execute(command: IncrementAllChild2GrandChild1) = command.execute {
        val allParents = c.testDataRepository.find(c.testDataQueries.all())
        allParents.forEach { p ->
            p.child2?.let { child ->
                child.grandChild1?.let { ++it.value }
            }
        }
    }

    @Singleton
    class Context @Inject protected constructor(
            val superContext: ApplicationServices.Context,
            val testDataParentFactory: TestDataParentFactory,
            val testDataRepository: TestDataParentRepository,
            val testDataChildFactory: TestDataChildFactory,
            val testDataGrandChildFactory: TestDataGrandChildFactory,
            val testDataQueries: TestDataParentQueries
    )
}