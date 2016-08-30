@file:Suppress("NOTHING_TO_INLINE")

package com.aticosoft.appointments.mobile.business.domain.testing.application.common.service

import com.aticosoft.appointments.mobile.business.domain.application.common.service.ApplicationServices.Command
import com.aticosoft.appointments.mobile.business.domain.application.common.service.CommandPersistableObjectDelegate
import com.aticosoft.appointments.mobile.business.domain.model.common.entity.Entity
import com.rodrigodev.common.reflection.createReflections
import com.rodrigodev.common.reflection.isCollectionOf
import com.rodrigodev.common.reflection.isKotlinClass
import com.rodrigodev.common.reflection.isSubOfOrSameAs
import org.reflections.Reflections
import java.lang.reflect.Field
import java.lang.reflect.Type
import kotlin.properties.Delegates.notNull
import kotlin.reflect.KClass
import kotlin.reflect.KProperty
import kotlin.reflect.KProperty1
import kotlin.reflect.jvm.javaMethod
import kotlin.reflect.jvm.javaType
import kotlin.reflect.memberProperties

/**
 * Created by Rodrigo Quesada on 06/12/15.
 */
internal class CommandImplementationVerifier(packagePaths: Array<String>) {

    private val reflections: Reflections = createReflections(*packagePaths)

    var results: List<CommandImplementation> by notNull()
        private set

    fun run() {
        results = reflections.getSubTypesOf(Command::class.java)
                .map { CommandImplementation(it.isValid(), it) }
    }
}

//region Validation
private fun Class<*>.isValid() = !isKotlinClass() || kotlin.nonCommandMemberProperties().asSequence().all { it.isValid() }

private inline fun KProperty1<*, *>.isValid(): Boolean = returnType.javaType.let { type ->
    if (type.isEntity() || type.isCollectionOfEntities()) usesCommandEntityDelegate() else true
}
//endregion

//region Classes
private inline fun KClass<*>.nonCommandMemberProperties() = Command::class.let { memberPropertiesNotIn(it) }

private inline fun KClass<*>.memberPropertiesNotIn(anotherClass: KClass<*>): Collection<KProperty1<*, *>> {
    val anotherMemberPropertyNames = anotherClass.memberProperties.map { it.name }
    return memberProperties.filter { it.name !in anotherMemberPropertyNames }
}

private inline fun Type.isEntity() = isSubOfOrSameAs(Entity::class.java)

private inline fun Type.isCollectionOfEntities() = isCollectionOf(Entity::class.java)
//endregion

//region Properties
private inline fun <T, R> KProperty1<T, R>.usesCommandEntityDelegate(): Boolean = delegate()?.isCommandEntityDelegate() ?: false

private inline fun <T> KProperty<T>.delegate(): Field? = try {
    getter.javaMethod?.declaringClass?.getDeclaredField("$name\$delegate")
}
catch(e: NoSuchFieldException) {
    null
}

private inline fun Field.isCommandEntityDelegate() = this.type.isSubOfOrSameAs(CommandPersistableObjectDelegate::class.java)
//endregion