package com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.filtering.test_data

import com.aticosoft.appointments.mobile.business.domain.model.common.Entity
import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.AbstractTestData
import javax.jdo.annotations.PersistenceCapable

/**
 * Created by Rodrigo Quesada on 06/11/15.
 */
@PersistenceCapable
internal class TestDataParent(context: Entity.Context, value: Int, childValue: Int) : AbstractTestData(context, value) {

    var child: TestDataChild = TestDataChild(context, childValue)
        private set
}

@PersistenceCapable
internal class TestDataChild(context: Entity.Context, value: Int) : AbstractTestData(context, value)