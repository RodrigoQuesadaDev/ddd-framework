package com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.filtering.test_data

import com.aticosoft.appointments.mobile.business.domain.application.common.observation.EntityListener
import com.aticosoft.appointments.mobile.business.domain.application.common.observation.EntityObservationFilter
import com.aticosoft.appointments.mobile.business.domain.application.common.observation.EntityObserver
import com.aticosoft.appointments.mobile.business.domain.application.common.observation.QueryView
import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.filtering.test_data.TestByIdFilter.DEFAULT
import com.querydsl.core.types.Path
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 09/11/15.
 */
@Singleton
internal class TestDataParentObserver @Inject constructor(services: EntityObserver.Services, testDataParentRepository: TestDataParentRepository) : EntityObserver<TestDataParent>(services, testDataParentRepository, TestDataParent::class.java) {

    override val defaultQueryView: QueryView = TestDataParentQueryView.DEFAULT

    var byIdFilter: TestByIdFilter = DEFAULT

    override fun entityByIdFilters(id: Long) = byIdFilter.get(id) ?: super.entityByIdFilters(id)

    override fun Array<out EntityObservationFilter<*>>.plusDefaultFiltersFrom(queryView: QueryView) = this
}

@Singleton
internal class TestDataParentListener @Inject constructor(services: EntityListener.Services) : EntityListener<TestDataParent>(services, TestDataParent::class.java)

@Singleton
internal class TestDataChildListener @Inject constructor(services: EntityListener.Services) : EntityListener<TestDataChild>(services, TestDataChild::class.java)

internal enum class TestDataParentQueryView(override vararg val fields: Path<*>) : QueryView {
    DEFAULT(QTestDataParent.testDataParent.child)
}

@Singleton
internal class TestEntityListenersProviderForFiltering @Inject constructor(
        val testDataParentListenerProvider: Provider<TestDataParentListener>,
        val testDataChildListenerProvider: Provider<TestDataChildListener>
) {
    fun get(): Array<EntityListener<*>> = arrayOf(testDataParentListenerProvider.get(), testDataChildListenerProvider.get())
}