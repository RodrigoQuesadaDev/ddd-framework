package com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.garbage.priority.test_data

import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.TestEvent
import com.google.auto.factory.AutoFactory
import javax.jdo.annotations.PersistenceCapable

/**
 * Created by Rodrigo Quesada on 25/08/16.
 */
@PersistenceCapable
//@AutoFactory
internal class SamePriorityEvent(value: Int) : TestEvent(value)

@PersistenceCapable
//@AutoFactory
internal class DifferentPriorityEvent(value: Int) : TestEvent(value)

@PersistenceCapable
//@AutoFactory
internal class DefaultPriorityEvent(value: Int) : TestEvent(value)