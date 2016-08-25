package com.aticosoft.appointments.mobile.business.infrastructure.domain.model.appointment

import com.aticosoft.appointments.mobile.business.domain.application.common.observation.entity.EntityListener
import com.aticosoft.appointments.mobile.business.domain.application.common.observation.entity.EntityObserver
import com.aticosoft.appointments.mobile.business.domain.application.common.observation.persistable_object.PersistableObjectListener
import com.aticosoft.appointments.mobile.business.domain.model.appointment.Appointment
import com.aticosoft.appointments.mobile.business.domain.model.appointment.AppointmentQueries
import com.aticosoft.appointments.mobile.business.domain.model.appointment.AppointmentQueryView
import com.aticosoft.appointments.mobile.business.domain.model.appointment.validation.AppointmentValidator
import com.aticosoft.appointments.mobile.business.domain.model.appointment.validation.timeslot.TimeSlotsAlignmentValidator
import com.aticosoft.appointments.mobile.business.domain.model.appointment.validation.timeslot.TimeSlotsSpaceValidator
import com.aticosoft.appointments.mobile.business.domain.model.common.entity.EntityRepository
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.PersistableObject
import com.aticosoft.appointments.mobile.business.domain.model.common.persistable_object.validation.PersistableObjectValidator
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.JdoRootEntityModule
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.QueryViews
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.entity.JdoEntityRepository
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.persistable_object.PersistableObjectInitializer
import dagger.Module
import dagger.Provides
import dagger.multibindings.ElementsIntoSet
import dagger.multibindings.IntoSet
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 10/01/16.
 */
@Module
/*internal*/ class AppointmentModule : JdoRootEntityModule<Appointment,
        AppointmentQueries, JdoAppointmentQueries,
        AppointmentQueryView,
        AppointmentValidator,
        PersistableObjectInitializer<Appointment>,
        EntityListener<Appointment>> {

    @Provides
    override fun provideType(): Class<Appointment> = Appointment::class.java

    @Provides @IntoSet
    override fun provideTypeIntoSet(): Class<out PersistableObject<*>> = provideType()

    @Provides @Singleton
    override fun provideQueries(queries: JdoAppointmentQueries): AppointmentQueries = queries

    @Provides @IntoSet @QueryViews
    override fun provideQueryViewsIntoSet(): Class<out Enum<*>> = AppointmentQueryView::class.java

    @Provides @Singleton
    override fun provideRepository(repository: JdoEntityRepository<Appointment>): EntityRepository<Appointment> = repository

    @Provides @ElementsIntoSet
    fun provideValidators(
            timeSlotsAlignmentValidator: TimeSlotsAlignmentValidator, timeSlotsSpaceValidator: TimeSlotsSpaceValidator
    ): Set<PersistableObjectValidator<*, *>> {
        return setOf(timeSlotsAlignmentValidator, timeSlotsSpaceValidator)
    }

    //TODO change/refactor code so that the programmer doesn't need to define these methods (provideEntityInitializerIntoSet & provideEntityListenerIntoSet)
    //TODO also move provideEntityInitializerIntoSet to own module like EntityListenersModule (module that is commented right now)
    @Provides @IntoSet
    override fun provideInitializerIntoSet(initializer: PersistableObjectInitializer<Appointment>): PersistableObjectInitializer<*> = initializer

    @Provides @IntoSet
    override fun provideListenerIntoSet(listener: EntityListener<Appointment>): PersistableObjectListener<*, *> = listener

    @Provides @Singleton
    fun provideEntityObserver(observer: AppointmentObserver): EntityObserver<Appointment> = observer
}