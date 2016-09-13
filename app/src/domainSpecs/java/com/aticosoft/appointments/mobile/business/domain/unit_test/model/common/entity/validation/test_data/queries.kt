package com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.entity.validation.test_data

import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.UniqueQuery
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.persistable_object.JdoQueries
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 14/11/15.
 */
@Singleton
internal class OddValueAndEmailParentQueries @Inject constructor() : JdoQueries<OddValueAndEmailParent>() {

    fun first() = UniqueQuery { context.queryFactory.selectFrom(QOddValueAndEmailParent.oddValueAndEmailParent).fetchFirst() }
}

@Singleton
internal class PrimeNumberAndGmailParentQueries @Inject constructor() : JdoQueries<PrimeNumberAndGmailParent>() {

    fun first() = UniqueQuery { context.queryFactory.selectFrom(QPrimeNumberAndGmailParent.primeNumberAndGmailParent).fetchFirst() }
}