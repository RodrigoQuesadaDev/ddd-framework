@file:Suppress("NOTHING_TO_INLINE")

package com.aticosoft.appointments.mobile.business.domain.testing.application.common.service.command

import com.aticosoft.appointments.mobile.business.domain.application.common.service.ApplicationServices.Command
import com.aticosoft.appointments.mobile.business.domain.application.common.service.CommandPersistableObjectDelegate
import com.aticosoft.appointments.mobile.business.domain.model.common.entity.Entity
import com.aticosoft.appointments.mobile.business.domain.testing.common.`class`.verification.ClassVerifier
import com.aticosoft.appointments.mobile.business.domain.testing.common.`class`.verification.GenericClassVerifier
import com.aticosoft.appointments.mobile.business.domain.testing.common.`class`.verification.ValidClassImplementationResult
import com.rodrigodev.common.reflection.isCollectionOf
import com.rodrigodev.common.reflection.isKotlinClass
import com.rodrigodev.common.reflection.isSubOfOrSameAs
import com.rodrigodev.common.reflection.usesDelegate
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
    if (type.isEntity() || type.isCollectionOfEntities()) usesDelegate(CommandPersistableObjectDelegate::class) else true
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