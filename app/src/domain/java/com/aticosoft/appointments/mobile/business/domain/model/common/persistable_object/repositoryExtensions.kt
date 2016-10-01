@file:Suppress("NOTHING_TO_INLINE")

package com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object

/**
 * Created by Rodrigo Quesada on 29/09/16.
 */
inline fun Repository<*, *>.isEmpty() = size() == 0L

inline fun Repository<*, *>.isNotEmpty() = !isEmpty()