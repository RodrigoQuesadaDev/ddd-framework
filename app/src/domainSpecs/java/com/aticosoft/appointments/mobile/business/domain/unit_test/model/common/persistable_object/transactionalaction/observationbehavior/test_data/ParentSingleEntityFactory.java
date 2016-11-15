package com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.persistable_object.transactionalaction.observationbehavior.test_data;

import javax.annotation.Generated;
import javax.inject.Inject;
import javax.inject.Provider;

@Generated("com.google.auto.factory.processor.AutoFactoryProcessor")
public final class ParentSingleEntityFactory {
    private final Provider<ParentSingleEntity.Context> arg0Provider;

    @Inject
    public ParentSingleEntityFactory(Provider<ParentSingleEntity.Context> arg0Provider) {
        this.arg0Provider = arg0Provider;
    }

    public ParentSingleEntity create(int arg1) {
        return new ParentSingleEntity(arg0Provider.get(), arg1);
    }
}
