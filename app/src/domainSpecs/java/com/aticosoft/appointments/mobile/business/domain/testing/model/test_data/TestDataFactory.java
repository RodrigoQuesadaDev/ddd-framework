package com.aticosoft.appointments.mobile.business.domain.testing.model.test_data;

import javax.annotation.Generated;
import javax.inject.Inject;
import javax.inject.Provider;
@Generated("com.google.auto.factory.processor.AutoFactoryProcessor")
public final class TestDataFactory {
  private final Provider<com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.TestData.Context> arg0Provider;
  @Inject
  public TestDataFactory(Provider<com.aticosoft.appointments.mobile.business.domain.testing.model.test_data.TestData.Context> arg0Provider) {
    this.arg0Provider = arg0Provider;
  }
  public TestData create(int arg1) {
    return new TestData(arg0Provider.get(), arg1);
  }
}
