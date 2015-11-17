package com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.query_view.test_data

import com.aticosoft.appointments.mobile.business.domain.application.common.observation.EntityListener
import com.aticosoft.appointments.mobile.business.domain.application.common.observation.EntityObserver
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 17/11/15.
 */
@Singleton
internal class TestDataParentObserver @Inject constructor(services: EntityObserver.Services, testDataParentRepository: TestDataParentRepository) : EntityObserver<TestDataParent>(services, testDataParentRepository, TestDataParent::class.java)

@Singleton
internal class TestDataParentListener @Inject constructor(services: EntityListener.Services) : EntityListener<TestDataParent>(services, TestDataParent::class.java)

@Singleton
internal class TestDataChildListener @Inject constructor(services: EntityListener.Services) : EntityListener<TestDataChild>(services, TestDataChild::class.java)

@Singleton
internal class TestDataGrandChildListener @Inject constructor(services: EntityListener.Services) : EntityListener<TestDataGrandChild>(services, TestDataGrandChild::class.java)