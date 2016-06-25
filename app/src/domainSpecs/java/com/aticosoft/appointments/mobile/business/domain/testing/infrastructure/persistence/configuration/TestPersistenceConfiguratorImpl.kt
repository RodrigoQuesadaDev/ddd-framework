package com.aticosoft.appointments.mobile.business.domain.testing.infrastructure.persistence.configuration

import com.aticosoft.appointments.mobile.business.infrastructure.persistence.configuration.PersistenceConfiguratorImpl
import org.datanucleus.PropertyNames
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rodrigo Quesada on 10/09/15.
 */
@Singleton
internal class TestPersistenceConfiguratorImpl @Inject protected constructor() : PersistenceConfiguratorImpl() {

    private companion object {
        val PERSISTENCE_UNIT = "Testing"
        val IN_MEMORY_LOCATION = "mem:db1"
        val SCHEMA_GENERATION_MODE = "drop-and-create"
        val PERSISTENCE_FILE = "META-INF/testing/persistence.xml"
    }

    override fun location() = IN_MEMORY_LOCATION

    override fun overrideConnectionUrlParameters(parameters: MutableMap<String, Any>): Map<String, Any> {
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
