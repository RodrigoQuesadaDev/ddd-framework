package com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.persistable_object.transactionalaction.observationbehavior.test_data;

import javax.annotation.Generated;
import javax.inject.Inject;
import javax.inject.Provider;
@Generated("com.google.auto.factory.processor.AutoFactoryProcessor")
public final class SampleMultiEntityFactory {
  private final Provider<com.aticosoft.appointments.mobile.business.domain.model.common.entity.Entity.Context> arg0Provider;
  @Inject
  public SampleMultiEntityFactory(Provider<com.aticosoft.appointments.mobile.business.domain.model.common.entity.Entity.Context> arg0Provider) {
    this.arg0Provider = arg0Provider;
  }
  public SampleMultiEntity create(int arg1) {
    return new SampleMultiEntity(arg0Provider.get(), arg1);
  }
}
