package com.aticosoft.appointments.mobile.business.infrastructure.persistence;

import android.content.pm.ApplicationInfo;

import com.aticosoft.appointments.mobile.business.infrastructure.persistence.datanucleus.CustomDataNucleusClassLoader;
import com.google.common.collect.ImmutableMap;

import org.datanucleus.PropertyNames;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

import static com.aticosoft.appointments.mobile.business.infrastructure.persistence.PersistenceConfigurer.ConnectionUrlParameters.CACHE_SIZE;
import static com.aticosoft.appointments.mobile.business.infrastructure.persistence.PersistenceConfigurer.ConnectionUrlParameters.FILE_LOCK;
import static com.aticosoft.appointments.mobile.business.infrastructure.persistence.PersistenceConfigurer.ConnectionUrlParameters.MV_STORE;
import static com.aticosoft.appointments.mobile.business.infrastructure.persistence.PersistenceConfigurer.ConnectionUrlParameters.PAGE_SIZE;

/**
 * Created by rodrigo on 13/08/15.
 */
@Singleton
public class PersistenceConfigurer {

    private static final String PERSISTENCE_UNIT_NAME = "Appointments";
    private static final String CONNECTION_URL_FORMAT = "jdbc:h2:%s";
    private static final String CONNECTION_URL_PARAM_FORMAT = ";%s=%s";

    protected static class ConnectionUrlParameters {
        public static final String MV_STORE = "MV_STORE";
        public static final String FILE_LOCK = "FILE_LOCK";
        public static final String PAGE_SIZE = "PAGE_SIZE";
        public static final String CACHE_SIZE = "CACHE_SIZE";
    }

    @Inject CustomDataNucleusClassLoader customDataNucleusClassLoader;
    @Inject ApplicationInfo applicationInfo;

    @Inject protected PersistenceConfigurer() {
    }

    public PersistenceManagerFactory createPersistenceManagerFactory() {
        return JDOHelper.getPersistenceManagerFactory(properties(), PERSISTENCE_UNIT_NAME);
    }

    private String connectionUrl() {
        StringBuilder url = new StringBuilder(String.format(CONNECTION_URL_FORMAT, location()));
        addParams(url);
        return url.toString();
    }

    private void addParams(StringBuilder url) {
        @SuppressWarnings("unchecked")
        Iterable<Entry> entries = connectionUrlParameters().entrySet();
        for (Entry entry : entries) {
            url.append(String.format(
                    CONNECTION_URL_PARAM_FORMAT, entry.getKey(), entry.getValue()
            ));
        }
    }

    protected String location() {
        return applicationInfo.dataDir + "/databases/h2";
    }

    @SuppressWarnings("unchecked")
    private ImmutableMap properties() {
        Map props = new HashMap();
        props.put(PropertyNames.PROPERTY_CONNECTION_URL, connectionUrl());
        props.put(PropertyNames.PROPERTY_CLASSLOADER_PRIMARY, customDataNucleusClassLoader);
        return ImmutableMap.copyOf(overrideProperties(props));
    }

    protected Map overrideProperties(Map properties) {
        return properties;
    }

    @SuppressWarnings("unchecked")
    private ImmutableMap connectionUrlParameters() {
        Map params = new HashMap();
        params.put(MV_STORE, false);
        params.put(FILE_LOCK, "FS");
        params.put(PAGE_SIZE, 1024);
        params.put(CACHE_SIZE, 8192);
        return ImmutableMap.copyOf(overrideConnectionUrlParameters(params));
    }

    protected Map overrideConnectionUrlParameters(Map parameters) {
        return parameters;
    }
}
