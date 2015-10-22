package com.aticosoft.appointments.mobile.business.domain.specs.test.common.infrastructure.persistence

import com.aticosoft.appointments.mobile.business.infrastructure.persistence.PersistenceConfigurator
import com.aticosoft.appointments.mobile.business.infrastructure.persistence.PersistenceConfigurator.ConnectionUrlParameters
import org.datanucleus.PropertyNames

/**
 * Created by rodrigo on 10/09/15.
 */
internal class TestPersistenceConfigurator(services: PersistenceConfigurator.Services) : PersistenceConfigurator(services) {

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

    override fun overrideProperties(props: MutableMap<String, Any>): Map<String, Any> {
        props.put(PropertyNames.PROPERTY_SCHEMA_GENERATE_DATABASE_MODE, "drop-and-create")
        return props
    }
}
