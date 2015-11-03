package com.aticosoft.appointments.mobile.business.domain.testing.model.test_data

import com.aticosoft.appointments.mobile.business.domain.model.common.CountQuery
import com.aticosoft.appointments.mobile.business.domain.model.common.ListQuery
import com.aticosoft.appointments.mobile.business.domain.model.common.UniqueQuery
import com.aticosoft.appointments.mobile.business.domain.testing.application.test_data.TestIsPrimeFilter
import com.aticosoft.appointments.mobile.business.domain.testing.application.test_data.TestValueIsFilter
import com.aticosoft.appointments.mobile.business.infrastructure.persistence.PersistenceContext
import com.rodrigodev.common.testing.number.primeNumbersUpTo
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 31/10/15.
 */
internal abstract class AbstractTestDataQueries<T : AbstractTestData> {

    abstract fun valueIs(value: Int, filter: TestValueIsFilter? = null): UniqueQuery<T>
}

@Singleton
internal class TestDataQueries @Inject constructor(private val context: PersistenceContext) : AbstractTestDataQueries<TestData>() {

    override fun valueIs(value: Int, filter: TestValueIsFilter?) = UniqueQuery {
        val d = QTestData.testData
        context.queryFactory.selectFrom(d).where(d.value.eq(value)).fetchOne()
    }
}

@Singleton
internal class TestDataParentQueries @Inject constructor(private val context: PersistenceContext) : AbstractTestDataQueries<TestDataParent>() {
    private companion object {
        val PRIME_NUMBERS_UP_TO_100 = primeNumbersUpTo(100)
    }

    override fun valueIs(value: Int, filter: TestValueIsFilter?) = UniqueQuery(*filter?.get(value) ?: arrayOf()) {
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