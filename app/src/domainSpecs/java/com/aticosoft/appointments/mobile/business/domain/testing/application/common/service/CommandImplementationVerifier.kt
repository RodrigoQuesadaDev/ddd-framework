@file:Suppress("NOTHING_TO_INLINE")

package com.aticosoft.appointments.mobile.business.domain.testing.application.common.service

import com.aticosoft.appointments.mobile.business.domain.application.common.service.ApplicationServices.Command
import com.aticosoft.appointments.mobile.business.domain.application.common.service.CommandPersistableObjectDelegate
import com.aticosoft.appointments.mobile.business.domain.model.common.entity.Entity
import org.reflections.Reflections
import org.reflections.scanners.ResourcesScanner
import org.reflections.scanners.SubTypesScanner
import org.reflections.util.ClasspathHelper
import org.reflections.util.ClasspathHelper.contextClassLoader
import org.reflections.util.ClasspathHelper.staticClassLoader
import org.reflections.util.ConfigurationBuilder
import org.reflections.util.FilterBuilder
import java.lang.reflect.Field
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.lang.reflect.TypeVariable
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

    private val reflections: Reflections

    init {
        val classLoaders: Array<ClassLoader> = arrayOf(contextClassLoader(), staticClassLoader())

        reflections = Reflections(ConfigurationBuilder()
                .setScanners(SubTypesScanner(), ResourcesScanner())
                .setUrls(ClasspathHelper.forClassLoader(*classLoaders))
                .filterInputsBy(FilterBuilder().includePackage(*packagePaths))
        )
    }

    var results: List<CommandImplementation> by notNull()
        private set

    fun run() {
        results = reflections.getSubTypesOf(Command::class.java)
                .map { CommandImplementation(it.isValid(), it) }
    }
}

/***************************************************************************************************
 * Validation
 **************************************************************************************************/

private fun Class<*>.isValid() = !isKotlinClass() || kotlin.nonCommandMemberProperties().asSequence().all { it.isValid() }

private inline fun KProperty1<*, *>.isValid(): Boolean = returnType.javaType.let { type ->
    if (type.isEntity() || type.isCollectionOfEntities()) usesCommandEntityDelegate() else true
}

/***************************************************************************************************
 * Classes
 **************************************************************************************************/

private val KOTLIN_METADATA_ANNOTATION_NAME = "kotlin.Metadata"

private inline fun Class<*>.isKotlinClass() = declaredAnnotations.any { it.annotationClass.java.name == KOTLIN_METADATA_ANNOTATION_NAME }

private inline fun KClass<*>.nonCommandMemberProperties() = Command::class.let { memberPropertiesNotIn(it) }

private inline fun KClass<*>.memberPropertiesNotIn(anotherClass: KClass<*>): Collection<KProperty1<*, *>> {
    val anotherMemberPropertyNames = anotherClass.memberProperties.map { it.name }
    return memberProperties.filter { it.name !in anotherMemberPropertyNames }
}

private inline fun KClass<*>.isAssignableFrom(anotherClass: Class<*>) = this.java.isAssignableFrom(anotherClass)

private inline fun Array<out KClass<*>>.anyIsAssignableFrom(anotherClass: Class<*>) = any { it.isAssignableFrom(anotherClass) }

private inline fun Array<out KClass<*>>.anyIsAssignableFromAny(otherClasses: List<Class<*>>) = otherClasses.any { this.anyIsAssignableFrom(it) }

private inline fun Type.isEntity() = classes().any { Entity::class.isAssignableFrom(it) }

private inline fun Type.isCollectionOfEntities() = this is ParameterizedType && isCollection() && isParameterizedWithEntity()

private val collectionClasses = arrayOf(List::class, Set::class, Map::class)

private inline fun ParameterizedType.isCollection() = collectionClasses.anyIsAssignableFromAny(classes())

private inline fun ParameterizedType.isParameterizedWithEntity() = actualTypeArguments.any { it.isEntity() }

private fun Type.classes(): List<Class<*>> = when (this) {
    is Class<*> -> listOf(this)
    is ParameterizedType -> rawType.classes()
    is TypeVariable<*> -> this.bounds.flatMap { it.classes() }
    else -> emptyList()
}

/***************************************************************************************************
 * Properties
 **************************************************************************************************/

private inline fun <T, R> KProperty1<T, R>.usesCommandEntityDelegate(): Boolean = delegate()?.isCommandEntityDelegate() ?: false

private inline fun <T> KProperty<T>.delegate(): Field? = try {
    getter.javaMethod?.declaringClass?.getDeclaredField("$name\$delegate")
}
catch(e: NoSuchFieldException) {
    null
}

private inline fun Field.isCommandEntityDelegate() = CommandPersistableObjectDelegate::class.isAssignableFrom(this.type)