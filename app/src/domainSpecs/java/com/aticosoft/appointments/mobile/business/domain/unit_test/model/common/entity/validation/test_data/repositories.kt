package com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.entity.validation.test_data

import com.aticosoft.appointments.mobile.business.domain.testing.model.TestRepositoryManager
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.JdoRepository
import com.aticosoft.appointments.mobile.business.infrastructure.persistence.PersistenceContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 15/11/15.
 */
@Singleton
internal class OddValueAndEmailParentRepository @Inject protected constructor(context: PersistenceContext) : JdoRepository<OddValueAndEmailParent>(context, QOddValueAndEmailParent.oddValueAndEmailParent)

@Singleton
internal class OddValueAndEmailParentRepositoryManager @Inject protected constructor(services: TestRepositoryManager.Services, repository: OddValueAndEmailParentRepository) : TestRepositoryManager<OddValueAndEmailParentRepository>(services, repository)

@Singleton
internal class PrimeNumberAndGmailParentRepository @Inject protected constructor(context: PersistenceContext) : JdoRepository<PrimeNumberAndGmailParent>(context, QPrimeNumberAndGmailParent.primeNumberAndGmailParent)

@Singleton
internal class PrimeNumberAndGmailParentRepositoryManager @Inject protected constructor(services: TestRepositoryManager.Services, repository: PrimeNumberAndGmailParentRepository) : TestRepositoryManager<PrimeNumberAndGmailParentRepository>(services, repository)