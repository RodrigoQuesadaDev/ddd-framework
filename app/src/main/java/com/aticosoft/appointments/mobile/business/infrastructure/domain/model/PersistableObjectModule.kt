package com.aticosoft.appointments.mobile.business.infrastructure.domain.model

import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.listener.async.PersistableObjectAsyncListener
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.PersistableObject
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.Queries
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.Repository
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.validation.PersistableObjectValidator
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.persistable_object.PersistableObjectInitializer

/**
 * Created by Rodrigo Quesada on 23/08/16.
 */
/*internal*/ interface PersistableObjectModule<P : PersistableObject<*>, in L : PersistableObjectAsyncListener<P, *>> {

    fun provideType(): Class<P>

    fun provideTypeIntoSet(): Class<out PersistableObject<*>>

    fun provideInitializerIntoSet(initializer: PersistableObjectInitializer<P>): PersistableObjectInitializer<*>

    fun provideListenerIntoSet(listener: L): PersistableObjectAsyncListener<*, *>

    fun provideValidatorsIntoSet(): Set<PersistableObjectValidator<*, *>> = emptySet()

    @ValueObjects
    fun provideValueObjectsIntoSet(): Set<Class<*>> = emptySet()
}

/*internal*/ interface RootPersistableObjectModule<P : PersistableObject<*>,
        in L : PersistableObjectAsyncListener<P, *>,
        out Q : Queries<P>,
        in R : Repository<P, *>>
: PersistableObjectModule<P, L> {

    fun provideQueries(): Q = throw UnsupportedOperationException()

    @QueryViews
    fun provideQueryViewsIntoSet(): Class<out Enum<*>>

    fun provideRepository(repository: R): Repository<P, *>
}