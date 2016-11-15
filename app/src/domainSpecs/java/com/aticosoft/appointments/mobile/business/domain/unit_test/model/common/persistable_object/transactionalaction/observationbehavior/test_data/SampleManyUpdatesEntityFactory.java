package com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.persistable_object.transactionalaction.observationbehavior.test_data;

import javax.annotation.Generated;
import javax.inject.Inject;
import javax.inject.Provider;
@Generated("com.google.auto.factory.processor.AutoFactoryProcessor")
public final class SampleManyUpdatesEntityFactory {
  private final Provider<com.aticosoft.appointments.mobile.business.domain.model.common.entity.Entity.Context> arg0Provider;
  @Inject
  public SampleManyUpdatesEntityFactory(Provider<com.aticosoft.appointments.mobile.business.domain.model.common.entity.Entity.Context> arg0Provider) {
    this.arg0Provider = arg0Provider;
  }
  public SampleManyUpdatesEntity create(int arg1) {
    return new SampleManyUpdatesEntity(arg0Provider.get(), arg1);
  }
}
