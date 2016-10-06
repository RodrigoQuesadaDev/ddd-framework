@file:Suppress("NOTHING_TO_INLINE")

package com.aticosoft.appointments.mobile.business.domain.testing.model.common.value_object

import com.aticosoft.appointments.mobile.business.domain.model.common.value_object.ValueObject
import com.aticosoft.appointments.mobile.business.domain.testing.common.`class`.validation.ValidClassImplementationResult
import com.rodrigodev.common.properties.delegates.ReadOnlyDelegate
import com.rodrigodev.common.properties.delegates.WriteOnceDelegate
import com.rodrigodev.common.reflection.allMemberProperties
import com.rodrigodev.common.reflection.createReflections
import com.rodrigodev.common.reflection.isKotlinClass
import com.rodrigodev.common.reflection.usesDelegate
import org.reflections.Reflections
import kotlin.properties.Delegates.notNull
import kotlin.reflect.KProperty1
import kotlin.reflect.jvm.javaType

/**
 * Created by Rodrigo Quesada on 06/12/15.
 */
internal class ValueObjectImplementationVerifier(packagePaths: Array<String>) {

    private val reflections: Reflections = createReflections(*packagePaths)

    var results: List<ValidClassImplementationResult> by notNull()
        private set

    fun run() {
        results = reflections.getTypesAnnotatedWith(ValueObject::class.java)
                .map { ValidClassImplementationResult(it.isValid(), it) }
    }
}

//region Validation
private inline fun Class<*>.isValid() = isKotlinClass() && kotlin.allMemberProperties.asSequence().all { it.isValid() }

private inline fun KProperty1<*, *>.isValid(): Boolean = returnType.javaType.let { type ->
    usesDelegate(WriteOnceDelegate::class) || usesDelegate(ReadOnlyDelegate::class)
}
//endregion