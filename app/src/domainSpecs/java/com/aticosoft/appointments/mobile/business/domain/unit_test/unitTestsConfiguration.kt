package com.aticosoft.appointments.mobile.business.domain.unit_test

import com.aticosoft.appointments.mobile.business.domain.specs.DomainStory
import com.aticosoft.appointments.mobile.business.domain.testing.TestApplication
import com.aticosoft.appointments.mobile.business.domain.testing.TestApplicationComponent
import com.aticosoft.appointments.mobile.business.domain.testing.TestApplicationModule
import com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.display.DataRefreshRateIsThrottled
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
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.action.executionorder.EventActionDoesNotGetExecuted
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.action.executionorder.EventActionExecutionWhenIsNotKept
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.event.action.subscription.EventSubscriptionCreation
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.persistable_object.listener.async.ObservingObjectChangesAsync
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.persistable_object.transactionalaction.observationbehavior.TransactionalActionObservationBehavior
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.persistable_object.transactionalaction.TransactionalActionPriority
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.persistable_object.transactionalaction.subscription.TransactionalActionSubscriptionCreation
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.persistable_object.transactionalaction.TransactionalActionUpdatingBehavior
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.valueobject.behavior.ValueObjectBehavior
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.valueobject.implementation.ValueObjectImplementationVerification
import com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.valueobject.registration.ValueObjectRegistrationVerification
import dagger.Component
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 26/06/16.
 */
@Singleton
@Component(modules = arrayOf(TestApplicationModule::class))
internal interface UnitTestApplicationComponent : TestApplicationComponent {

    fun inject(test: ObservingObjectChangesAsync)
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
    fun inject(test: EventSubscriptionCreation)
    fun inject(test: EventActionDoesNotGetExecuted)
    fun inject(test: EventActionExecutionWhenIsNotKept)
    fun inject(test: CommandsCannotBeReused)
    fun inject(test: CommandImplementationVerification)
    fun inject(test: ValueObjectImplementationVerification)
    fun inject(test: ValueObjectRegistrationVerification)
    fun inject(test: ValueObjectBehavior)
    fun inject(test: TransactionalActionSubscriptionCreation)
    fun inject(test: TransactionalActionObservationBehavior)
    fun inject(test: TransactionalActionUpdatingBehavior)
    fun inject(test: TransactionalActionPriority)

    @Component.Builder
    interface Builder : TestApplicationComponent.Builder<UnitTestApplicationComponent, Builder>
}

internal abstract class UnitTestApplication<S : DomainStory>(
        injectTest: UnitTestApplicationComponent.(S) -> Unit
) : TestApplication<S, UnitTestApplicationComponent, UnitTestApplicationComponent.Builder>({ DaggerUnitTestApplicationComponent.builder() }, injectTest)