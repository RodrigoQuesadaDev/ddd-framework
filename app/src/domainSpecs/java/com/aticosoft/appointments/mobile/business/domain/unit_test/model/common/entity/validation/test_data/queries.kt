package com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.entity.validation.test_data

import com.aticosoft.appointments.mobile.business.domain.model.common.EntityQueries
import com.aticosoft.appointments.mobile.business.domain.model.common.UniqueQuery
import com.aticosoft.appointments.mobile.business.infrastructure.persistence.PersistenceContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 14/11/15.
 */
@Singleton
internal class OddValueAndEmailParentQueries @Inject constructor(private val context: PersistenceContext) : EntityQueries<OddValueAndEmailParent> {

    fun first() = UniqueQuery { context.queryFactory.selectFrom(QOddValueAndEmailParent.oddValueAndEmailParent).fetchFirst() }
}

@Singleton
internal class PrimeNumberAndGmailParentQueries @Inject constructor(private val context: PersistenceContext) : EntityQueries<PrimeNumberAndGmailParent> {

    fun first() = UniqueQuery { context.queryFactory.selectFrom(QPrimeNumberAndGmailParent.primeNumberAndGmailParent).fetchFirst() }
}