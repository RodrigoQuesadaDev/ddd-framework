package com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object

import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.listener.sync.ObjectChangeType
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.listener.sync.ObjectChangeType.ADD
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.listener.sync.ObjectChangeType.UPDATE
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 04/11/16.
 */
@Singleton
/*internal*/ interface AbstractTransactionalAction<P : PersistableObject<*>> {

    val objectType: Class<P>

    /*internal*/ val changeTypes: Array<out ObjectChangeType>
}

@Singleton
/*internal*/ abstract class TransactionalActionBase<P : PersistableObject<*>>() : AbstractTransactionalAction<P> {

    private lateinit var m: InjectedMembers<P>

    override val objectType: Class<P>
        get() = m.objectType

    //region Injection
    @Inject
    protected fun inject(injectedMembers: InjectedMembers<P>) {
        m = injectedMembers
    }

    protected class InjectedMembers<P : PersistableObject<*>> @Inject protected constructor(
            val objectType: Class<P>
    )
    //endregion
}

@Singleton
/*internal*/ abstract class TransactionalAction<P : PersistableObject<*>>(vararg changeTypes: ObjectChangeType = emptyArray()) : TransactionalActionBase<P>() {

    override val changeTypes = if (changeTypes.isEmpty()) arrayOf(ADD, UPDATE) else changeTypes

    abstract fun execute(value: P)
}

@Singleton
/*internal*/ abstract class TransactionalUpdateAction<P : PersistableObject<*>> : TransactionalActionBase<P>() {

    override val changeTypes = arrayOf(UPDATE)

    abstract fun execute(previousValue: P, currentValue: P)
}