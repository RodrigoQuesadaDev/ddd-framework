package com.aticosoft.appointments.mobile.business.domain.model.common

import com.aticosoft.appointments.mobile.business.domain.application.common.observation.EntityObservationFilter

/**
 * Created by Rodrigo Quesada on 04/11/15.
 */
/*internal*/ interface EntityQueries<E : Entity>

/*internal*/ abstract class Query<R>(
        val filters: Array<out EntityObservationFilter<*>>,
        private val body: () -> R
) {
    internal fun execute(): R = body()
}

/*internal*/ class UniqueQuery<E : Entity>(
        vararg filters: EntityObservationFilter<*>,
        body: () -> E?
) : Query<E?>(filters, body = body)

/*internal*/ class ListQuery<E : Entity>(
        vararg filters: EntityObservationFilter<*>,
        body: () -> List<E>
) : Query<List<E>>(filters, body = body)

/*internal*/ class CountQuery<E : Entity>(
        vararg filters: EntityObservationFilter<*>,
        body: () -> Long
) : Query<Long>(filters, body = body)