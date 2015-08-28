package com.aticosoft.appointments.mobile.business.test.common.infrastructure.persistence;

import com.aticosoft.appointments.mobile.business.infrastructure.persistence.PersistenceConfigurer;

import java.util.Map;

/**
 * Created by rodrigo on 22/08/15.
 */
public class TestPersistenceConfigurer extends PersistenceConfigurer {

    public static final String IN_MEMORY_LOCATION = "mem:db1";

    protected TestPersistenceConfigurer(Services services) {
        super(services);
    }

    @Override protected String location() {
        return IN_MEMORY_LOCATION;
    }

    @Override protected Map overrideConnectionUrlParameters(Map parameters) {
        parameters.remove(ConnectionUrlParameters.FILE_LOCK);
        parameters.remove(ConnectionUrlParameters.PAGE_SIZE);
        parameters.remove(ConnectionUrlParameters.CACHE_SIZE);
        return parameters;
    }
}
