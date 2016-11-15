@file:Suppress("NOTHING_TO_INLINE")

package com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.persistable_object.transactionalaction.common.test_data

import com.aticosoft.appointments.mobile.business.domain.application.common.service.ApplicationServices
import com.aticosoft.appointments.mobile.business.domain.application.common.service.CommandDelegates.Arrays.delegate
import com.aticosoft.appointments.mobile.business.domain.model.common.entity.EntityRepository
import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.AbstractTestData
import com.rodrigodev.common.rx.testing.triggerTestSchedulerActions
import javax.inject.Inject

/**
 * Created by Rodrigo Quesada on 05/12/15.
 */
internal abstract class LocalTestDataServices<E : AbstractTestData>(
        protected val createEntity: (Int) -> E
) : ApplicationServices() {

    private lateinit var m: InjectedMembers<E>

    class Insert(val values: List<Int>) : Command()

    fun execute(command: Insert) = command.customExecute {
        values.forEach {
            m.repository.add(createEntity(it))
        }
    }

    class Delete(val values: List<Int>) : Command()

    fun execute(command: Delete) = command.customExecute {
        with(m) {
            values.forEach {
                val data = repository.find(queries.valueIs(it))!!
                repository.remove(data)
            }
        }
    }

    class Change(val value: Int, val newValue: Int) : Command()

    private inline fun Change.change() = with(m) {
        val data = repository.find(queries.valueIs(value))!!
        data.value = newValue
    }

    fun execute(command: Change) = command.customExecute { change() }

    class ChangeSequentially(val value: Int, val newValues: List<Int>) : Command()

    fun execute(command: ChangeSequentially) = command.customExecute {
        with(m) {
            val data = repository.find(queries.valueIs(value))!!
            newValues.forEach { data.value = it }
        }
    }

    class ChangeMany(vararg changes: Change) : Command() {
        val changes: Array<out Change> by changes.delegate()
    }

    fun execute(command: ChangeMany) = command.customExecute {
        changes.forEach { it.change() }
    }

    class IncrementAll() : Command()

    fun execute(command: IncrementAll) = command.customExecute {
        with(m) {
            repository.find(queries.all()).forEach { ++it.value }
        }
    }

    private inline fun <C : Command> C.customExecute(crossinline call: C.() -> Unit) {
        execute { call() }
        triggerTestSchedulerActions()
    }

    //region Injection
    @Inject
    protected fun inject(injectedMembers: InjectedMembers<E>) {
        m = injectedMembers
    }

    protected class InjectedMembers<E : AbstractTestData> @Inject protected constructor(
            val repository: EntityRepository<E>,
            val queries: LocalTestDataQueries<E>
    )
    //endregion
}