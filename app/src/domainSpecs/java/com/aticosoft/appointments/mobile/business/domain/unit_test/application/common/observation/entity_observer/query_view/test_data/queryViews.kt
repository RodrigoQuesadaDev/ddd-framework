package com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.query_view.test_data

import com.aticosoft.appointments.mobile.business.domain.application.common.observation.QueryView
import com.aticosoft.appointments.mobile.business.domain.model.common.Entity
import com.querydsl.core.types.Path

/**
 * Created by Rodrigo Quesada on 17/11/15.
 */
internal enum class TestDataParentQueryView(override vararg val fields: Path<*>) : QueryView {
    ONLY_PARENT(),
    PARENT_EMBEDDED_1(p.embedded1),
    PARENT_EMBEDDED_2(p.embedded2),
    PARENT_CHILD_1(p.child1),
    PARENT_EMBEDDED_2_CHILD_1(p.embedded2, p.child1),
    PARENT_CHILD_2(p.child2),
    PARENT_EMBEDDED_1_CHILD_2(p.embedded1, p.child2),
    PARENT_CHILD_1_AND_GRANDCHILD_2(p.child1, c.grandChild2),
    PARENT_EMBEDDED_1_CHILD_1_AND_GRANDCHILD_2(p.embedded1, p.child1, c.grandChild2),
    PARENT_EMBEDDED_1_CHILD_1_AND_GRANDCHILD_2_EMBEDDED_1(p.embedded1, p.child1, c.grandChild2, g.embedded1),
    PARENT_CHILD_2_AND_GRANDCHILD_1(p.child2, c.grandChild1),
    PARENT_EMBEDDED_2_CHILD_2_AND_GRANDCHILD_1(p.embedded2, p.child2, c.grandChild1),
    PARENT_EMBEDDED_2_CHILD_2_AND_GRANDCHILD_1_EMBEDDED_2(p.embedded2, p.child2, c.grandChild1, g.embedded2),
    PARENT_EMBEDDEDS_CHILD_2_AND_GRANDCHILD_1_EMBEDDEDS(p.embedded1, p.embedded2, p.child2, c.grandChild1, g.embedded1, g.embedded2),
    PARENT_CHILD_2_AND_GRANDCHILD_2(p.child2, c.grandChild2);

    override lateinit var _filterTypes: Sequence<Class<out Entity>>
    override lateinit var fetchGroupName: String
}

private val p = QTestDataParent.testDataParent
private val c = QTestDataChild.testDataChild
private val g = QTestDataGrandChild.testDataGrandChild