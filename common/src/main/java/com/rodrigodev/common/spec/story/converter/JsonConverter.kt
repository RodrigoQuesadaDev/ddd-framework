package com.rodrigodev.common.spec.story.converter

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.JavaType
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.paranamer.ParanamerModule
import org.jbehave.core.steps.ParameterConverters
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * Created by Rodrigo Quesada on 06/11/15.
 */
class JsonConverter : ParameterConverters.ParameterConverter {

    private val mapper = ObjectMapper()
            .configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true)
            .registerModule(ParanamerModule())

    override fun accept(type: Type): Boolean = (type.typePathList.last() as? Class<*>)?.isAnnotationPresent(JsonData::class.java) ?: false

    override fun convertValue(value: String, type: Type): Any = mapper.readValue(value, type.typePathList.toCollectionType())

    private fun List<Type?>.toCollectionType(): JavaType = with(mapper.typeFactory) {
        var javaType = constructType(last())
        (1..size - 1).forEach {
            javaType = constructCollectionType(MutableList::class.java, javaType)
        }
        javaType
    }
}

//TODO remove '2' suffix whe KT-9923 is fixed
private val Type.argumentType2: Type
    get() = (this as ParameterizedType).actualTypeArguments.first()

//TODO remove '2' suffix whe KT-9923 is fixed
private val Class<*>.isList2: Boolean
    get() = isAssignableFrom(List::class.java)

//TODO remove '2' suffix whe KT-9923 is fixed
private val Type.isList2: Boolean
    get() = this is ParameterizedType && (rawType as Class<*>).isList2

private val Type.typePathList: List<Type?>
    get() = calculateTypePathList(arrayListOf()).reversed()

private fun Type.calculateTypePathList(pathList: MutableList<Type?>): List<Type?> {
    pathList.add(when {
        isList2 -> this.apply { argumentType2.calculateTypePathList(pathList) }
        this is Class<*> -> this
        else -> null
    })
    return pathList
}

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class JsonData