package com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.entity.validation.test_data;

import javax.annotation.Generated;
import javax.inject.Inject;
import javax.inject.Provider;
@Generated("com.google.auto.factory.processor.AutoFactoryProcessor")
public final class PrimeNumberAndGmailParentFactory {
  private final Provider<PrimeNumberAndGmailParent.Context> arg0Provider;
  @Inject
  public PrimeNumberAndGmailParentFactory(Provider<PrimeNumberAndGmailParent.Context> arg0Provider) {
    this.arg0Provider = arg0Provider;
  }
  public PrimeNumberAndGmailParent create(int arg1, String arg2, int arg3, int arg4, String arg5, int arg6) {
    return new PrimeNumberAndGmailParent(arg0Provider.get(), arg1, arg2, arg3, arg4, arg5, arg6);
  }
}
