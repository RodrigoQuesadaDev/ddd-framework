package com.aticosoft.appointments.mobile.business.infrastructure.persistence.datanucleus.plugin

import android.content.res.AssetManager
import java.net.URL
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.properties.Delegates

/**
 * Created by rodrigo on 08/09/15.
 */
@Singleton
class CustomPluginManager @Inject constructor(
        private val  pluginFactory: Plugin.Factory,
        private val  assetManager: AssetManager
) {

    private val plugins: List<Plugin> = assetManager.list(Plugin.BASE_PATH).map { pluginFactory.create(it) }
    val pluginUrls: List<URL> by Delegates.lazy { plugins.map { it.url } }
}