package com.aticosoft.appointments.mobile.business.domain.testing.infrastructure.domain.model

import com.aticosoft.appointments.mobile.business.domain.application.common.observation.entity.EntityListener
import com.aticosoft.appointments.mobile.business.domain.model.common.entity.Entity
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.Queries
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.validation.PersistableObjectValidator
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.JdoRootEntityModule
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.persistable_object.PersistableObjectInitializer

/**
 * Created by Rodrigo Quesada on 27/06/16.
 */
internal abstract class TestRootEntityModule<E : Entity,
        out QV : Enum<out QV>,
        V : PersistableObjectValidator<E, *>,
        I : PersistableObjectInitializer<E>,
        L : EntityListener<E>> : JdoRootEntityModule<E, Queries<E>, Queries<E>, QV, V, I, L> {

    override fun provideQueries(queries: Queries<E>): Queries<E> {
        // Do nothing! (test root entity modules do not need provide methods for queries)
        throw UnsupportedOperationException()
    }
}