package com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_listener.test_data;

import javax.annotation.Generated;
import javax.inject.Inject;
import javax.inject.Provider;

@Generated("com.google.auto.factory.processor.AutoFactoryProcessor")
public final class TestDataChildFactory {
    private final Provider<com.aticosoft.appointments.mobile.business.domain.model.common.Entity.Context> arg0Provider;

    @Inject
    public TestDataChildFactory(Provider<com.aticosoft.appointments.mobile.business.domain.model.common.Entity.Context> arg0Provider) {
        this.arg0Provider = arg0Provider;
    }

    public TestDataChild create(int arg1) {
        return new TestDataChild(arg0Provider.get(), arg1);
    }
}