package com.aticosoft.appointments.mobile.business.domain.testing.infrastructure.domain.model

import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.TestDataModule

import dagger.Module

/**
 * Created by Rodrigo Quesada on 04/12/15.
 */
@Module(includes = arrayOf(
        TestDataModule::class,
        com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_listener.test_data.TestDataParentModule::class,
        com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_listener.test_data.TestDataChildModule::class,
        com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.filtering.test_data.TestDataParentModule::class,
        com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.filtering.test_data.TestDataChildModule::class,
        com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.query_view.test_data.TestDataParentModule::class,
        com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.query_view.test_data.TestDataChildModule::class,
        com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.query_view.test_data.TestDataGrandChildModule::class,
        com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.entity.validation.test_data.OddValueAndEmailParentModule::class,
        com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.entity.validation.test_data.OddValueAndEmailChildModule::class,
        com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.entity.validation.test_data.PrimeNumberAndGmailParentModule::class,
        com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.entity.validation.test_data.PrimeNumberAndGmailChildModule::class,
        com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.valueobject.behavior.test_data.EntityWithValueObjectModule::class,
        com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.valueobject.implementation.correct_implementations.ValueObjectsModule::class,
        com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.valueobject.implementation.wrong_implementations.ValueObjectsModule::class,
        com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.valueobject.registration.test_data.ValueObjectsModule::class,
        com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.creation.test_data.NoSubscriptionsEventModule::class,
        com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.creation.test_data.OneSubscriptionEventModule::class,
        com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.creation.test_data.FiveSubscriptionsEventModule::class,
        com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.simpleaction.test_data.NoSubscriptionsEventModule::class,
        com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.simpleaction.test_data.ThreeSubscriptionsEventModule::class,
        com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.simpleaction.test_data.FiveSubscriptionsEventModule::class,
        com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.priority.test_data.SamePriorityEventModule::class,
        com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.priority.test_data.DifferentPriorityEventModule::class,
        com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.priority.test_data.DefaultPriorityEventModule::class,
        com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.timesreceived.test_data.SampleEventModule::class,
        com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.condition.test_data.SampleEventModule::class,
        com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.condition.test_data.BadCondModifiesEventModule::class,
        com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.condition.test_data.BadCondAddedAfterInitEventModule::class
))
internal class TestDomainModelModule