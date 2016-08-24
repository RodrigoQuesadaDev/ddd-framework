package com.aticosoft.appointments.mobile.business.domain.model.common.entity

import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.Repository

/**
 * Created by Rodrigo Quesada on 24/09/15.
 */
/*internal*/ interface EntityRepository<E : Entity> : Repository<E, String>
