package com.aticosoft.appointments.mobile.business.domain.testing.infrastructure.persistence

import com.aticosoft.appointments.mobile.business.infrastructure.persistence.PersistenceConfigurator
import org.datanucleus.PropertyNames

/**
* Created by Rodrigo Quesada on 10/09/15.
*/
internal class TestPersistenceConfigurator(services: PersistenceConfigurator.Services) : PersistenceConfigurator(services) {

    private companion object {
        val PERSISTENCE_UNIT = "Testing"
        val IN_MEMORY_LOCATION = "mem:db1"
        val SCHEMA_GENERATION_MODE = "drop-and-create"
        val PERSISTENCE_FILE = "META-INF/testing/persistence.xml"
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

    override fun overrideProperties(props: MutableMap<Any, Any>): Map<Any, Any> {
        props.put(PropertyNames.PROPERTY_PERSISTENCE_UNIT_NAME, PERSISTENCE_UNIT)
        props.put(PropertyNames.PROPERTY_SCHEMA_GENERATE_DATABASE_MODE, SCHEMA_GENERATION_MODE)
        props.put(PropertyNames.PROPERTY_PERSISTENCE_XML_FILENAME, javaClass.classLoader.getResource(PERSISTENCE_FILE).toString())
        return props
    }
}
