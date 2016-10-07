package com.aticosoft.appointments.mobile.business.domain.testing.common.`class`.verification

import com.rodrigodev.common.reflection.createReflections
import org.reflections.Reflections

/**
 * Created by Rodrigo Quesada on 06/10/16.
 */
internal interface ClassVerifier<R : ClassVerificationResult> {

    val _genericVerifier: GenericClassVerifier<R>

    val results: List<R>
        get() = _genericVerifier.results

    fun run() {
        _genericVerifier.run()
    }
}

internal abstract class GenericClassVerifier<R : ClassVerificationResult>(packagePaths: Array<String>) {

    private val reflections: Reflections = createReflections(*packagePaths)

    var results: List<R> = emptyList()
        private set

    fun run() {
        results = reflections.retrieveClasses().map { it.verify() }
    }

    abstract fun Reflections.retrieveClasses(): Collection<Class<*>>

    abstract fun Class<*>.verify(): R
}