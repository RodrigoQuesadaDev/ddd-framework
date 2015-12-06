package com.rodrigodev.common.test

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