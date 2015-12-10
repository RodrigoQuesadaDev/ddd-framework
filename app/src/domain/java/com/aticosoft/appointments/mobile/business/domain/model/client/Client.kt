package com.aticosoft.appointments.mobile.business.domain.model.client

import com.aticosoft.appointments.mobile.business.domain.model.common.Entity
import com.google.auto.factory.Provided
import org.joda.time.LocalDate
import javax.jdo.annotations.PersistenceCapable

/**
 * Created by Rodrigo Quesada on 27/09/15.
 */
@PersistenceCapable
//@AutoFactory
class Client protected constructor(
        @Provided context: Entity.Context,
        name: String,
        birthDate: LocalDate
) : Entity(context) {

    var name = name
        private set
    var birthDate = birthDate
        private set
}