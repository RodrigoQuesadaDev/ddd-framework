package com.aticosoft.appointments.mobile.business.infrastructure.persistence.datanucleus.plugin

import android.content.res.AssetManager
import java.net.URL
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.properties.Delegates

/**
* Created by Rodrigo Quesada on 08/09/15.
*/
@Singleton
internal class CustomPluginManager @Inject constructor(
        private val  pluginFactory: Plugin.Factory,
        private val  assetManager: AssetManager
) {

    private val plugins: List<Plugin> = assetManager.list(Plugin.BASE_PATH).map { pluginFactory.create(it) }
    val pluginUrls: List<URL> by lazy { plugins.map { it.url } }
}