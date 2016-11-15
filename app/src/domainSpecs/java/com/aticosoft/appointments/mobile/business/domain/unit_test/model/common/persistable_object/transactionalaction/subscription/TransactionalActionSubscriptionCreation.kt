@file:Suppress("NOTHING_TO_INLINE")

package com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.persistable_object.transactionalaction.subscription

import com.aticosoft.appointments.mobile.business.domain.model.common.entity.Entity
import com.aticosoft.appointments.mobile.business.domain.specs.DomainStory
import com.aticosoft.appointments.mobile.business.domain.testing.model.TestEntityRepositoryManager
import com.aticosoft.appointments.mobile.business.domain.testing.model.TestTransactionalActionsManager
import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.AbstractTestData
import com.aticosoft.appointments.mobile.business.domain.unit_test.UnitTestApplication
import com.aticosoft.appointments.mobile.business.domain.unit_test.UnitTestApplicationComponent
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.persistable_object.transactionalaction.common.TransactionalActionStepBase
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.persistable_object.transactionalaction.common.test_data.LocalTestDataServices
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.persistable_object.transactionalaction.common.test_data.isOfType
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.persistable_object.transactionalaction.subscription.TransactionalActionSubscriptionCreation.LocalSteps.LocalObjectType
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.persistable_object.transactionalaction.subscription.TransactionalActionSubscriptionCreation.UnitTestApplicationImpl
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.persistable_object.transactionalaction.subscription.test_data.FiveSubscriptionsEntity
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.persistable_object.transactionalaction.subscription.test_data.LocalTestTransactionalAction
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.persistable_object.transactionalaction.subscription.test_data.NoSubscriptionsEntity
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.persistable_object.transactionalaction.subscription.test_data.OneSubscriptionEntity
import com.rodrigodev.common.assertj.Assertions.assertThatList
import com.rodrigodev.common.properties.Delegates.unsupportedOperation
import org.assertj.core.api.Assertions.assertThat
import org.jbehave.core.annotations.Given
import org.jbehave.core.annotations.Then
import org.robolectric.annotation.Config
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 03/11/16.
 */
@Config(application = UnitTestApplicationImpl::class)
internal class TransactionalActionSubscriptionCreation : DomainStory() {

    class UnitTestApplicationImpl : UnitTestApplication<TransactionalActionSubscriptionCreation>(UnitTestApplicationComponent::inject)

    @Inject protected lateinit var localSteps: LocalSteps

    init {
        steps { listOf(localSteps) }
    }

    class LocalSteps @Inject constructor(
            private val noSubscriptionsEntityMembers: LocalEntityMembers<NoSubscriptionsEntity>,
            private val oneSubscriptionEntityMembers: LocalEntityMembers<OneSubscriptionEntity>,
            private val fiveSubscriptionsEntityMembers: LocalEntityMembers<FiveSubscriptionsEntity>
    ) : TransactionalActionStepBase<LocalSteps, LocalObjectType, LocalTestTransactionalAction<*>>() {

        override val objectTypeValues = LocalObjectType.values()
        override val actionType = LocalTestTransactionalAction::class.java

        @Given("no declared transactional actions for \$objectType object changes")
        fun givenNoDeclaredTransactionalActionsForObjectChanges(objectType: LocalObjectType) {
            assertThat(objectType.declaredTransactionalActions.toList()).isEmpty()
        }

        @Given("\$objectType transactional actions with the next ids are declared: [\$ids]")
        fun givenTransactionalActionsWithTheNextIdsAreDeclared(objectType: LocalObjectType, ids: MutableList<Int>) {
            assertThatList(objectType.declaredTransactionalActions.toIds().toList())
                    .containsExactlyElementsOfInAnyOrder(ids)
        }

        @Then("no transactional actions are subscribed to \$objectType object changes")
        fun thenNoTransactionalActionsAreSubscribedToObjectChanges(objectType: LocalObjectType) = with(objectType) {
            assertThat(m.actionsManager.actionsForObjectType.toList()).isEmpty()
        }

        @Then("only 1 transactional action is subscribed to \$objectType object changes and it has the id \$id")
        fun thenOnly1TransactionalActionIsSubscribedToObjectChangesAndItHasTheId(objectType: LocalObjectType, id: Int) = with(objectType) {
            thenOnlyXTransactionalActionsAreSubscribedToObjectChangesAndTheyHaveTheIds(1, objectType, mutableListOf(id))
        }


        @Then("only \$totalSubscribedActions transactional actions are subscribed to \$objectType object changes and they have the ids [\$ids]")
        fun thenOnlyXTransactionalActionsAreSubscribedToObjectChangesAndTheyHaveTheIds(totalSubscribedActions: Int, objectType: LocalObjectType, ids: MutableList<Int>) = with(objectType) {
            require(totalSubscribedActions == ids.size)

            assertThatList(
                    m.actionsManager.actionsForObjectType.toIds().toList()
            )
                    .containsExactlyElementsOfInAnyOrder(ids)
        }

        //region Event Members
        @Singleton
        class LocalEntityMembers<E : AbstractTestData> @Inject protected constructor(
                override val repositoryManager: TestEntityRepositoryManager<E>,
                override val actionsManager: TestTransactionalActionsManager
        ) : EntityMembers<E, LocalTestDataServices<E>> {
            override val services: LocalTestDataServices<E> by unsupportedOperation()
        }
        //endregion

        //region Other Classes
        enum class LocalObjectType(
                override val entityClass: Class<out Entity>,
                override val eventMembers: LocalSteps.() -> LocalEntityMembers<*>
        ) : ObjectType<LocalSteps, LocalEntityMembers<*>> {
            NO_SUBSCRIPTIONS(NoSubscriptionsEntity::class.java, { noSubscriptionsEntityMembers }),
            ONE_SUBSCRIPTION(OneSubscriptionEntity::class.java, { oneSubscriptionEntityMembers }),
            FIVE_SUBSCRIPTIONS(FiveSubscriptionsEntity::class.java, { fiveSubscriptionsEntityMembers });

            val TestTransactionalActionsManager.actionsForObjectType: Sequence<LocalTestTransactionalAction<*>>
                get() = actions.asSequence()
                        .filter { it.javaClass.isOfType(entityClass) }
                        .map { it as LocalTestTransactionalAction<*> }
        }
        //endregion
    }
}

//region Utils
private inline fun Sequence<LocalTestTransactionalAction<*>>.toIds() = map { it.id }
//endregion