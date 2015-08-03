package com.aticosoft.appointments.mobile.business;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by rodrigo on 27/07/15.
 */
@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(MainActivity mainActivity);
}
