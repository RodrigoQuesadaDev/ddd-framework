package com.aticosoft.appointments.mobile.business.infrastructure.persistence.datanucleus.plugin

import com.aticosoft.appointments.mobile.business.BuildConfig
import com.rodrigodev.common.file.pathOf
import java.io.File
import java.net.URL
import javax.inject.Inject
import javax.inject.Singleton

/**
* Created by Rodrigo Quesada on 07/09/15.
*/
internal class Plugin private constructor(
        private val s: Plugin.Services,
        private val id: String
) {
    companion object {
        val BASE_PATH = "plugins"
    }

    private object Files {
        val PLUGIN_FILE_PATH = "plugin.xml"
        val MANIFEST_PATH = "META-INF/MANIFEST.MF"
    }

    private val sourceDir: PluginDirectory = PluginDirectory(pathOf(BASE_PATH, id))
    private val targetDir: PluginDirectory = PluginDirectory(pathOf(
            s.applicationInfo.dataDir, BASE_PATH, BuildConfig.VERSION_CODE.toString(), id
    ))
    private val fileCopier: PluginFileCopier = s.fileCopierFactory.create(sourceDir, targetDir)
    val url: URL

    init {
        copySourceFiles()
        // Assign url value after files have been moved and directories created
        this.url = initUrl()
    }

    private fun initUrl() = File(targetDir.path).toURI().toURL()

    private fun copySourceFiles() {
        fileCopier.copyFile(Files.PLUGIN_FILE_PATH)
        fileCopier.copyFile(Files.MANIFEST_PATH)
    }

    protected class Services @Inject constructor(
            val applicationInfo: android.content.pm.ApplicationInfo,
            val fileCopierFactory: PluginFileCopier.Factory
    )

    @Singleton
    class Factory @Inject constructor(val services: Services) {

        fun create(id: String) = Plugin(services, id)
    }
}