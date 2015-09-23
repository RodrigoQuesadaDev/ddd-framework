package com.aticosoft.appointments.mobile.business.domain.model.client

import com.aticosoft.appointments.mobile.business.domain.model.common.Entity
import javax.jdo.annotations.PersistenceCapable

/**
 * Created by rodrigo on 27/09/15.
 */
@PersistenceCapable
class Client(
        id: Long,
        var name: String
) : Entity(id)