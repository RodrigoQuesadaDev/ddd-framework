package com.aticosoft.appointments.mobile.business.domain.testing.infrastructure.domain.model

import com.aticosoft.appointments.mobile.business.domain.model.common.entity.Entity
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.Queries
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.JdoRootEntityModule

/**
 * Created by Rodrigo Quesada on 27/06/16.
 */
// Test root entity modules do not need to define abstract query views
internal abstract class TestRootEntityModule<E : Entity> : JdoRootEntityModule<E, Queries<E>>