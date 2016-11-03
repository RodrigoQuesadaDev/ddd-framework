package com.aticosoft.appointments.mobile.business.infrastructure.domain.model

import com.aticosoft.appointments.mobile.business.domain.application.common.observation.entity.EntityAsyncListener
import com.aticosoft.appointments.mobile.business.domain.model.common.entity.Entity
import com.aticosoft.appointments.mobile.business.domain.model.common.entity.EntityRepository
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.Queries

/**
 * Created by Rodrigo Quesada on 26/06/16.
 */
/*internal*/ interface EntityModule<E : Entity> : PersistableObjectModule<E, EntityAsyncListener<E>> {

    override fun provideTypeIntoSet(): Class<out Entity>
}

/*internal*/ interface RootEntityModule<E : Entity,
        out Q : Queries<E>,
        in R : EntityRepository<E>> : EntityModule<E>, RootPersistableObjectModule<E, EntityAsyncListener<E>, Q, R> {

    override fun provideRepository(repository: R): EntityRepository<E>
}