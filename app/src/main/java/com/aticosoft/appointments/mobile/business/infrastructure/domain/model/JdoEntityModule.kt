package com.aticosoft.appointments.mobile.business.infrastructure.domain.model

import com.aticosoft.appointments.mobile.business.domain.model.common.entity.Entity
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.Queries
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.entity.JdoEntityRepository

/**
 * Created by Rodrigo Quesada on 26/06/16.
 */
/*internal*/ interface JdoEntityModule<E : Entity> : EntityModule<E>

/*internal*/ interface JdoRootEntityModule<E : Entity, out Q : Queries<E>>
: RootEntityModule<E, Q, JdoEntityRepository<E>>