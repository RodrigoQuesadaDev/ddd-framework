package com.aticosoft.appointments.mobile.business.infrastructure.persistence.configuration

import android.content.pm.ApplicationInfo
import com.aticosoft.appointments.mobile.business.infrastructure.persistence.datanucleus.CustomDataNucleusClassLoader
import com.rodrigodev.common.collection.toHashMap
import com.rodrigodev.common.file.pathOf
import org.datanucleus.PropertyNames
import org.datanucleus.util.PersistenceUtils
import javax.inject.Inject
import javax.inject.Singleton
import javax.jdo.JDOHelper
import javax.jdo.PersistenceManagerFactory

/**
 * Created by Rodrigo Quesada on 08/09/15.
 */
@Singleton
/*internal*/ interface PersistenceConfigurator {

    fun createPersistenceManagerFactory(): PersistenceManagerFactory
}

@Singleton
/*internal*/ open class PersistenceConfiguratorImpl @Inject protected constructor() : PersistenceConfigurator {
    private companion object {
        val PROPERTIES_FILE = "META-INF/datanucleus.properties"
        val CONNECTION_URL_FORMAT = "jdbc:h2:%s"
        val CONNECTION_URL_PARAM_FORMAT = ";%s=%s"
    }

    protected object ConnectionUrlParameters {
        val MV_STORE = "MV_STORE"
        val FILE_LOCK = "FILE_LOCK"
        val PAGE_SIZE = "PAGE_SIZE"
        val CACHE_SIZE = "CACHE_SIZE"

        val defaultValues = mapOf(
                MV_STORE to false,
                FILE_LOCK to "FS",
                PAGE_SIZE to 1024,
                CACHE_SIZE to 8192
        )
    }

    @Inject lateinit var customDataNucleusClassLoader: CustomDataNucleusClassLoader
    @Inject lateinit var applicationInfo: ApplicationInfo

    override fun createPersistenceManagerFactory() = JDOHelper.getPersistenceManagerFactory(properties())

    private fun properties(): Map<Any, Any> {

        val props = hashMapOf<Any, Any>(
                PropertyNames.PROPERTY_CONNECTION_URL to connectionUrl(),
                PropertyNames.PROPERTY_CLASSLOADER_PRIMARY to customDataNucleusClassLoader
        )
        props.putAll(PersistenceUtils.setPropertiesUsingFile(PROPERTIES_FILE))

        return overrideProperties(props)
    }

    protected open fun overrideProperties(props: MutableMap<Any, Any>): Map<Any, Any> = props

    protected open fun overrideConnectionUrlParameters(parameters: MutableMap<String, Any>): Map<String, Any> = parameters

    //region Utils
    private fun connectionUrl() = CONNECTION_URL_FORMAT.format(location()) + urlParams()

    protected open fun location(): String = pathOf(applicationInfo.dataDir, "databases/h2")

    private fun urlParams() = connectionUrlParameters().map { CONNECTION_URL_PARAM_FORMAT.format(it.key, it.value) }

    private fun connectionUrlParameters(): Map<String, Any> = overrideConnectionUrlParameters(ConnectionUrlParameters.defaultValues.toHashMap())
    //endregion
}