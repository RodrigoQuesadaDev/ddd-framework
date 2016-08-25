package com.aticosoft.appointments.mobile.business.infrastructure.domain.model

import com.aticosoft.appointments.mobile.business.domain.application.common.observation.entity.EntityListener
import com.aticosoft.appointments.mobile.business.domain.model.common.entity.Entity
import com.aticosoft.appointments.mobile.business.domain.model.common.entity.EntityRepository
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.Queries
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.validation.PersistableObjectValidator
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.persistable_object.PersistableObjectInitializer

/**
 * Created by Rodrigo Quesada on 26/06/16.
 */
/*internal*/ interface EntityModule<E : Entity,
        V : PersistableObjectValidator<E, *>,
        I : PersistableObjectInitializer<E>,
        L : EntityListener<E>> : PersistableObjectModule<E, V, I, L>

/*internal*/ interface RootEntityModule<E : Entity,
        out Q : Queries<E>, in QI : Q,
        out QV : Enum<out QV>,
        in R : EntityRepository<E>,
        V : PersistableObjectValidator<E, *>,
        I : PersistableObjectInitializer<E>,
        L : EntityListener<E>> : RootPersistableObjectModule<E, Q, QI, QV, R, V, I, L> {

    override fun provideRepository(repository: R): EntityRepository<E>
}