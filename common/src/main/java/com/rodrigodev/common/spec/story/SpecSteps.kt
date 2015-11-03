package com.rodrigodev.common.spec.story

import org.jbehave.core.steps.ParameterConverters

/**
* Created by Rodrigo Quesada on 29/10/15.
*/
abstract class SpecSteps {

    open val converters: Array<ParameterConverters.ParameterConverter> = emptyArray()
}