package com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.query_view.test_data

import com.aticosoft.appointments.mobile.business.domain.application.common.observation.QueryView
import com.querydsl.core.types.Path

/**
 * Created by Rodrigo Quesada on 17/11/15.
 */
internal enum class TestDataParentQueryView(override vararg val fields: Path<*>) : QueryView {
    ONLY_PARENT(),
    PARENT_CHILD_1(QTestDataParent.testDataParent.child1),
    PARENT_CHILD_2(QTestDataParent.testDataParent.child2),
    PARENT_CHILD_1_AND_GRANDCHILD_2(QTestDataParent.testDataParent.child1, QTestDataChild.testDataChild.grandChild2),
    PARENT_CHILD_2_AND_GRANDCHILD_1(QTestDataParent.testDataParent.child2, QTestDataChild.testDataChild.grandChild1),
    PARENT_CHILD_2_AND_GRANDCHILD_2(QTestDataParent.testDataParent.child2, QTestDataChild.testDataChild.grandChild2)
}