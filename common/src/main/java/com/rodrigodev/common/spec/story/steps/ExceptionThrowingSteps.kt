package com.rodrigodev.common.spec.story.steps

import org.jbehave.core.annotations.BeforeScenario

/**
 * Created by Rodrigo Quesada on 10/12/15.
 */
interface ExceptionThrowingSteps {

    var throwable: Throwable?

    @BeforeScenario
    fun resetThrowable() {
        throwable = null
    }
}