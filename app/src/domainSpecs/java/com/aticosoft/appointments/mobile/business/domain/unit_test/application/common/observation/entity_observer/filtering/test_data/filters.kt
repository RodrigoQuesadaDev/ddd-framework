package com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.filtering.test_data

import com.aticosoft.appointments.mobile.business.domain.application.common.observation.EntityChangeEvent
import com.aticosoft.appointments.mobile.business.domain.application.common.observation.EntityObservationFilter
import com.rodrigodev.common.testing.number.isDivisibleBy
import com.rodrigodev.common.testing.number.isEven
import com.rodrigodev.common.testing.number.isOdd
import com.rodrigodev.common.testing.number.isPrime

/**
 * Created by Rodrigo Quesada on 11/11/15.
 */
enum class TestChildFilter {
    ODD_CHILD {
        override fun get(): Array<EntityObservationFilter<*>> = arrayOf(EntityObservationFilter(TestDataChild::class.java) { it.value.isOdd() })
    },
    ODD_CHILD_REMOVED {
        override fun get(): Array<EntityObservationFilter<*>> = arrayOf(EntityObservationFilter(TestDataChild::class.java, EntityChangeEvent.EventType.REMOVE) { it.value.isOdd() })
    },
    ODD_AND_DIVISIBLE_BY_THREE_CHILD {
        override fun get(): Array<EntityObservationFilter<*>> = arrayOf(EntityObservationFilter(TestDataChild::class.java) { with(it.value) { isOdd() && isDivisibleBy(3) } })
    };

    abstract fun get(): Array<EntityObservationFilter<*>>
}

enum class TestParentFilter {
    PRIME_PARENT {
        override fun get(): Array<EntityObservationFilter<*>> = arrayOf(EntityObservationFilter(TestDataParent::class.java) { it.value.isPrime() })
    },
    EVEN_PARENT {
        override fun get(): Array<EntityObservationFilter<*>> = arrayOf(EntityObservationFilter(TestDataParent::class.java) { it.value.isEven() })
    };

    abstract fun get(): Array<EntityObservationFilter<*>>
}

enum class TestByIdFilter() {
    DEFAULT {
        override fun get(id: String) = null
    },
    ODD_CHILD {
        override fun get(id: String) = TestChildFilter.ODD_CHILD.get()
    },
    ODD_CHILD_REMOVED {
        override fun get(id: String) = TestChildFilter.ODD_CHILD_REMOVED.get()
    },
    ODD_AND_DIVISIBLE_BY_THREE_CHILD {
        override fun get(id: String) = TestChildFilter.ODD_AND_DIVISIBLE_BY_THREE_CHILD.get()
    };

    abstract fun get(id: String): Array<EntityObservationFilter<*>>?
}

enum class TestValueIsFilter {
    PARENT_WITH_VALUE {
        override fun get(value: Int): Array<EntityObservationFilter<*>> = arrayOf(EntityObservationFilter(TestDataParent::class.java) { it.value == value })
    },
    EVEN_PARENT {
        override fun get(value: Int) = TestParentFilter.EVEN_PARENT.get()
    },
    ODD_CHILD {
        override fun get(value: Int) = TestChildFilter.ODD_CHILD.get()
    },
    ODD_CHILD_REMOVED {
        override fun get(value: Int) = TestChildFilter.ODD_CHILD_REMOVED.get()
    },
    ODD_AND_DIVISIBLE_BY_THREE_CHILD {
        override fun get(value: Int) = TestChildFilter.ODD_AND_DIVISIBLE_BY_THREE_CHILD.get()
    };

    abstract fun get(value: Int): Array<EntityObservationFilter<*>>
}

enum class TestIsPrimeFilter {
    PRIME_PARENT {
        override fun get() = TestParentFilter.PRIME_PARENT.get()
    },
    EVEN_PARENT {
        override fun get() = TestParentFilter.EVEN_PARENT.get()
    },
    ODD_CHILD {
        override fun get() = TestChildFilter.ODD_CHILD.get()
    },
    ODD_CHILD_REMOVED {
        override fun get() = TestChildFilter.ODD_CHILD_REMOVED.get()
    },
    ODD_AND_DIVISIBLE_BY_THREE_CHILD {
        override fun get() = TestChildFilter.ODD_AND_DIVISIBLE_BY_THREE_CHILD.get()
    };

    abstract fun get(): Array<EntityObservationFilter<*>>
}