package com.aticosoft.appointments.mobile.business.domain.model.client

import com.aticosoft.appointments.mobile.business.domain.model.common.Entity
import org.joda.time.LocalDate
import javax.jdo.annotations.PersistenceCapable

/**
 * Created by rodrigo on 27/09/15.
 */
@PersistenceCapable
class Client(
        id: Long,
        name: String,
        birthDate: LocalDate
) : Entity(id) {

    var name: String = name
        private set
    var birthDate: LocalDate = birthDate
        private set
}