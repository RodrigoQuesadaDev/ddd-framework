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

    class AddData(val parentValue: Int, val child1: AddData.Child?, val child2: AddData.Child?
    ) : Command() {
        class Child(val value: Int, val grandChild1: Int?, val grandChild2: Int?)
    }

    fun execute(command: AddData) = command.execute {
        c.testDataRepository.add(TestDataParent(
                c.entityContext,
                parentValue,
                child1?.let { child ->
                    TestDataChild(
                            c.entityContext,
                            child.value,
                            child.grandChild1?.let { TestDataGrandChild(c.entityContext, it) },
                            child.grandChild2?.let { TestDataGrandChild(c.entityContext, it) }
                    )
                },
                child2?.let { child ->
                    TestDataChild(
                            c.entityContext,
                            child.value,
                            child.grandChild1?.let { TestDataGrandChild(c.entityContext, it) },
                            child.grandChild2?.let { TestDataGrandChild(c.entityContext, it) }
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
    protected class Context @Inject constructor(
            val superContext: ApplicationServices.Context,
            val entityContext: Entity.Context,
            val testDataRepository: TestDataParentRepository,
            val testDataQueries: TestDataParentQueries
    )
}