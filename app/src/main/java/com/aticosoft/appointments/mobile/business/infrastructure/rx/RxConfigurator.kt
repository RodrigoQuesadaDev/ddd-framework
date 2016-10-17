package com.aticosoft.appointments.mobile.business.infrastructure.rx

import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 17/10/16.
 */
@Singleton
/*internal*/ class RxConfigurator @Inject protected constructor() {

    fun configure() {
        //TODO implement logic that send error to logging component?
        //RxJavaHooks.setOnError { System.err.println("exception: $it") }
    }
}