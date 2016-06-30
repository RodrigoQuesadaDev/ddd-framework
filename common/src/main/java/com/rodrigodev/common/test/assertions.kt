package com.rodrigodev.common.test

import com.rodrigodev.common.reflection.isAssignableFromAny

/**
 * Created by Rodrigo Quesada on 06/12/15.
 */
inline fun catchThrowable(call: () -> Unit): Throwable? = try {
    call()
    null
}
catch(throwable: Throwable) {
    throwable
}

inline fun catchThrowable(types: Iterable<Class<out Throwable>>, call: () -> Unit): Throwable? = try {
    call()
    null
}
catch(throwable: Throwable) {
    if (throwable.javaClass.isAssignableFromAny(types)) throwable else throw throwable
}