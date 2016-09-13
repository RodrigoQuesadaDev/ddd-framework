package com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.filtering.test_data

import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.CountQuery
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.ListQuery
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.UniqueQuery
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.persistable_object.JdoQueries
import com.rodrigodev.common.testing.number.primeNumbersUpTo
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 14/11/15.
 */
@Singleton
internal class TestDataParentQueries @Inject constructor() : JdoQueries<TestDataParent>() {
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