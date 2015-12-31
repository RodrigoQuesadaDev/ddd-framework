package com.aticosoft.appointments.mobile.business.infrastructure.domain.model.appointment

import com.aticosoft.appointments.mobile.business.domain.model.appointment.Appointment
import com.aticosoft.appointments.mobile.business.domain.model.appointment.AppointmentQueries
import com.aticosoft.appointments.mobile.business.domain.model.appointment.AppointmentRepository
import com.aticosoft.appointments.mobile.business.domain.model.common.validation.EntityValidator
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.EntityModule
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.entity.EntityInitializer
import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.create
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 10/01/16.
 */
@Module
/*internal*/ class AppointmentModule : EntityModule {
    companion object : EntityModule.CompanionObject {

        override val entityType = Appointment::class.java

        override val validators = emptyArray<EntityValidator<*>>()

        override fun EntityInitializer.Factory.create() = create<Appointment> { inject(it) }
    }

    @Provides @Singleton
    fun provideAppointmentQueries(appointmentQueries: JdoAppointmentQueries): AppointmentQueries = appointmentQueries

    @Provides @Singleton
    fun provideAppointmentRepository(appointmentRepository: JdoAppointmentRepository): AppointmentRepository = appointmentRepository

    interface EntityInjection {
        fun inject(entity: Appointment)
    }
}