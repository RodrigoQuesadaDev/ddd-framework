@file:Suppress("NOTHING_TO_INLINE")

package com.aticosoft.appointments.mobile.business.domain.application.common.observation

import com.aticosoft.appointments.mobile.business.domain.model.common.Entity
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
            override lateinit var _filterTypes: Sequence<Class<out Entity>>
            override lateinit var fetchGroupName: String
        }
    }

    var _filterTypes: Sequence<Class<out Entity>>

    val fields: Array<out Path<*>>

    var fetchGroupName: String

    fun init() {
        _filterTypes = fields.filterTypes()
        fetchGroupName = if (fields.isNotEmpty()) nonDefaultFetchGroupName() else FetchPlan.DEFAULT
    }

    private inline fun nonDefaultFetchGroupName(): String = javaClass.simpleName + "." + ((this as Enum<*>)).name

    fun defaultFiltersFor(filters: Array<out EntityObservationFilter<*>>): Array<out EntityObservationFilter<*>> {
        return (_filterTypes - filters.toTypes())
                .map { EntityObservationFilter(it) }
                .toTypedArray()
    }
}

//region Filters
@Suppress("UNCHECKED_CAST")
private inline fun Array<out Path<*>>.filterTypes() = asSequence().flatMap { sequenceOf(it.type, it.root.type) }.filter { it.isEntityType() }.distinct() as Sequence<Class<out Entity>>

private inline fun Array<out EntityObservationFilter<*>>.toTypes() = asSequence().map { it.entityType }.distinct()

private inline fun Class<*>.isEntityType() = Entity::class.java.isAssignableFrom(this)
//endregion

//region Published Extensions
@Suppress("UNCHECKED_CAST")
internal fun Class<out Enum<*>>.queryViews(): Array<out QueryView> = getMethod(QueryViewEnum.VALUES_METHOD).invoke(null) as Array<QueryView>

private object QueryViewEnum {
    val VALUES_METHOD = "values"
}
//endregion