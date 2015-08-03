package com.aticosoft.appointments.mobile.business.infrastructure.annotations;

import java.lang.annotation.Retention;

import javax.inject.Qualifier;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by rodrigo on 27/07/15.
 */
@Qualifier
@Retention(RUNTIME)
public @interface ForApplication {
}
