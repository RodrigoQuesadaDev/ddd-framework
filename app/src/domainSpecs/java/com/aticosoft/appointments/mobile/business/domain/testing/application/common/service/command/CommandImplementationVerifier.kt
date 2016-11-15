@file:Suppress("NOTHING_TO_INLINE")

package com.aticosoft.appointments.mobile.business.domain.testing.application.common.service.command

import com.aticosoft.appointments.mobile.business.domain.application.common.service.ApplicationServices.Command
import com.aticosoft.appointments.mobile.business.domain.application.common.service.CommandDelegate
import com.aticosoft.appointments.mobile.business.domain.application.common.service.CommandPersistableObjectDelegate
import com.aticosoft.appointments.mobile.business.domain.model.common.entity.Entity
import com.aticosoft.appointments.mobile.business.domain.testing.common.`class`.verification.ClassVerifier
import com.aticosoft.appointments.mobile.business.domain.testing.common.`class`.verification.GenericClassVerifier
import com.aticosoft.appointments.mobile.business.domain.testing.common.`class`.verification.ValidClassImplementationResult
import com.rodrigodev.common.reflection.*
import org.reflections.Reflections
import java.lang.reflect.Type
import kotlin.reflect.KClass
import kotlin.reflect.KProperty1
import kotlin.reflect.jvm.javaType
import kotlin.reflect.memberProperties

/**
 * Created by Rodrigo Quesada on 06/12/15.
 */
internal class CommandImplementationVerifier(packagePaths: Array<String>) : ClassVerifier<ValidClassImplementationResult> {

    override val _genericVerifier = object : GenericClassVerifier<ValidClassImplementationResult>(packagePaths) {

        override fun Reflections.retrieveClasses() = getSubTypesOf(Command::class.java)

        override fun Class<*>.verify() = ValidClassImplementationResult(isValid(), this)
    }
}

//region Validation
private inline fun Class<*>.isValid() = isKotlinClass() && kotlin.nonCommandMemberProperties().asSequence().all { it.isValid() }

private inline fun KProperty1<*, *>.isValid(): Boolean = returnType.javaType.let { type ->
    when {
        type.isEntity() || type.isCollectionOfEntities() || type.isArrayOfEntities() -> usesDelegate(CommandPersistableObjectDelegate::class)
        type.isCommand() || type.isCollectionOfCommands() || type.isArrayOfCommands() -> usesDelegate(CommandDelegate::class)
        else -> true
    }
}
//endregion

//region Class Utils
private inline fun KClass<*>.nonCommandMemberProperties() = memberPropertiesNotIn(Command::class)

private inline fun KClass<*>.memberPropertiesNotIn(anotherClass: KClass<*>): Collection<KProperty1<*, *>> {
    val anotherMemberPropertyNames = anotherClass.memberProperties.map { it.name }
    return memberProperties.filter { it.name !in anotherMemberPropertyNames }
}
//endregion

//region Entity Class Utils
private inline fun Type.isEntity() = isSubOfOrSameAs(Entity::class.java)

private inline fun Type.isArrayOfEntities() = isArrayOf(Entity::class.java)

private inline fun Type.isCollectionOfEntities() = isCollectionOf(Entity::class.java)
//endregion

//region Command Class Utils
private inline fun Type.isCommand() = isSubOfOrSameAs(Command::class.java)

private inline fun Type.isArrayOfCommands() = isArrayOf(Command::class.java)

private inline fun Type.isCollectionOfCommands() = isCollectionOf(Command::class.java)
//endregion