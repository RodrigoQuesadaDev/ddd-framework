package com.aticosoft.appointments.mobile.business.domain.model.configuration

import com.aticosoft.appointments.mobile.business.domain.model.common.Entity
import com.google.auto.factory.Provided
import javax.jdo.annotations.PersistenceCapable

/**
 * Created by Rodrigo Quesada on 17/12/15.
 */
@PersistenceCapable
//@AutoFactory
/*internal*/ class Configuration(@Provided context: Entity.Context) : Entity(context) {

    var maxConcurrentAppointments: Int = 0

    init {
        reset()
    }

    private fun reset() {
        //TODO put some code here
    }
}

//TODO test default values when first created (that is when the app is first used)

//TODO add validation for values (such as value range for max concurrent appointments)

//TODO pin configuration to L2 cache