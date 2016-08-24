package com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.filtering.test_data

import com.aticosoft.appointments.mobile.business.domain.application.common.observation.QueryView
import com.aticosoft.appointments.mobile.business.domain.application.common.observation.entity.EntityObserver
import com.aticosoft.appointments.mobile.business.domain.application.common.observation.persistable_object.PersistableObjectObservationFilter
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.PersistableObject
import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.filtering.test_data.TestByIdFilter.DEFAULT
import com.querydsl.core.types.Path
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 09/11/15.
 */
@Singleton
internal class TestDataParentObserver @Inject constructor() : EntityObserver<TestDataParent>() {

    override val defaultQueryView: QueryView = TestDataParentQueryView.DEFAULT

    var byIdFilter: TestByIdFilter = DEFAULT

    override fun objectByIdFilters(id: String) = byIdFilter.get(id) ?: super.objectByIdFilters(id)

    override fun Array<out PersistableObjectObservationFilter<*>>.plusDefaultFiltersFrom(queryView: QueryView) = this
}

internal enum class TestDataParentQueryView(override vararg val fields: Path<*>) : QueryView {
    DEFAULT(QTestDataParent.testDataParent.child);

    override lateinit var _filterTypes: Sequence<Class<out PersistableObject<*>>>
    override lateinit var fetchGroupName: String
}