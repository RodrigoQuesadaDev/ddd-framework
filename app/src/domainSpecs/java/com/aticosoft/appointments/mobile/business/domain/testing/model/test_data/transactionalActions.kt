package com.aticosoft.appointments.mobile.business.domain.testing.model.test_data

import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.AbstractTransactionalAction
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.TransactionalAction
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.TransactionalUpdateAction
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.listener.sync.ObjectChangeType

/**
 * Created by Rodrigo Quesada on 04/11/16.
 */
internal interface TestAbstractTransactionalAction<E : AbstractTestData> : AbstractTransactionalAction<E> {

    fun resetConfiguration()
}

internal abstract class TestTransactionalAction<E : AbstractTestData>(vararg changeTypes: ObjectChangeType) : TestAbstractTransactionalAction<E>, TransactionalAction<E>(*changeTypes)

internal abstract class TestTransactionalUpdateAction<E : AbstractTestData> : TestAbstractTransactionalAction<E>, TransactionalUpdateAction<E>()