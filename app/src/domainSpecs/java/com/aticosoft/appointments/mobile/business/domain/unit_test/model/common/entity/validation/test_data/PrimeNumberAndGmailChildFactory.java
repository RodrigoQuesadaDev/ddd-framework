package com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.entity.validation.test_data;

import javax.annotation.Generated;
import javax.inject.Inject;
import javax.inject.Provider;
@Generated("com.google.auto.factory.processor.AutoFactoryProcessor")
public final class PrimeNumberAndGmailChildFactory {
  private final Provider<com.aticosoft.appointments.mobile.business.domain.model.common.Entity.Context> arg0Provider;
  @Inject
  public PrimeNumberAndGmailChildFactory(Provider<com.aticosoft.appointments.mobile.business.domain.model.common.Entity.Context> arg0Provider) {
    this.arg0Provider = arg0Provider;
  }
  public PrimeNumberAndGmailChild create(int arg1, String arg2, int arg3) {
    return new PrimeNumberAndGmailChild(arg0Provider.get(), arg1, arg2, arg3);
  }
}
