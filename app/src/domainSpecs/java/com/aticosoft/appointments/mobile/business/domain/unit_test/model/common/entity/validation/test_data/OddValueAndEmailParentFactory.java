package com.aticosoft.appointments.mobile.business.domain.unit_test.model.common.entity.validation.test_data;

import javax.annotation.Generated;
import javax.inject.Inject;
import javax.inject.Provider;

@Generated("com.google.auto.factory.processor.AutoFactoryProcessor")
public final class OddValueAndEmailParentFactory {
    private final Provider<OddValueAndEmailParent.Context> arg0Provider;

    @Inject
    public OddValueAndEmailParentFactory(Provider<OddValueAndEmailParent.Context> arg0Provider) {
        this.arg0Provider = arg0Provider;
    }

    public OddValueAndEmailParent create(int arg1, String arg2, int arg3, String arg4) {
        return new OddValueAndEmailParent(arg0Provider.get(), arg1, arg2, arg3, arg4);
    }
}