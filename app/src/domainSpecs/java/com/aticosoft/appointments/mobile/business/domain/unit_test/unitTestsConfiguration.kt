package com.aticosoft.appointments.mobile.business.domain.unit_test

import com.aticosoft.appointments.mobile.business.domain.specs.DomainStory
import com.aticosoft.appointments.mobile.business.domain.testing.TestApplication
import com.aticosoft.appointments.mobile.business.domain.testing.TestApplicationComponent
import com.aticosoft.appointments.mobile.business.domain.testing.TestApplicationModule
import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.display.DataRefreshRateIsThrottled
import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_listener.ObservingEntityChanges
import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.ObservingTotalCount
import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.filtering.FilteringObservationOfCountQuery
import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.filtering.FilteringObservationOfListQuery
import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.filtering.FilteringObservationOfSingleEntityById
import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.filtering.FilteringObservationOfUniqueQuery
import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.query_view.ConstrainingViewOfEntityById
import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.query_view.ConstrainingViewOfListQuery
import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.query_view.ConstrainingViewOfUniqueQuery
import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.StaleEntityChecking
import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.command.CommandImplementationVerification
import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.command.CommandsCannotBeReused
import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.passed_entities.DetachedEntityChecking
import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.passed_entities.DirtyEntityChecking
import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.service.passed_entities.PassedEntityInstancesNotModified
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.entity.EntityDependenciesAreInjected
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.entity.validation.EntitiesAreValidated
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.creation.EventSubscriptionCreation
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.simpleaction.SimpleAction
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.value_object.ValueObjectImplementationVerification
import dagger.Component
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 26/06/16.
 */
@Singleton
@Component(modules = arrayOf(TestApplicationModule::class))
internal interface UnitTestApplicationComponent : TestApplicationComponent {

    fun inject(test: ObservingEntityChanges)
    fun inject(test: ObservingTotalCount)
    fun inject(test: FilteringObservationOfSingleEntityById)
    fun inject(test: FilteringObservationOfUniqueQuery)
    fun inject(test: FilteringObservationOfListQuery)
    fun inject(test: FilteringObservationOfCountQuery)
    fun inject(test: ConstrainingViewOfEntityById)
    fun inject(test: ConstrainingViewOfUniqueQuery)
    fun inject(test: ConstrainingViewOfListQuery)
    fun inject(test: DataRefreshRateIsThrottled)
    fun inject(test: EntitiesAreValidated)
    fun inject(test: EntityDependenciesAreInjected)
    fun inject(test: DetachedEntityChecking)
    fun inject(test: DirtyEntityChecking)
    fun inject(test: StaleEntityChecking)
    fun inject(test: PassedEntityInstancesNotModified)
    fun inject(test: CommandsCannotBeReused)
    fun inject(test: CommandImplementationVerification)
    fun inject(test: ValueObjectImplementationVerification)
    fun inject(test: SimpleAction)
    fun inject(test: EventSubscriptionCreation)

    @Component.Builder
    interface Builder : TestApplicationComponent.Builder<UnitTestApplicationComponent, Builder>
}

internal abstract class UnitTestApplication<S : DomainStory>(
        injectTest: UnitTestApplicationComponent.(S) -> Unit
) : TestApplication<S, UnitTestApplicationComponent, UnitTestApplicationComponent.Builder>({ DaggerUnitTestApplicationComponent.builder() }, injectTest)