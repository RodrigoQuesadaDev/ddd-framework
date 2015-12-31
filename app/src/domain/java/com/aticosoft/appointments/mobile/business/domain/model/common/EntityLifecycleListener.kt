package com.aticosoft.appointments.mobile.business.domain.model.common

import javax.jdo.listener.InstanceLifecycleListener

/**
 * Created by Rodrigo Quesada on 10/01/16.
 */
/*internal*/ interface EntityLifecycleListener<E : Entity> : InstanceLifecycleListener {
    val entityType: Class<E>
}