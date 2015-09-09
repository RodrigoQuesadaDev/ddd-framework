package com.aticosoft.appointments.mobile.business.infrastructure.persistence.datanucleus.plugin

import com.aticosoft.appointments.mobile.business.BuildConfig
import com.rodrigodev.common.file.pathOf
import java.io.File
import java.io.FileOutputStream
import java.net.URL
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by rodrigo on 07/09/15.
 */
class Plugin private constructor(
        private val s: Plugin.Services,
        private val id: String
) {
    companion object {

        val BASE_PATH = "plugins"

        private val PLUGIN_FILE_PATH = "plugin.xml"
        private val MANIFEST_PATH = "META-INF/MANIFEST.MF"
    }

    private val sourceDir: PluginDirectory = PluginDirectory(pathOf(BASE_PATH, id))
    private val targetDir: PluginDirectory = PluginDirectory(pathOf(
            s.applicationInfo.dataDir, BASE_PATH, BuildConfig.VERSION_CODE.toString(), id
    ))
    val url: URL

    init {
        copySourceFiles()
        // Assign url value after files have been moved and directories created
        this.url = initUrl()
    }

    private fun initUrl() = File(targetDir.path).toURI().toURL()

    private fun copySourceFiles() {
        copyFile(PLUGIN_FILE_PATH)
        copyFile(MANIFEST_PATH)
    }

    private fun copyFile(filePath: String) {
        val outputFile = File(targetDir.append(filePath))
        if (!outputFile.exists()) {

            s.assetManager.open(sourceDir.append(filePath)).use { input ->
                outputFile.getParentFile().mkdirs()
                FileOutputStream(outputFile).use { output ->
                    input.copyTo(output)
                }
            }
        }
    }

    class Services @Inject constructor(
            val applicationInfo: android.content.pm.ApplicationInfo,
            val assetManager: android.content.res.AssetManager
    )

    @Singleton
    class Factory @Inject constructor(val services: Services) {

        fun create(id: String) = Plugin(services, id)
    }
}