package com.aticosoft.appointments.mobile.business.domain.testing.model

import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.AbstractTransactionalAction
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.TransactionalActionsManager
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 04/11/16.
 */
@Singleton
internal class TestTransactionalActionsManager @Inject protected constructor() : TransactionalActionsManager() {

    public override val actions: MutableSet<AbstractTransactionalAction<*>>
        get() = super.actions
}