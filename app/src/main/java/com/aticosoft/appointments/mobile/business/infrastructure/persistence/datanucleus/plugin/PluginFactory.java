package com.aticosoft.appointments.mobile.business.infrastructure.persistence.datanucleus.plugin;

import com.aticosoft.appointments.mobile.business.infrastructure.persistence.datanucleus.plugin.Plugin.Services;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by rodrigo on 13/08/15.
 */
@Singleton
public class PluginFactory {

    @Inject Services services;

    @Inject PluginFactory() {
    }

    public Plugin create(String id) {
        return new Plugin(services, id);
    }
}
