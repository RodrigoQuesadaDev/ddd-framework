@file:Suppress("NOTHING_TO_INLINE")

package com.aticosoft.appointments.mobile.business.domain.model.common

import com.aticosoft.appointments.mobile.business.domain.model.common.embedded.EmbeddedDelegate
import com.aticosoft.appointments.mobile.business.domain.model.common.embedded.EmbeddedValueObject

/**
 * Created by Rodrigo Quesada on 02/10/16.
 */
internal object Delegates {

    inline fun <T, E : EmbeddedValueObject> embedded(delegator: EmbeddedDelegate.Delegator<T, E>, noinline get: () -> E, noinline set: (E) -> Unit)
            = EmbeddedDelegate(EmbeddedDelegate.EmbeddedProperty(get, set), delegator)
}