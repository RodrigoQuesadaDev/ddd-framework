package com.aticosoft.appointments.mobile.business.infrastructure.domain.model

import com.aticosoft.appointments.mobile.business.domain.application.common.observation.EntityListener
import com.aticosoft.appointments.mobile.business.domain.model.common.Entity
import com.aticosoft.appointments.mobile.business.domain.model.common.EntityQueries
import com.aticosoft.appointments.mobile.business.domain.model.common.Repository
import com.aticosoft.appointments.mobile.business.domain.model.common.validation.EntityValidator
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.entity.EntityInitializer

/**
 * Created by Rodrigo Quesada on 26/06/16.
 */
/*internal*/ interface EntityModule<E : Entity,
        V : EntityValidator<E, *>,
        I : EntityInitializer<E>,
        L : EntityListener<E>> {

    fun provideEntityType(): Class<E>

    fun provideEntityTypeIntoSet(): Class<out Entity>

    fun provideEntityInitializerIntoSet(initializer: I): EntityInitializer<*>

    fun provideEntityListenerIntoSet(listener: L): EntityListener<*>
}

/*internal*/ interface RootEntityModule<E : Entity,
        Q : EntityQueries<E>, QI : Q,
        QV : Enum<out QV>,
        R : Repository<E>,
        V : EntityValidator<E, *>,
        I : EntityInitializer<E>,
        L : EntityListener<E>> : EntityModule<E, V, I, L> {

    fun provideQueries(queries: QI): Q

    @QueryViews
    fun provideQueryViewsIntoSet(): Class<out Enum<*>>

    fun provideRepository(repository: R): Repository<E>
}