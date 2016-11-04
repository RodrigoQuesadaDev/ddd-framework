package com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.persistable_object.transactionalaction

import com.aticosoft.appointments.mobile.business.domain.specs.DomainStory
import com.aticosoft.appointments.mobile.business.domain.unit_test.UnitTestApplication
import com.aticosoft.appointments.mobile.business.domain.unit_test.UnitTestApplicationComponent
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.persistable_object.transactionalaction.TransactionalActionPriority.UnitTestApplicationImpl
import com.rodrigodev.common.spec.story.steps.SpecSteps
import org.robolectric.annotation.Config
import javax.inject.Inject

/**
 * Created by Rodrigo Quesada on 03/11/16.
 */
@Config(application = UnitTestApplicationImpl::class)
internal class TransactionalActionPriority : DomainStory() {

    class UnitTestApplicationImpl : UnitTestApplication<TransactionalActionPriority>(UnitTestApplicationComponent::inject)

    @Inject protected lateinit var localSteps: LocalSteps

    init {
        steps { listOf(localSteps) }
    }

    class LocalSteps @Inject constructor(
    ) : SpecSteps() {
    }
}