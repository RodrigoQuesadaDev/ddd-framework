@file:Suppress("NOTHING_TO_INLINE")

package com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.persistable_object.transactionalaction.common.test_data

import com.aticosoft.appointments.mobile.business.domain.model.common.entity.Entity
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.AbstractTransactionalAction
import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.TestAbstractTransactionalAction
import com.rodrigodev.common.reflection.createReflectionsForPackages
import com.rodrigodev.common.reflection.isSubOfOrSameAs
import com.rodrigodev.common.reflection.typeArgument


/**
 * Created by Rodrigo Quesada on 10/10/16.
 */
internal inline fun <reified R : TestAbstractTransactionalAction<*>> Class<out Entity>.declaredActions()
        : Sequence<R> = declaredActions(R::class.java)

internal inline fun <R : TestAbstractTransactionalAction<*>> Class<out Entity>.declaredActions(actionType: Class<R>)
        : Sequence<R> = createReflectionsForPackages(actionType.`package`)
        .getSubTypesOf(actionType).asSequence()
        .filter { it.isOfType(this) }
        .map { it.newInstance() }

internal inline fun Class<out AbstractTransactionalAction<*>>.isOfType(entityType: Class<out Entity>)
        : Boolean = typeArgument(AbstractTransactionalAction::class.java, 0)!!.isSubOfOrSameAs(entityType)