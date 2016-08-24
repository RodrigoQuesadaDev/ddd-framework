package com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object

import javax.jdo.listener.InstanceLifecycleListener

/**
 * Created by Rodrigo Quesada on 10/01/16.
 */
/*internal*/ interface PersistableObjectLifecycleListener<P : PersistableObject<*>> : InstanceLifecycleListener {
    val objectType: Class<P>
}