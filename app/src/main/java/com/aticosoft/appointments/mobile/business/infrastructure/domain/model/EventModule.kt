package com.aticosoft.appointments.mobile.business.infrastructure.domain.model

import com.aticosoft.appointments.mobile.business.domain.application.common.observation.event.EventListener
import com.aticosoft.appointments.mobile.business.domain.model.common.event.Event
import com.aticosoft.appointments.mobile.business.domain.model.common.event.EventRepository
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.Queries
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.validation.PersistableObjectValidator
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.persistable_object.PersistableObjectInitializer

/**
 * Created by Rodrigo Quesada on 23/08/16.
 */
/*internal*/ interface EventModule<E : Event,
        Q : Queries<E>, QI : Q,
        QV : Enum<out QV>,
        R : EventRepository<E>,
        V : PersistableObjectValidator<E, *>,
        I : PersistableObjectInitializer<E>,
        L : EventListener<E>> : RootPersistableObjectModule<E, Q, QI, QV, R, V, I, L>