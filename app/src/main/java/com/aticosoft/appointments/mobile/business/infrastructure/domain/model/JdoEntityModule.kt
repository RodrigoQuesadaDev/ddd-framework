package com.aticosoft.appointments.mobile.business.infrastructure.domain.model

import com.aticosoft.appointments.mobile.business.domain.application.common.observation.entity.EntityListener
import com.aticosoft.appointments.mobile.business.domain.model.common.entity.Entity
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.Queries
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.validation.PersistableObjectValidator
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.entity.JdoEntityRepository
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.persistable_object.PersistableObjectInitializer

/**
 * Created by Rodrigo Quesada on 26/06/16.
 */
/*internal*/ interface JdoEntityModule<E : Entity,
        V : PersistableObjectValidator<E, *>,
        I : PersistableObjectInitializer<E>,
        L : EntityListener<E>> : EntityModule<E, V, I, L>

/*internal*/ interface JdoRootEntityModule<E : Entity,
        out Q : Queries<E>, in QI : Q,
        out QV : Enum<out QV>,
        V : PersistableObjectValidator<E, *>,
        I : PersistableObjectInitializer<E>,
        L : EntityListener<E>> : RootEntityModule<E, Q, QI, QV, JdoEntityRepository<E>, V, I, L>