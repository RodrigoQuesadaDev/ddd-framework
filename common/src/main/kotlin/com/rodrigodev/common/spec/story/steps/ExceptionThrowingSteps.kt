package com.rodrigodev.common.spec.story.steps

import org.jbehave.core.annotations.BeforeScenario
import org.jbehave.core.annotations.ScenarioType

/**
 * Created by Rodrigo Quesada on 10/12/15.
 */
interface ExceptionThrowingSteps {

    var throwable: Throwable?

    @BeforeScenario(uponType = ScenarioType.ANY)
    fun resetThrowable() {
        throwable = null
    }
}