package com.aticosoft.appointments.mobile.business.infrastructure.domain.model

import com.aticosoft.appointments.mobile.business.domain.application.common.observation.persistable_object.PersistableObjectListener
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.PersistableObject
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.Queries
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.Repository
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.validation.PersistableObjectValidator
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.persistable_object.PersistableObjectInitializer

/**
 * Created by Rodrigo Quesada on 23/08/16.
 */
/*internal*/ interface PersistableObjectModule<P : PersistableObject<*>,
        V : PersistableObjectValidator<P, *>,
        I : PersistableObjectInitializer<P>,
        L : PersistableObjectListener<P, *>> {

    //TODO change *Module stuff to use PersistableObjectModuleProvider (that way *Module implementations get way easier and this interface can define provideValidatorsIntoSet method and similiar ones)

    fun provideType(): Class<P>

    fun provideTypeIntoSet(): Class<out PersistableObject<*>>

    fun provideInitializerIntoSet(initializer: I): PersistableObjectInitializer<*>

    fun provideListenerIntoSet(listener: L): PersistableObjectListener<*, *>
}

/*internal*/ interface RootPersistableObjectModule<P : PersistableObject<*>,
        out Q : Queries<P>, in QI : Q,
        out QV : Enum<out QV>,
        in R : Repository<P, *>,
        V : PersistableObjectValidator<P, *>,
        I : PersistableObjectInitializer<P>,
        L : PersistableObjectListener<P, *>> : PersistableObjectModule<P, V, I, L> {

    fun provideQueries(queries: QI): Q

    @QueryViews
    fun provideQueryViewsIntoSet(): Class<out Enum<*>>

    fun provideRepository(repository: R): Repository<P, *>
}