@file:Suppress("NOTHING_TO_INLINE")

package com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.valueobject.behavior.test_data

import com.aticosoft.appointments.mobile.business.domain.application.common.service.ApplicationServices
import com.aticosoft.appointments.mobile.business.domain.model.common.entity.EntityRepository
import com.rodrigodev.common.kotlin.pass
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 06/10/16.
 */
@Singleton
internal class TestDataServices @Inject protected constructor(
        private val factory: EntityWithValueObjectFactory,
        private val repository: EntityRepository<EntityWithValueObject>,
        private val queries: EntityWithValueObjectQueries
) : ApplicationServices() {

    class CreateValueObject(val value: Int) : Command()

    fun execute(command: CreateValueObject) = command.execute {
        createEntityWithValueObject(value)
    }

    class CreateAndThenModified(val value: Int) : Command()

    fun execute(command: CreateAndThenModified) = command.execute {
        val entity = createEntityWithValueObject(value)
        entity.valueObject.value++
    }

    class FindAndThenModified(val value: Int) : Command()

    fun execute(command: FindAndThenModified) = command.execute {
        val entity = repository.find(queries.valueIs(value))!!
        entity.valueObject.value++
    }

    private fun createEntityWithValueObject(value: Int): EntityWithValueObject
            = factory.create(value).pass { repository.add(it) }
}