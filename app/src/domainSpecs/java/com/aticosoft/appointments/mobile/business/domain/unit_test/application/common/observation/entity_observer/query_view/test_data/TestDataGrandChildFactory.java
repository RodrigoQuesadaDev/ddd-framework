package com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.query_view.test_data;

import javax.annotation.Generated;
import javax.inject.Inject;
import javax.inject.Provider;
@Generated("com.google.auto.factory.processor.AutoFactoryProcessor")
public final class TestDataGrandChildFactory {
  private final Provider<com.aticosoft.appointments.mobile.business.domain.model.common.Entity.Context> arg0Provider;
  @Inject
  public TestDataGrandChildFactory(Provider<com.aticosoft.appointments.mobile.business.domain.model.common.Entity.Context> arg0Provider) {
    this.arg0Provider = arg0Provider;
  }
  public TestDataGrandChild create(int arg1) {
    return new TestDataGrandChild(arg0Provider.get(), arg1);
  }
}
