package com.aticosoft.appointments.mobile.business.domain.testing.model.test_data

import com.aticosoft.appointments.mobile.business.domain.model.common.Entity
import javax.jdo.annotations.PersistenceCapable

/**
 * Created by Rodrigo Quesada on 24/10/15.
 */
@PersistenceCapable
internal open class TestData(context: Entity.Context, value: Int) : AbstractTestData(context, value)

internal abstract class AbstractTestData(context: Entity.Context, var value: Int) : Entity(context)