package com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.entity.validation.test_data;

import javax.annotation.Generated;
import javax.inject.Inject;
import javax.inject.Provider;

@Generated("com.google.auto.factory.processor.AutoFactoryProcessor")
public final class OddValueAndEmailChildFactory {
    private final Provider<com.aticosoft.appointments.mobile.business.domain.model.common.Entity.Context> arg0Provider;

    @Inject
    public OddValueAndEmailChildFactory(Provider<com.aticosoft.appointments.mobile.business.domain.model.common.Entity.Context> arg0Provider) {
        this.arg0Provider = arg0Provider;
    }

    public OddValueAndEmailChild create(int arg1, String arg2) {
        return new OddValueAndEmailChild(arg0Provider.get(), arg1, arg2);
    }
}