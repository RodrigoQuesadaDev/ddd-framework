package com.rodrigodev.common.boolean

/**
 * Created by Rodrigo Quesada on 02/10/16.
 */
inline fun <R> Boolean.orNull(body: () -> R): R? = if (this) body() else null
