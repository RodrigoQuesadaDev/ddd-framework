package com.aticosoft.appointments.mobile.business.domain.testing.model.test_data

import com.aticosoft.appointments.mobile.business.domain.model.common.Entity
import javax.jdo.annotations.PersistenceCapable

/**
 * Created by rodrigo on 24/10/15.
 */
@PersistenceCapable
internal class TestData(id: Long, value: Int) : Entity(id) {

    var value: Int = value
}