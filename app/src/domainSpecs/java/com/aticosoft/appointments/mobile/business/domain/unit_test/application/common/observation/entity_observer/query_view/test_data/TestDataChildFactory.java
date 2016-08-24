package com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.query_view.test_data;

import com.aticosoft.appointments.mobile.business.domain.model.common.entity.Entity;

import javax.annotation.Generated;
import javax.inject.Inject;
import javax.inject.Provider;

@Generated("com.google.auto.factory.processor.AutoFactoryProcessor")
public final class TestDataChildFactory {
    private final Provider<Entity.Context> arg0Provider;

    @Inject
    public TestDataChildFactory(Provider<Entity.Context> arg0Provider) {
        this.arg0Provider = arg0Provider;
    }

    public TestDataChild create(int arg1, TestDataGrandChild arg2, TestDataGrandChild arg3) {
        return new TestDataChild(arg0Provider.get(), arg1, arg2, arg3);
    }
}