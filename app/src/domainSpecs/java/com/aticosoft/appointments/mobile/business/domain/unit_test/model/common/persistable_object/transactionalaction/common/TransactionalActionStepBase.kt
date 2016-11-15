package com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.persistable_object.transactionalaction.common

import com.aticosoft.appointments.mobile.business.domain.model.common.entity.Entity
import com.aticosoft.appointments.mobile.business.domain.testing.model.TestEntityRepositoryManager
import com.aticosoft.appointments.mobile.business.domain.testing.model.TestTransactionalActionsManager
import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.AbstractTestData
import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.TestAbstractTransactionalAction
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.persistable_object.transactionalaction.common.TransactionalActionStepBase.ObjectType
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.persistable_object.transactionalaction.common.test_data.LocalTestDataServices
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.persistable_object.transactionalaction.common.test_data.LocalTestDataServices.*
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.persistable_object.transactionalaction.common.test_data.declaredActions
import com.rodrigodev.common.spec.story.steps.SpecSteps
import org.assertj.core.api.Assertions.assertThat
import org.jbehave.core.annotations.Given
import org.jbehave.core.annotations.When

/**
 * Created by Rodrigo Quesada on 04/11/16.
 */
internal abstract class TransactionalActionStepBase<S : TransactionalActionStepBase<S, *, *>, T : ObjectType<S, *>, A : TestAbstractTransactionalAction<*>> : SpecSteps() {

    protected abstract val objectTypeValues: Array<T>
    protected abstract val actionType: Class<A>

    @Given("transactional actions configuration is reset")
    fun givenTransactionalActionsConfigurationIsReset() {
        objectTypeValues.forEach { t -> t.declaredTransactionalActions.forEach { a -> a.resetConfiguration() } }
    }

    @Given("\$totalDefinedActions transactional action{s|} defined for \$objectType object")
    fun givenXTransactionalActionsDefinedForObject(totalDefinedActions: Int, objectType: T) {
        assertThat(objectType.declaredTransactionalActions.toList()).hasSize(totalDefinedActions)
    }

    @When("{after that |}I insert \$objectType objects [\$objects]")
    fun whenIInsertObjects(objectType: T, objects: MutableList<Int>) = with(objectType) {
        m.services.execute(Insert(objects))
    }

    @When("{after that |}I delete \$objectType objects [\$objects]")
    fun whenIDeleteObjects(objectType: T, objects: MutableList<Int>) = with(objectType) {
        m.services.execute(Delete(objects))
    }

    @When("{after that |}I change \$objectType object \$value into \$newValue")
    fun whenAfterThatIChangeObjectInto(objectType: T, value: Int, newValue: Int) = with(objectType) {
        m.services.execute(Change(value, newValue))
    }

    @When("{after that |}I sequentially change \$objectType object \$value into the next values (one after another; same transaction) [\$newValues]")
    fun whenAfterThatISequentiallyChangeObjectIntoTheNextValues(objectType: T, value: Int, newValues: MutableList<Int>) = with(objectType) {
        m.services.execute(ChangeSequentially(value, newValues))
    }

    @When("{after that |}I change \$objectType objects (same transaction): \$value1 into \$newValue1 and \$value2 into \$newValue2")
    fun whenAfterThatIChangeObjectsInto(objectType: T, value1: Int, newValue1: Int, value2: Int, newValue2: Int) = with(objectType) {
        m.services.execute(ChangeMany(Change(value1, newValue1), Change(value2, newValue2)))
    }

    @When("{after that |}I increment all existing \$objectType objects (same transaction)")
    fun whenIIncrementAllExistingObjects(objectType: T) = with(objectType) {
        m.services.execute(IncrementAll())
    }

    protected val T.declaredTransactionalActions: Sequence<A>
        get() = entityClass.declaredActions(actionType)

    interface ObjectType<S : TransactionalActionStepBase<S, *, *>, out M : EntityMembers<*, *>> {
        val entityClass: Class<out Entity>
        val eventMembers: S.() -> M
        val TransactionalActionStepBase<*, *, *>.m: M
            @Suppress("UNCHECKED_CAST")
            get() = eventMembers(this as S)
    }

    interface EntityMembers<E : AbstractTestData, out S : LocalTestDataServices<*>> {
        val repositoryManager: TestEntityRepositoryManager<E>
        val actionsManager: TestTransactionalActionsManager
        val services: S
    }
}