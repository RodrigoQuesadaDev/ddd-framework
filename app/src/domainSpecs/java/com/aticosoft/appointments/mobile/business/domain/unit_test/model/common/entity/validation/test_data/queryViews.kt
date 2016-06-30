package com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.entity.validation.test_data

import com.aticosoft.appointments.mobile.business.domain.application.common.observation.QueryView
import com.aticosoft.appointments.mobile.business.domain.model.common.Entity
import com.querydsl.core.types.Path

/**
 * Created by Rodrigo Quesada on 17/11/15.
 */
internal enum class OddValueAndEmailParentQueryView(override vararg val fields: Path<*>) : QueryView {
    ALL(QOddValueAndEmailParent.oddValueAndEmailParent.child);

    override lateinit var _filterTypes: Sequence<Class<out Entity>>
    override lateinit var fetchGroupName: String
}

internal enum class PrimeNumberAndGmailParentQueryView(override vararg val fields: Path<*>) : QueryView {
    ALL(QPrimeNumberAndGmailParent.primeNumberAndGmailParent.child);

    override lateinit var _filterTypes: Sequence<Class<out Entity>>
    override lateinit var fetchGroupName: String
}