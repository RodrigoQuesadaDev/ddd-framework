package com.aticosoft.appointments.mobile.business.domain.testing.model.common.valueobject;

import com.aticosoft.appointments.mobile.business.infrastructure.domain.model.common.valueobject.ValueObjectsManager;

import javax.annotation.Generated;
import javax.inject.Inject;
import javax.inject.Provider;

@Generated("com.google.auto.factory.processor.AutoFactoryProcessor")
public final class ValueObjectRegistrationVerifierFactory {
    private final Provider<ValueObjectsManager> arg0Provider;

    @Inject
    public ValueObjectRegistrationVerifierFactory(Provider<ValueObjectsManager> arg0Provider) {
        this.arg0Provider = arg0Provider;
    }

    public ValueObjectRegistrationVerifier create(String[] arg1) {
        return new ValueObjectRegistrationVerifier(arg0Provider.get(), arg1);
    }
}
