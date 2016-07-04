package com.rodrigodev.common.spec.story.converter

import com.rodrigodev.common.collection.toArrayList
import org.jbehave.core.annotations.AsParameters
import org.jbehave.core.steps.ParameterConverters
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * Created by Rodrigo Quesada on 06/11/15.
 */
class ListConverter constructor(
        private val parameterConverters: ParameterConverters,
        private val valueSeparator: String = ListConverter.DEFAULT_LIST_SEPARATOR
) : ParameterConverters.ParameterConverter {
    private companion object {
        val DEFAULT_LIST_SEPARATOR = ","
    }

    override fun accept(type: Type) = type is ParameterizedType && type.rawType.isList && !type.argumentType.isAnnotationPresent(AsParameters::class.java)

    override fun convertValue(value: String, type: Type): List<*> = value.split(valueSeparator).asSequence()
            .map { it.trim() }
            .filterNot { it.isEmpty() }
            .map { parameterConverters.convert(it, type.argumentType) }
            .toArrayList()
}

private val Type.argumentType: Class<*>
    get() = (this as ParameterizedType).actualTypeArguments.first() as Class<*>

private val Class<*>.isList: Boolean
    get() = isAssignableFrom(List::class.java)

private val Type.isList: Boolean
    get() = this is ParameterizedType && rawType.let { it is Class<*> && it.isList }