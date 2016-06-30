package com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.query_view.test_data

import com.aticosoft.appointments.mobile.business.domain.application.common.service.ApplicationServices
import com.rodrigodev.common.nullability.nullOr
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 15/11/15.
 */
@Singleton
internal class TestDataParentServices @Inject constructor(
        val context: ApplicationServices.Context,
        val testDataParentFactory: TestDataParentFactory,
        val testDataRepository: TestDataParentRepository,
        val testDataChildFactory: TestDataChildFactory,
        val testDataGrandChildFactory: TestDataGrandChildFactory,
        val testDataQueries: TestDataParentQueries
) : ApplicationServices(context) {

    class AddData(val parentValue: Int, val embedded1: Int?, val embedded2: ComplexEmbedded?, val child1: Child?, val child2: Child?
    ) : Command() {

        class ComplexEmbedded(val value: Int, val nestedEmbedded: Int?)

        class Child(val value: Int, val grandChild1: GrandChild?, val grandChild2: GrandChild?)

        class GrandChild(val value: Int, val embedded1: Int?, val embedded2: ComplexEmbedded?)
    }

    fun execute(command: AddData) = command.execute {

        //region Utils
        fun createSimpleEmbedded(value: Int?) = value.nullOr { TestDataSimpleEmbedded(it) }

        fun createComplexEmbedded(e: AddData.ComplexEmbedded?) = e.nullOr { TestDataComplexEmbedded(it.value, createSimpleEmbedded(it.nestedEmbedded)) }

        fun createGrandChild(g: AddData.GrandChild?) = g.nullOr { testDataGrandChildFactory.create(it.value, createSimpleEmbedded(it.embedded1), createComplexEmbedded(it.embedded2)) }

        fun createChild(c: AddData.Child?) = c.nullOr { testDataChildFactory.create(it.value, createGrandChild(it.grandChild1), createGrandChild(it.grandChild2)) }
        //endregion

        testDataRepository.add(testDataParentFactory.create(
                parentValue,
                createSimpleEmbedded(embedded1),
                embedded2?.let { TestDataComplexEmbedded(it.value, createSimpleEmbedded(it.nestedEmbedded)) },
                createChild(child1),
                createChild(child2)
        ))
    }

    class IncrementAllChild1() : Command()

    fun execute(command: IncrementAllChild1) = command.execute {
        val allParents = testDataRepository.find(testDataQueries.all())
        allParents.forEach { p ->
            p.child1?.let { ++it.value }
        }
    }

    class IncrementAllChild2GrandChild1() : Command()

    fun execute(command: IncrementAllChild2GrandChild1) = command.execute {
        val allParents = testDataRepository.find(testDataQueries.all())
        allParents.forEach { p ->
            p.child2?.let { child ->
                child.grandChild1?.let { ++it.value }
            }
        }
    }
}