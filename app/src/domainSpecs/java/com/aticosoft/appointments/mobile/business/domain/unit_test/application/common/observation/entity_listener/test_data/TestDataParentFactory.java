package com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_listener.test_data;

import javax.annotation.Generated;
import javax.inject.Inject;
import javax.inject.Provider;

@Generated("com.google.auto.factory.processor.AutoFactoryProcessor")
public final class TestDataParentFactory {
    private final Provider<TestDataParent.Context> arg0Provider;

    @Inject
    public TestDataParentFactory(Provider<TestDataParent.Context> arg0Provider) {
        this.arg0Provider = arg0Provider;
    }

    public TestDataParent create(int arg1, int arg2) {
        return new TestDataParent(arg0Provider.get(), arg1, arg2);
    }
}