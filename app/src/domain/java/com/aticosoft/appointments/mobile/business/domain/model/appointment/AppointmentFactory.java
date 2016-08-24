package com.aticosoft.appointments.mobile.business.domain.model.appointment;

import com.aticosoft.appointments.mobile.business.domain.model.common.entity.Entity;

import javax.annotation.Generated;
import javax.inject.Inject;
import javax.inject.Provider;

@Generated("com.google.auto.factory.processor.AutoFactoryProcessor")
public final class AppointmentFactory {
    private final Provider<Entity.Context> arg0Provider;

    @Inject
    public AppointmentFactory(Provider<Entity.Context> arg0Provider) {
        this.arg0Provider = arg0Provider;
    }

    public Appointment create(String arg1, org.joda.time.Interval arg2) {
        return new Appointment(arg0Provider.get(), arg1, arg2);
    }
}
