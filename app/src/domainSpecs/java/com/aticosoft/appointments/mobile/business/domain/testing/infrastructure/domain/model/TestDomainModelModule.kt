package com.aticosoft.appointments.mobile.business.domain.testing.infrastructure.domain.model

import com.aticosoft.appointments.mobile.business.domain.testing.infrastructure.domain.model.common.TestDomainModelCommonModule
import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.TestDataModule
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.DomainModelBaseModule
import dagger.Module

/**
 * Created by Rodrigo Quesada on 04/12/15.
 */
@Module(includes = arrayOf(
        DomainModelBaseModule::class,
        TestDomainModelCommonModule::class,
        TestDataModule::class,
        com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.persistable_object.listener.async.test_data.TestDataParentModule::class,
        com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.persistable_object.listener.async.test_data.TestDataChildModule::class,
        com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.filtering.test_data.TestDataParentModule::class,
        com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.filtering.test_data.TestDataChildModule::class,
        com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.query_view.test_data.TestDataParentModule::class,
        com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.query_view.test_data.TestDataChildModule::class,
        com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.query_view.test_data.TestDataGrandChildModule::class,
        com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.entity.validation.test_data.OddValueAndEmailParentModule::class,
        com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.entity.validation.test_data.OddValueAndEmailChildModule::class,
        com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.entity.validation.test_data.PrimeNumberAndGmailParentModule::class,
        com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.entity.validation.test_data.PrimeNumberAndGmailChildModule::class,
        com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.action.subscription.test_data.NoSubscriptionsEventModule::class,
        com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.action.subscription.test_data.OneSubscriptionEventModule::class,
        com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.action.subscription.test_data.FiveSubscriptionsEventModule::class,
        com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.action.executionorder.test_data.NoActionsEventModule::class,
        com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.action.executionorder.test_data.ThreeActionsEventModule::class,
        com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.action.executionorder.test_data.FiveActionsEventModule::class,
        com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.valueobject.behavior.test_data.EntityWithValueObjectModule::class,
        com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.valueobject.implementation.correct_implementations.ValueObjectsModule::class,
        com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.valueobject.implementation.wrong_implementations.ValueObjectsModule::class,
        com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.valueobject.registration.test_data.ValueObjectsModule::class,
        com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.persistable_object.transactionalaction.subscription.test_data.NoSubscriptionsEntityModule::class,
        com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.persistable_object.transactionalaction.subscription.test_data.OneSubscriptionEntityModule::class,
        com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.persistable_object.transactionalaction.subscription.test_data.FiveSubscriptionsEntityModule::class,
        com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.persistable_object.transactionalaction.observationbehavior.test_data.ParentSingleEntityModule::class,
        com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.persistable_object.transactionalaction.observationbehavior.test_data.ChildSingleEntityModule::class,
        com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.persistable_object.transactionalaction.observationbehavior.test_data.SampleMultiEntityModule::class,
        com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.persistable_object.transactionalaction.observationbehavior.test_data.SampleManyUpdatesEntityModule::class,
        com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.persistable_object.transactionalaction.observationbehavior.test_data.SampleUpdateDifferentObjectsEntityModule::class,
        com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.persistable_object.transactionalaction.observationbehavior.test_data.SampleManyActionsSameTypeEntityModule::class,
        com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.persistable_object.transactionalaction.observationbehavior.test_data.SampleUpdateWithPreviousValueEntityModule::class,
        com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.persistable_object.transactionalaction.observationbehavior.test_data.SampleDefaultEntityModule::class
))
internal class TestDomainModelModule