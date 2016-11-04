package com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.persistable_object.transactionalaction

import com.aticosoft.appointments.mobile.business.domain.application.common.observation.persistable_object.PersistableObjectChangeEvent
import com.aticosoft.appointments.mobile.business.domain.specs.DomainStory
import com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.AbstractTestData
import com.aticosoft.appointments.mobile.business.domain.unit_test.UnitTestApplication
import com.aticosoft.appointments.mobile.business.domain.unit_test.UnitTestApplicationComponent
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.persistable_object.transactionalaction.TransactionalActionObservationBehavior.UnitTestApplicationImpl
import com.rodrigodev.common.spec.story.steps.SpecSteps
import org.jbehave.core.annotations.AsParameters
import org.robolectric.annotation.Config
import javax.inject.Inject

/**
 * Created by Rodrigo Quesada on 03/11/16.
 */
@Config(application = UnitTestApplicationImpl::class)
internal class TransactionalActionObservationBehavior : DomainStory() {

    class UnitTestApplicationImpl : UnitTestApplication<TransactionalActionObservationBehavior>(UnitTestApplicationComponent::inject)

    @Inject protected lateinit var localSteps: LocalSteps

    init {
        steps { listOf(localSteps) }
    }

    class LocalSteps @Inject constructor(
    ) : SpecSteps() {
    }
}

private fun PersistableObjectChangeEvent<AbstractTestData>.toExample() = ChangeEventExample(type, previous = previousValue?.value, current = currentValue?.value)

@AsParameters
internal data class ChangeEventExample(
        var type: PersistableObjectChangeEvent.EventType?,
        var previous: Int?,
        var current: Int?
) {
    constructor() : this(null, null, null)
}

internal enum class EntityType {PARENT, CHILD }