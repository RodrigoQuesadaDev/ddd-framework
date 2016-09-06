@file:Suppress("NOTHING_TO_INLINE")

package com.aticosoft.appointments.mobile.business.domain.application.common.observation.persistable_object

import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.PersistableObject
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.Repository
import org.joda.time.Duration
import org.joda.time.Duration.millis
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 15/10/15.
 */
@Singleton
/*internal*/ abstract class PersistableObjectExternalObserver<P : PersistableObject<I>, I, R : Repository<P, I>> protected constructor() : PersistableObjectObserverBase<P, I, R>(DATA_REFRESH_RATE_TIME) {
    companion object {
        val DATA_REFRESH_RATE_TIME: Duration = millis(500)
    }
}