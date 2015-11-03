package com.aticosoft.appointments.mobile.business.domain.model.client

import com.aticosoft.appointments.mobile.business.domain.model.common.Entity
import org.joda.time.LocalDate
import javax.jdo.annotations.PersistenceCapable

/**
 * Created by Rodrigo Quesada on 27/09/15.
 */
@PersistenceCapable
class Client(
        context: Entity.Context,
        name: String,
        birthDate: LocalDate
) : Entity(context) {

    var name: String = name
        private set
    var birthDate: LocalDate = birthDate
        private set
}