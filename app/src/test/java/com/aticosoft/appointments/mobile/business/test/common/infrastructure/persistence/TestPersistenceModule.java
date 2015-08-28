package com.aticosoft.appointments.mobile.business.test.common.infrastructure.persistence;

import com.aticosoft.appointments.mobile.business.infrastructure.persistence.PersistenceConfigurer;
import com.aticosoft.appointments.mobile.business.infrastructure.persistence.PersistenceConfigurer.Services;
import com.aticosoft.appointments.mobile.business.infrastructure.persistence.PersistenceModule;

import javax.jdo.PersistenceManagerFactory;

import lombok.AllArgsConstructor;

/**
 * Created by rodrigo on 22/08/15.
 */
@AllArgsConstructor
public class TestPersistenceModule extends PersistenceModule {

    @Override protected PersistenceConfigurer providePersistenceConfigurer(Services services) {
        return new TestPersistenceConfigurer(services);
    }
}
