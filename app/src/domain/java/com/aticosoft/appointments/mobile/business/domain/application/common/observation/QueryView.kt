@file:Suppress("NOTHING_TO_INLINE")

package com.aticosoft.appointments.mobile.business.domain.application.common.observation

import com.querydsl.core.types.Path
import com.rodrigodev.common.collection.toTypedArray
import javax.jdo.FetchPlan

/**
 * Created by Rodrigo Quesada on 17/11/15.
 */
/*internal*/ interface QueryView {
    companion object {
        val DEFAULT = object : QueryView {
            override val fields = emptyArray<Path<*>>()
        }
    }

    val fields: Array<out Path<*>>

    val fetchGroupName: String
        get() = if (fields.isNotEmpty()) nonDefaultFetchGroupName() else FetchPlan.DEFAULT

    private inline fun nonDefaultFetchGroupName(): String = javaClass.simpleName + "." + ((this as Enum<*>)).name

    fun defaultFiltersFor(filters: Array<out EntityObservationFilter<*>>): Array<out EntityObservationFilter<*>> {
        return (fields.filterTypes() - filters.toTypes())
                .map { EntityObservationFilter(it) }
                .toTypedArray()
    }
}

private inline fun Array<out Path<*>>.filterTypes(): Sequence<Class<*>> = asSequence().flatMap { sequenceOf(it.type, it.root.type) }.distinct()

private inline fun Array<out EntityObservationFilter<*>>.toTypes() = asSequence().map { it.entityType }.distinct()

@Suppress("UNCHECKED_CAST")
internal inline fun Class<out Enum<*>>.queryViews(): Array<out QueryView> = getMethod(QueryViewEnum.VALUES_METHOD).invoke(null) as Array<QueryView>

private object QueryViewEnum {
    val VALUES_METHOD = "values"
}