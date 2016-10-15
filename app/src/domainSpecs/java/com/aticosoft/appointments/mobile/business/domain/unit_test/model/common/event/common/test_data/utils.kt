@file:Suppress("NOTHING_TO_INLINE")

package com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.common.test_data

import com.aticosoft.appointments.mobile.business.domain.model.common.event.Event
import com.aticosoft.appointments.mobile.business.domain.model.common.event.EventAction
import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.TestEventAction
import com.rodrigodev.common.reflection.createReflectionsForPackages
import com.rodrigodev.common.reflection.isSubOfOrSameAs
import com.rodrigodev.common.reflection.typeArgument


/**
 * Created by Rodrigo Quesada on 10/10/16.
 */
internal inline fun <reified R : TestEventAction<*>> Class<out Event>.declaredActions()
        : Sequence<R> = declaredActions(R::class.java)

internal inline fun <R : TestEventAction<*>> Class<out Event>.declaredActions(actionType: Class<R>)
        : Sequence<R> = createReflectionsForPackages(actionType.`package`)
        .getSubTypesOf(actionType).asSequence()
        .filter { it.typeArgument(EventAction::class.java, 0)!!.isSubOfOrSameAs(this) }
        .map { it.newInstance() }