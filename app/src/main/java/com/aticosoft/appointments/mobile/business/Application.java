package com.aticosoft.appointments.mobile.business;

import lombok.experimental.Accessors;

/**
 * Created by rodrigo on 27/07/15.
 */
@Accessors
public class Application extends AbstractApplication<ApplicationComponent> {

    @Override
    protected ApplicationComponent createApplicationComponent() {
        return DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }
}
