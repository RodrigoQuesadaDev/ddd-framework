@file:Suppress("NOTHING_TO_INLINE")

package com.aticosoft.appointments.mobile.business.domain.testing.application.common.service

import com.rodrigodev.common.reflection.isContainedUnder

/**
 * Created by Rodrigo Quesada on 06/12/15.
 */
class CommandImplementation(val valid: Boolean, val clazz: Class<*>)

internal inline fun List<CommandImplementation>.valid() = filter { it.valid }

internal inline fun List<CommandImplementation>.invalid() = filter { !it.valid }

internal inline fun List<CommandImplementation>.classes() = map { it.clazz }

internal inline fun List<CommandImplementation>.classNames() = classes().map { it.canonicalName }

internal inline fun List<CommandImplementation>.packages() = classes().map { it.`package` }

internal inline fun List<CommandImplementation>.containedUnder(packageName: String) = filter { it.clazz.`package`.isContainedUnder(packageName) }