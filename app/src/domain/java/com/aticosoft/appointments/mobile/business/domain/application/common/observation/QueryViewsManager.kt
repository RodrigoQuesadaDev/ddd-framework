@file:Suppress("NOTHING_TO_INLINE")

package com.aticosoft.appointments.mobile.business.domain.application.common.observation

import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.QueryViews
import com.aticosoft.appointments.mobile.business.infrastructure.persistence.PersistenceContext
import com.querydsl.core.types.Path
import com.rodrigodev.common.collection.peek
import com.rodrigodev.common.collection.toTypedArray
import javax.inject.Inject
import javax.inject.Singleton
import javax.jdo.FetchGroup
import javax.jdo.FetchPlan
import javax.jdo.PersistenceManagerFactory

/**
 * Created by Rodrigo Quesada on 17/11/15.
 */
@Singleton
/*internal*/ class QueryViewsManager @Inject constructor(
        @QueryViews private val queryViewEnumDefinitions: MutableSet<Class<out Enum<*>>>,
        private val pmf: PersistenceManagerFactory,
        private val context: PersistenceContext
) {
    init {
        registerViews()
    }

    private fun registerViews() {
        queryViewEnumDefinitions.asSequence()
                .flatMap { definition -> definition.queryViews().asSequence() }
                .peek { it.init() }
                .flatMap { queryView ->
                    queryView.fields.asSequence()
                            .checkFields()
                            .toFetchGroupInfo(queryView)
                }
                .forEach { fetchGroupInfo ->
                    pmf.addFetchGroupForRoot(fetchGroupInfo)
                    pmf.addFetchGroupForFieldType(fetchGroupInfo)
                }

        pmf.addBasicMembersToFetchGroups()
    }

    internal inline fun <R> withView(view: QueryView, call: () -> R): R {
        context.persistenceManager.fetchPlan.setGroup(view.fetchGroupName).setNoLimitFetchDepth()
        return call()
    }
}

//region Register Views
private inline fun Sequence<Path<*>>.checkFields() = peek { check(it.metadata.parent?.metadata?.isRoot ?: false, { "Specified fields for QueryView must be direct fields of root element." }) }

private inline fun PersistenceManagerFactory.addFetchGroupForRoot(info: FetchGroupInfo) = with(info) {
    val fetchGroup = getFetchGroup(field.root.type, name)
    fetchGroup.addMember(field.metadata.name)
    addFetchGroups(fetchGroup)
}

private inline fun PersistenceManagerFactory.addFetchGroupForFieldType(info: FetchGroupInfo) = with(info) {
    addFetchGroups(getFetchGroup(field.type, name))
}

private inline fun PersistenceManagerFactory.addBasicMembersToFetchGroups() {
    addFetchGroups(*fetchGroups.asSequence().map {
        (it as FetchGroup).addCategory(FetchGroup.DEFAULT)
    }.toTypedArray())
}

private inline fun Sequence<Path<*>>.toFetchGroupInfo(view: QueryView) = map { FetchGroupInfo(view.fetchGroupName, it) }

private class FetchGroupInfo(val name: String, val field: Path<*>)
//endregion

//region General
private fun FetchPlan.setNoLimitFetchDepth() {
    maxFetchDepth = FetchDepth.NO_LIMIT
}

private object FetchDepth {
    val NO_LIMIT = -1
}
//endregion