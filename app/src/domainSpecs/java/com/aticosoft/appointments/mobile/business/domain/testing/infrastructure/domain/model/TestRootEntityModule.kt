package com.aticosoft.appointments.mobile.business.domain.testing.infrastructure.domain.model

import com.aticosoft.appointments.mobile.business.domain.application.common.observation.EntityListener
import com.aticosoft.appointments.mobile.business.domain.model.common.Entity
import com.aticosoft.appointments.mobile.business.domain.model.common.EntityQueries
import com.aticosoft.appointments.mobile.business.domain.model.common.Repository
import com.aticosoft.appointments.mobile.business.domain.model.common.validation.EntityValidator
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.RootEntityModule
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.entity.EntityInitializer

/**
 * Created by Rodrigo Quesada on 27/06/16.
 */
internal abstract class TestRootEntityModule<E : Entity,
        Q : EntityQueries<E>, QI : Q,
        QV : Enum<out QV>,
        R : Repository<E>,
        V : EntityValidator<E, *>,
        I : EntityInitializer<E>,
        L : EntityListener<E>> : RootEntityModule<E, Q, QI, QV, R, V, I, L> {

    override final fun provideQueries(queries: QI): Q {
        // Do nothing! (test root entity modules do not need provide methods for queries)
        throw UnsupportedOperationException()
    }
}