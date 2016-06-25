package com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_listener.test_data

import com.aticosoft.appointments.mobile.business.domain.model.common.CountQuery
import com.aticosoft.appointments.mobile.business.domain.model.common.EntityQueries
import com.aticosoft.appointments.mobile.business.domain.model.common.ListQuery
import com.aticosoft.appointments.mobile.business.domain.model.common.UniqueQuery
import com.aticosoft.appointments.mobile.business.infrastructure.persistence.PersistenceContext
import com.rodrigodev.common.testing.number.primeNumbersUpTo
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 14/11/15.
 */
@Singleton
internal class TestDataParentQueries @Inject constructor(private val context: PersistenceContext) : EntityQueries<TestDataParent> {
    private companion object {
        val PRIME_NUMBERS_UP_TO_100 = primeNumbersUpTo(100)
    }

    fun valueIs(value: Int, filter: TestValueIsFilter? = null) = UniqueQuery(*filter?.get(value) ?: arrayOf()) {
        val d = QTestDataParent.testDataParent
        context.queryFactory.selectFrom(d).where(d.value.eq(value)).fetchOne()
    }

    fun isPrime(primeFilter: TestIsPrimeFilter) = ListQuery(*primeFilter.get()) {
        val d = QTestDataParent.testDataParent
        context.queryFactory.selectFrom(d).where(d.value.`in`(PRIME_NUMBERS_UP_TO_100)).fetch()
    }

    fun isPrimeCount(primeFilter: TestIsPrimeFilter) = CountQuery<TestDataParent>(*primeFilter.get()) {
        val d = QTestDataParent.testDataParent
        context.queryFactory.selectFrom(d).where(d.value.`in`(PRIME_NUMBERS_UP_TO_100)).fetchCount()
    }
}