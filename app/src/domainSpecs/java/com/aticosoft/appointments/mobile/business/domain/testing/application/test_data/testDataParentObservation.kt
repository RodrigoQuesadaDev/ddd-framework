package com.aticosoft.appointments.mobile.business.domain.testing.application.test_data

import com.aticosoft.appointments.mobile.business.domain.application.common.observation.EntityListener
import com.aticosoft.appointments.mobile.business.domain.application.common.observation.EntityObserver
import com.aticosoft.appointments.mobile.business.domain.testing.application.test_data.TestByIdFilter.DEFAULT
import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.TestDataChild
import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.TestDataParent
import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.TestDataParentRepository
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 09/11/15.
 */
@Singleton
internal class TestDataParentObserver @Inject constructor(services: TestDataParentObserver.Services) : EntityObserver<TestDataParent>(services, TestDataParent::class.java) {

    var byIdFilter: TestByIdFilter = DEFAULT

    override fun entityByIdFilters(id: Long) = byIdFilter.get(id) ?: super.entityByIdFilters(id)

    protected class Services @Inject constructor(override val entityRepository: TestDataParentRepository) : EntityObserver.Services<TestDataParent>()
}

@Singleton
internal class TestDataParentListener @Inject constructor(services: EntityListener.Services) : EntityListener<TestDataParent>(services, TestDataParent::class.java)

@Singleton
internal class TestDataChildListener @Inject constructor(services: EntityListener.Services) : EntityListener<TestDataChild>(services, TestDataChild::class.java)