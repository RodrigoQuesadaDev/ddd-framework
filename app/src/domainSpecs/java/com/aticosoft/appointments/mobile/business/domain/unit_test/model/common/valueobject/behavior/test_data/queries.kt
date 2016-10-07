package com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.valueobject.behavior.test_data

import com.aticosoft.appointments.mobile.business.domain.application.common.observation.QueryView
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.PersistableObject
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.UniqueQuery
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.persistable_object.JdoQueries
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 06/10/16.
 */
@Singleton
internal class EntityWithValueObjectQueries @Inject constructor() : JdoQueries<EntityWithValueObject>() {

    fun valueIs(value: Int) = UniqueQuery {
        val e = QEntityWithValueObject.entityWithValueObject
        context.queryFactory.selectFrom(e).where(e.valueObject.value.eq(value)).fetchOne()
    }
}

internal enum class EntityWithValueObjectQueryView : QueryView {
    ;

    override lateinit var _filterTypes: Sequence<Class<out PersistableObject<*>>>
    override lateinit var fetchGroupName: String
}