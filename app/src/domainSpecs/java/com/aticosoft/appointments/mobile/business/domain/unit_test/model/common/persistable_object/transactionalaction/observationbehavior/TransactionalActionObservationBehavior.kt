@file:Suppress("NOTHING_TO_INLINE")

package com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.persistable_object.transactionalaction.observationbehavior

import com.aticosoft.appointments.mobile.business.domain.model.common.entity.Entity
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.TransactionalUpdateAction
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.listener.sync.ObjectChangeType
import com.aticosoft.appointments.mobile.business.domain.specs.DomainStory
import com.aticosoft.appointments.mobile.business.domain.testing.model.TestEntityRepositoryManager
import com.aticosoft.appointments.mobile.business.domain.testing.model.TestTransactionalActionsManager
import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.AbstractTestData
import com.aticosoft.appointments.mobile.business.domain.unit_test.UnitTestApplication
import com.aticosoft.appointments.mobile.business.domain.unit_test.UnitTestApplicationComponent
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.persistable_object.transactionalaction.common.TransactionalActionStepBase
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.persistable_object.transactionalaction.common.test_data.LocalTestDataServices
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.persistable_object.transactionalaction.observationbehavior.TransactionalActionObservationBehavior.LocalSteps.LocalObjectType
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.persistable_object.transactionalaction.observationbehavior.TransactionalActionObservationBehavior.UnitTestApplicationImpl
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.persistable_object.transactionalaction.observationbehavior.test_data.*
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.persistable_object.transactionalaction.observationbehavior.test_data.AbstractLocalTestTransactionalAction.ProducedValue
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.persistable_object.transactionalaction.observationbehavior.test_data.AbstractLocalTestTransactionalAction.ValueProducer
import com.rodrigodev.common.assertj.Assertions.assertThatList
import org.assertj.core.api.Assertions.assertThat
import org.jbehave.core.annotations.AsParameters
import org.jbehave.core.annotations.Given
import org.jbehave.core.annotations.Then
import org.robolectric.annotation.Config
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 03/11/16.
 */
@Config(application = UnitTestApplicationImpl::class)
internal class TransactionalActionObservationBehavior : DomainStory() {

    class UnitTestApplicationImpl : UnitTestApplication<TransactionalActionObservationBehavior>(UnitTestApplicationComponent::inject)

    @Inject protected lateinit var localSteps: LocalSteps

    init {
        steps { listOf(localSteps) }
    }

    class LocalSteps @Inject constructor(
            private val parentSingleEntityMembers: LocalEntityMembers<ParentSingleEntity, ParentSingleServices>,
            private val childSingleEntityMembers: LocalEntityMembers<ChildSingleEntity, ParentSingleServices>,
            private val sampleMultiEntityMembers: LocalEntityMembers<SampleMultiEntity, SampleMultiServices>,
            private val sampleManyUpdatesEntityMembers: LocalEntityMembers<SampleManyUpdatesEntity, SampleManyUpdatesServices>,
            private val sampleUpdateDifferentObjectsEntityMembers: LocalEntityMembers<SampleUpdateDifferentObjectsEntity, SampleUpdateDifferentObjectsServices>,
            private val sampleManyActionsSameTypeEntityMembers: LocalEntityMembers<SampleManyActionsSameTypeEntity, SampleManyActionsSameTypeServices>,
            private val sampleUpdateWithPreviousValueEntityMembers: LocalEntityMembers<SampleUpdateWithPreviousValueEntity, SampleUpdateWithPreviousValueServices>,
            private val sampleDefaultEntityMembers: LocalEntityMembers<SampleDefaultEntity, SampleDefaultServices>
    ) : TransactionalActionStepBase<LocalSteps, LocalObjectType, AbstractLocalTestTransactionalAction<*>>() {

        override val objectTypeValues = LocalObjectType.values()
        override val actionType = AbstractLocalTestTransactionalAction::class.java

        @Given("no data")
        fun givenNoData() = LocalObjectType.values().forEach {
            with(it) {
                m.repositoryManager.clear()
                m.valueProducer.clear()
            }
        }

        @Given("\$n transactional action{s|} defined for \$objectType object changes of type [\$changeTypes]")
        fun givenNTransactionalActionsDefinedForObjectChangesOfType(n: Int, objectType: LocalObjectType, changeTypes: MutableList<ObjectChangeType>) {
            assertThat(
                    objectType.declaredTransactionalActions.filterWithSpecifiedChangeTypes(changeTypes).toList()
            )
                    .hasSize(n)
        }

        @Given("\$n transactional action{s|} defined for \$objectType object that observes update changes with their previous value")
        fun givenNTransactionalActionsDefinedForObjectThatObservesUpdateChangesWithTheirPreviousValue(n: Int, objectType: LocalObjectType) {
            assertThat(
                    objectType.declaredTransactionalActions.filterIsInstance<TransactionalUpdateAction<*>>().toList()
            )
                    .hasSize(n)
        }

        @Given("\$n transactional action{s|} defined for \$objectType object that doesn't specify any object change type")
        fun givenNTransactionalActionsDefinedForObjectThatDoesNotSpecifyAnyObjectChangeType(n: Int, objectType: LocalObjectType) {
            givenNTransactionalActionsDefinedForObjectChangesOfType(n, objectType, mutableListOf())
        }

        @Then("the values produced by the \$objectType transactional actions were exactly (and in the same order): \$producedValues")
        fun thenTheValuesProducedByTheTransactionalActionsWereExactly(objectType: LocalObjectType, producedValues: MutableList<ProducedValueExample>) = with(objectType) {
            assertThat(m.valueProducer.producedValues.toExamples()).containsExactlyElementsOf(producedValues)
        }

        @Then("the values produced by the \$objectType transactional action of type [\$changeTypes] were exactly (and in the same order): [\$producedValues]")
        fun thenTheValuesProducedByTheTransactionalActionWereExactly(objectType: LocalObjectType, changeTypes: MutableList<ObjectChangeType>, producedValues: MutableList<Int>) = with(objectType) {
            assertThat(m.valueProducer.producedValues.asSequence().filterWithChangeTypes(changeTypes).map { it.value }.toList())
                    .containsExactlyElementsOf(producedValues)
        }

        @Then("the value pairs produced by the \$objectType transactional action of type [\$changeTypes] were exactly (and in the same order): \$producedValues")
        fun thenTheValuePairsProducedByTheTransactionalActionWereExactly(objectType: LocalObjectType, changeTypes: MutableList<ObjectChangeType>, producedValues: MutableList<ProducedValueExample>) = with(objectType) {
            assertThat(m.valueProducer.producedValues.asSequence().filterWithChangeTypes(changeTypes).toExamples(ignoreType = true).toList())
                    .containsExactlyElementsOf(producedValues)
        }

        @Then("the values produced by every \$objectType transactional action were exactly (and in the same order): [\$producedValues]")
        fun thenTheValuesProducedByEveryTransactionalActionWereExactly(objectType: LocalObjectType, producedValues: MutableList<Int>) = with(objectType) {
            objectType.declaredTransactionalActions.forEach {
                assertThat().containsExactlyElementsOf(producedValues)

                m.valueProducer.producedValues
            }
        }

        @Then("the \$objectType transactional action observes object changes of type [\$changeTypes]")
        fun thenTheTransactionalActionObservesObjectChangesOfType(objectType: LocalObjectType, changeTypes: MutableList<ObjectChangeType>) {
            assertThatList(objectType.declaredTransactionalActions.single().changeTypes.toList()).containsExactlyElementsOfInAnyOrder(changeTypes)
        }

        //region Event Members
        @Singleton
        class LocalEntityMembers<E : AbstractTestData, out S : LocalTestDataServices<*>> @Inject protected constructor(
                override val repositoryManager: TestEntityRepositoryManager<E>,
                override val actionsManager: TestTransactionalActionsManager,
                override val services: S,
                val valueProducer: ValueProducer<E>
        ) : EntityMembers<E, S>
        //endregion

        //region Other Classes
        enum class LocalObjectType(
                override val entityClass: Class<out Entity>,
                override val eventMembers: LocalSteps.() -> LocalEntityMembers<*, *>
        ) : ObjectType<LocalSteps, LocalEntityMembers<*, *>> {
            PARENT_SINGLE(ParentSingleEntity::class.java, { parentSingleEntityMembers }),
            CHILD_SINGLE(ChildSingleEntity::class.java, { childSingleEntityMembers }),
            SAMPLE_MULTI(SampleMultiEntity::class.java, { sampleMultiEntityMembers }),
            SAMPLE_MANY_UPDATES(SampleManyUpdatesEntity::class.java, { sampleManyUpdatesEntityMembers }),
            SAMPLE_UPDATE_DIFFERENT_OBJECTS(SampleUpdateDifferentObjectsEntity::class.java, { sampleUpdateDifferentObjectsEntityMembers }),
            SAMPLE_MANY_ACTIONS_SAME_TYPE(SampleManyActionsSameTypeEntity::class.java, { sampleManyActionsSameTypeEntityMembers }),
            SAMPLE_UPDATE_WITH_PREVIOUS_VALUE(SampleUpdateWithPreviousValueEntity::class.java, { sampleUpdateWithPreviousValueEntityMembers }),
            SAMPLE_DEFAULT(SampleDefaultEntity::class.java, { sampleDefaultEntityMembers }),
        }
        //endregion
    }
}

private fun <T : WithChangeTypes> Sequence<T>.filterWithChangeTypes(changeTypes: List<ObjectChangeType>)
        : Sequence<T> = filter { it.changeTypes.sorted() == changeTypes.sorted() }

private fun <T : AbstractLocalTestTransactionalAction<*>> Sequence<T>.filterWithSpecifiedChangeTypes(changeTypes: List<ObjectChangeType>)
        = filterIsInstance<LocalTestTransactionalAction<*>>().filter { it.specifiedChangeTypes.sorted() == changeTypes.sorted() }

//region Utils
private const val DEFAULT_IGNORE_TYPE_VALUE = false

private inline fun ProducedValue.toExample(ignoreType: Boolean = DEFAULT_IGNORE_TYPE_VALUE) = ProducedValueExample(if (!ignoreType) changeTypes.single() else null, previousValue, value)

private inline fun List<ProducedValue>.toExamples(ignoreType: Boolean = DEFAULT_IGNORE_TYPE_VALUE) = map { it.toExample(ignoreType) }

private inline fun Sequence<ProducedValue>.toExamples(ignoreType: Boolean = DEFAULT_IGNORE_TYPE_VALUE) = map { it.toExample(ignoreType) }

@AsParameters
internal data class ProducedValueExample(
        var type: ObjectChangeType?,
        var previousValue: Int?,
        var value: Int?
) {
    constructor() : this(null, null, null)
}
//endregion