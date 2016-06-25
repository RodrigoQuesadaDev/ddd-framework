package com.aticosoft.appointments.mobile.business.domain.model.client;

import javax.annotation.Generated;
import javax.inject.Inject;
import javax.inject.Provider;

@Generated("com.google.auto.factory.processor.AutoFactoryProcessor")
public final class ClientFactory {
    private final Provider<com.aticosoft.appointments.mobile.business.domain.model.common.Entity.Context> arg0Provider;

    @Inject
    public ClientFactory(Provider<com.aticosoft.appointments.mobile.business.domain.model.common.Entity.Context> arg0Provider) {
        this.arg0Provider = arg0Provider;
    }

    public Client create(String arg1, org.joda.time.LocalDate arg2) {
        return new Client(arg0Provider.get(), arg1, arg2);
    }
}
