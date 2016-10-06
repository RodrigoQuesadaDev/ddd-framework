@file:Suppress("NOTHING_TO_INLINE")

package com.rodrigodev.common.reflection

import org.reflections.Reflections
import org.reflections.scanners.ResourcesScanner
import org.reflections.scanners.SubTypesScanner
import org.reflections.scanners.TypeAnnotationsScanner
import org.reflections.util.ClasspathHelper
import org.reflections.util.ClasspathHelper.contextClassLoader
import org.reflections.util.ClasspathHelper.staticClassLoader
import org.reflections.util.ConfigurationBuilder
import org.reflections.util.FilterBuilder

/**
 * Created by Rodrigo Quesada on 29/08/16.
 */
inline fun createReflections(vararg prefixes: String): Reflections {
    val classLoaders: Array<ClassLoader> = arrayOf(contextClassLoader(), staticClassLoader())

    return Reflections(ConfigurationBuilder()
            .setScanners(SubTypesScanner(), ResourcesScanner(), TypeAnnotationsScanner())
            .setUrls(ClasspathHelper.forClassLoader(*classLoaders))
            .filterInputsBy(FilterBuilder().includePackage(*prefixes)))
}

inline fun createReflections(prefixes: Collection<String>) = createReflections(*prefixes.toTypedArray())

fun createReflectionsForPackages(vararg packages: Package) = createReflections(packages.toPrefixes())

fun createReflectionsForPackages(packages: Collection<Package>) = createReflections(packages.toPrefixes())

//region Utils
private inline fun Collection<Package>.toPrefixes() = map { it.name }

private inline fun Array<out Package>.toPrefixes() = map { it.name }
//endregion