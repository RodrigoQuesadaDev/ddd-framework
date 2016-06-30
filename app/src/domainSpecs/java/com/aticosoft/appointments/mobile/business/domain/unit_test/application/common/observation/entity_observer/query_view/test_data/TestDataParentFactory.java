package com.aticosoft.appointments.mobile.business.domain.unit_test.application.common.observation.entity_observer.query_view.test_data;

import javax.annotation.Generated;
import javax.inject.Inject;
import javax.inject.Provider;

@Generated("com.google.auto.factory.processor.AutoFactoryProcessor")
public final class TestDataParentFactory {
    private final Provider<com.aticosoft.appointments.mobile.business.domain.model.common.Entity.Context> arg0Provider;

    @Inject
    public TestDataParentFactory(Provider<com.aticosoft.appointments.mobile.business.domain.model.common.Entity.Context> arg0Provider) {
        this.arg0Provider = arg0Provider;
    }

    public TestDataParent create(int arg1, TestDataSimpleEmbedded arg2, TestDataComplexEmbedded arg3, TestDataChild arg4, TestDataChild arg5) {
        return new TestDataParent(arg0Provider.get(), arg1, arg2, arg3, arg4, arg5);
    }
}