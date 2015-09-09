package com.aticosoft.appointments.mobile.business.test.common.infrastructure.persistence

import com.aticosoft.appointments.mobile.business.infrastructure.persistence.PersistenceConfigurer
import com.aticosoft.appointments.mobile.business.infrastructure.persistence.PersistenceConfigurer.ConnectionUrlParameters

/**
 * Created by rodrigo on 10/09/15.
 */
class TestPersistenceConfigurer(services: PersistenceConfigurer.Services) : PersistenceConfigurer(services) {

    private companion object {
        val IN_MEMORY_LOCATION: String = "mem:db1"
    }

    override protected fun location() = IN_MEMORY_LOCATION

    override protected fun overrideConnectionUrlParameters(parameters: MutableMap<String, Any>): Map<String, Any> {
        with(parameters) {
            remove(ConnectionUrlParameters.FILE_LOCK)
            remove(ConnectionUrlParameters.PAGE_SIZE)
            remove(ConnectionUrlParameters.CACHE_SIZE)
        }
        return parameters
    }
}
