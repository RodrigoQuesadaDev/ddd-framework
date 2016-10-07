@file:Suppress("NOTHING_TO_INLINE")

package com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.valueobject

import com.fasterxml.jackson.annotation.JsonProperty
import com.rodrigodev.common.spec.story.converter.JsonData
import org.jbehave.core.annotations.AsParameters

/**
 * Created by Rodrigo Quesada on 06/10/16.
 */
// For some reason single argument constructors do not get its arguments names detected automatically
// (using Paranamer plugin). Therefore I'm using @JsonProperty here.
@JsonData
internal data class Configuration(@JsonProperty("packageNames") val packageNames: Array<String>)

@AsParameters
internal class ClassExample {
    lateinit var className: String
}

internal inline fun List<ClassExample>.classNames() = map { it.className }