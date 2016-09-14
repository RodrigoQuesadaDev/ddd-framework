package com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object

import com.aticosoft.appointments.mobile.business.domain.application.common.observation.persistable_object.PersistableObjectObservationFilter

/**
 * Created by Rodrigo Quesada on 04/11/15.
 */
/*internal*/ interface Queries<P : PersistableObject<*>>

/*internal*/ abstract class Query<out R>(
        val filters: Array<out PersistableObjectObservationFilter<*>>,
        private val body: () -> R
) {
    internal fun execute(): R = body()
}

/*internal*/ class UniqueQuery<out P : PersistableObject<*>>(
        vararg filters: PersistableObjectObservationFilter<*>,
        body: () -> P?
) : Query<P?>(filters, body = body)

/*internal*/ class ListQuery<out P : PersistableObject<*>>(
        vararg filters: PersistableObjectObservationFilter<*>,
        body: () -> List<P>
) : Query<List<P>>(filters, body = body)

/*internal*/ class CountQuery<out P : PersistableObject<*>>(
        vararg filters: PersistableObjectObservationFilter<*>,
        body: () -> Long
) : Query<Long>(filters, body = body)