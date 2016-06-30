package com.aticosoft.appointments.mobile.business.infrastructure.domain.model.appointment

import com.aticosoft.appointments.mobile.business.domain.application.common.observation.EntityListener
import com.aticosoft.appointments.mobile.business.domain.application.common.observation.EntityObserver
import com.aticosoft.appointments.mobile.business.domain.model.appointment.Appointment
import com.aticosoft.appointments.mobile.business.domain.model.appointment.AppointmentQueries
import com.aticosoft.appointments.mobile.business.domain.model.appointment.AppointmentQueryView
import com.aticosoft.appointments.mobile.business.domain.model.appointment.validation.AppointmentValidator
import com.aticosoft.appointments.mobile.business.domain.model.appointment.validation.TimeSlotsValidation
import com.aticosoft.appointments.mobile.business.domain.model.common.Entity
import com.aticosoft.appointments.mobile.business.domain.model.common.Repository
import com.aticosoft.appointments.mobile.business.domain.model.common.validation.EntityValidator
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.QueryViews
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.RootEntityModule
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.entity.EntityInitializer
import dagger.Module
import dagger.Provides
import dagger.multibindings.ElementsIntoSet
import dagger.multibindings.IntoSet
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 10/01/16.
 */
@Module
/*internal*/ class AppointmentModule : RootEntityModule<Appointment,
        AppointmentQueries, JdoAppointmentQueries,
        AppointmentQueryView,
        JdoAppointmentRepository,
        AppointmentValidator<*>,
        EntityInitializer<Appointment>,
        EntityListener<Appointment>> {

    @Provides
    override fun provideEntityType(): Class<Appointment> = Appointment::class.java

    @Provides @IntoSet
    override fun provideEntityTypeIntoSet(): Class<out Entity> = provideEntityType()

    @Provides @Singleton
    override fun provideQueries(queries: JdoAppointmentQueries): AppointmentQueries = queries

    @Provides @IntoSet @QueryViews
    override fun provideQueryViewsIntoSet(): Class<out Enum<*>> = AppointmentQueryView::class.java

    @Provides @Singleton
    override fun provideRepository(repository: JdoAppointmentRepository): Repository<Appointment> = repository

    @Provides @ElementsIntoSet
    fun provideValidators(timeSlotsValidation: TimeSlotsValidation): Set<EntityValidator<*, *>> = timeSlotsValidation.validators

    //TODO change/refactor code so that the programmer doesn't need to define these methods (provideEntityInitializerIntoSet & provideEntityListenerIntoSet)
    //TODO also move provideEntityInitializerIntoSet to own module like EntityListenersModule (module that is commented right now)
    @Provides @IntoSet
    override fun provideEntityInitializerIntoSet(initializer: EntityInitializer<Appointment>): EntityInitializer<*> = initializer

    @Provides @IntoSet
    override fun provideEntityListenerIntoSet(listener: EntityListener<Appointment>): EntityListener<*> = listener

    @Provides @Singleton
    fun provideEntityObserver(observer: AppointmentObserver): EntityObserver<Appointment> = observer
}