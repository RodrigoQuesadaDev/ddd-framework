package com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.query_view.test_data;

import com.aticosoft.appointments.mobile.business.domain.model.common.entity.Entity;

import javax.annotation.Generated;
import javax.inject.Inject;
import javax.inject.Provider;

@Generated("com.google.auto.factory.processor.AutoFactoryProcessor")
public final class TestDataGrandChildFactory {
    private final Provider<Entity.Context> arg0Provider;

    @Inject
    public TestDataGrandChildFactory(Provider<Entity.Context> arg0Provider) {
        this.arg0Provider = arg0Provider;
    }

    public TestDataGrandChild create(int arg1, TestDataSimpleEmbedded arg2, TestDataComplexEmbedded arg3) {
        return new TestDataGrandChild(arg0Provider.get(), arg1, arg2, arg3);
    }
}