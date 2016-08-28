package com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.test_data

import com.aticosoft.appointments.mobile.business.domain.model.common.event.Event
import javax.jdo.annotations.PersistenceCapable

/**
 * Created by Rodrigo Quesada on 25/08/16.
 */
@PersistenceCapable
//@AutoFactory
internal class TestEventA(var value: Int) : Event() {
    private constructor() : this(0)
}

@PersistenceCapable
//@AutoFactory
internal class TestEventB(var value: Int) : Event() {
    private constructor() : this(0)
}