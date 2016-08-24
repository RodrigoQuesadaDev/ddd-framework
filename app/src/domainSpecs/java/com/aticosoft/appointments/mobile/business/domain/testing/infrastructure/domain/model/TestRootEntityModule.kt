package com.aticosoft.appointments.mobile.business.domain.testing.infrastructure.domain.model

import com.aticosoft.appointments.mobile.business.domain.application.common.observation.entity.EntityListener
import com.aticosoft.appointments.mobile.business.domain.model.common.entity.Entity
import com.aticosoft.appointments.mobile.business.domain.model.common.entity.EntityRepository
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.Queries
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.validation.PersistableObjectValidator
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.RootEntityModule
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.persistable_object.PersistableObjectInitializer

/**
 * Created by Rodrigo Quesada on 27/06/16.
 */
internal abstract class TestRootEntityModule<E : Entity,
        Q : Queries<E>, QI : Q,
        QV : Enum<out QV>,
        R : EntityRepository<E>,
        V : PersistableObjectValidator<E, *>,
        I : PersistableObjectInitializer<E>,
        L : EntityListener<E>> : RootEntityModule<E, Q, QI, QV, R, V, I, L> {

    override final fun provideQueries(queries: QI): Q {
        // Do nothing! (test root entity modules do not need provide methods for queries)
        throw UnsupportedOperationException()
    }
}