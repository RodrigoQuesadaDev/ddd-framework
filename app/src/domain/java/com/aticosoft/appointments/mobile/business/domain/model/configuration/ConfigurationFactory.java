package com.aticosoft.appointments.mobile.business.domain.model.configuration;

import com.aticosoft.appointments.mobile.business.domain.model.common.entity.Entity;

import javax.annotation.Generated;
import javax.inject.Inject;
import javax.inject.Provider;

@Generated("com.google.auto.factory.processor.AutoFactoryProcessor")
public final class ConfigurationFactory {
    private final Provider<Entity.Context> arg0Provider;

    @Inject
    public ConfigurationFactory(Provider<Entity.Context> arg0Provider) {
        this.arg0Provider = arg0Provider;
    }

    public Configuration create() {
        return new Configuration(arg0Provider.get());
    }
}