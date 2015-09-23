package com.aticosoft.appointments.mobile.business.infrastructure.persistence.datanucleus.plugin

import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by rodrigo on 22/09/15.
 */
internal class PluginFileCopier private constructor(
        private val s: PluginFileCopier.Services,
        private val sourceDir: PluginDirectory,
        private val targetDir: PluginDirectory
) {

    fun copyFile(filePath: String) {
        val outputFile = File(targetDir.append(filePath))
        if (!outputFile.exists()) {

            s.assetManager.open(sourceDir.append(filePath)).use { input ->
                outputFile.parentFile.mkdirs()
                FileOutputStream(outputFile).use { output ->
                    input.copyTo(output)
                }
            }
        }
    }

    protected class Services @Inject constructor(
            val assetManager: android.content.res.AssetManager
    )

    @Singleton
    class Factory @Inject constructor(val services: PluginFileCopier.Services) {

        fun create(sourceDir: PluginDirectory, targetDir: PluginDirectory) = PluginFileCopier(services, sourceDir, targetDir)
    }
}