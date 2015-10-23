package com.aticosoft.appointments.mobile.business.domain.model.common

import com.querydsl.core.types.EntityPath
import com.querydsl.core.types.dsl.NumberPath

/**
 * Created by rodrigo on 22/09/15.
 */
/*internal*/ abstract class QueryEntity<E : Entity, P : EntityPath<E>>(protected val entityPath: P) : EntityPath<E> by entityPath {

    abstract val id: NumberPath<Long>
}