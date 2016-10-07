package com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.valueobject.behavior.test_data;

import javax.annotation.Generated;
import javax.inject.Inject;
import javax.inject.Provider;

@Generated("com.google.auto.factory.processor.AutoFactoryProcessor")
public final class EntityWithValueObjectFactory {
    private final Provider<EntityWithValueObject.Context> arg0Provider;

    @Inject
    public EntityWithValueObjectFactory(Provider<EntityWithValueObject.Context> arg0Provider) {
        this.arg0Provider = arg0Provider;
    }

    public EntityWithValueObject create(int arg1) {
        return new EntityWithValueObject(arg0Provider.get(), arg1);
    }
}
