package com.aticosoft.appointments.mobile.business.infrastructure.persistence

import android.content.pm.ApplicationInfo
import com.aticosoft.appointments.mobile.business.infrastructure.persistence.datanucleus.CustomDataNucleusClassLoader
import com.rodrigodev.common.collection.toHashMap
import com.rodrigodev.common.file.pathOf
import org.datanucleus.PropertyNames
import javax.inject.Inject
import javax.inject.Singleton
import javax.jdo.JDOHelper

/**
 * Created by rodrigo on 08/09/15.
 */
@Singleton
/*internal*/ open class PersistenceConfigurator(
        private val s: PersistenceConfigurator.Services
) {
    private companion object {
        val PERSISTENCE_UNIT_NAME = "Appointments"
        val CONNECTION_URL_FORMAT = "jdbc:h2:%s"
        val CONNECTION_URL_PARAM_FORMAT = ";%s=%s"
    }

    //TODO this object should be protected but it doesnt work right now
    object ConnectionUrlParameters {
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

    fun createPersistenceManagerFactory() = JDOHelper.getPersistenceManagerFactory(properties(), PERSISTENCE_UNIT_NAME)

    private fun properties(): Map<String, Any> = overrideProperties(hashMapOf(
            PropertyNames.PROPERTY_CONNECTION_URL to connectionUrl(),
            PropertyNames.PROPERTY_CLASSLOADER_PRIMARY to s.customDataNucleusClassLoader
    ))

    protected open fun overrideProperties(props: MutableMap<String, Any>): Map<String, Any> = props

    private fun connectionUrl() = CONNECTION_URL_FORMAT.format(location()) + urlParams()

    protected open fun location(): String = pathOf(s.applicationInfo.dataDir, "databases/h2")

    private fun urlParams() = connectionUrlParameters().map { CONNECTION_URL_PARAM_FORMAT.format(it.key, it.value) }

    private fun connectionUrlParameters(): Map<String, Any> = overrideConnectionUrlParameters(ConnectionUrlParameters.defaultValues.toHashMap())

    protected open fun overrideConnectionUrlParameters(parameters: MutableMap<String, Any>): Map<String, Any> = parameters

    class Services @Inject constructor(
            val customDataNucleusClassLoader: CustomDataNucleusClassLoader,
            val applicationInfo: ApplicationInfo
    )
}