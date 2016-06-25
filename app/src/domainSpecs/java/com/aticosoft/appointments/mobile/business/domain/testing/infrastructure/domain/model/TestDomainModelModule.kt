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
        com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.entity.validation.test_data.PrimeNumberAndGmailChildModule::class
))
internal class TestDomainModelModule