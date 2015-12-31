package com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.entity.validation.test_data

import com.aticosoft.appointments.mobile.business.domain.application.common.observation.EntityObserver
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 17/11/15.
 */
@Singleton
internal class OddValueAndEmailParentObserver @Inject constructor(services: EntityObserver.Services, repository: OddValueAndEmailParentRepository) : EntityObserver<OddValueAndEmailParent>(services, repository, OddValueAndEmailParent::class.java)

@Singleton
internal class PrimeNumberAndGmailParentObserver @Inject constructor(services: EntityObserver.Services, repository: PrimeNumberAndGmailParentRepository) : EntityObserver<PrimeNumberAndGmailParent>(services, repository, PrimeNumberAndGmailParent::class.java)