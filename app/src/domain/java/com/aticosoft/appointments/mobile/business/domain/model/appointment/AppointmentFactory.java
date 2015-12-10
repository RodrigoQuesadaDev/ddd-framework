package com.aticosoft.appointments.mobile.business.domain.model.appointment;

import javax.annotation.Generated;
import javax.inject.Inject;
import javax.inject.Provider;
@Generated("com.google.auto.factory.processor.AutoFactoryProcessor")
public final class AppointmentFactory {
  private final Provider<com.aticosoft.appointments.mobile.business.domain.model.common.Entity.Context> arg0Provider;
  @Inject
  public AppointmentFactory(Provider<com.aticosoft.appointments.mobile.business.domain.model.common.Entity.Context> arg0Provider) {
    this.arg0Provider = arg0Provider;
  }
  public Appointment create(long arg1, org.joda.time.DateTime arg2) {
    return new Appointment(arg0Provider.get(), arg1, arg2);
  }
}
