package com.rodrigodev.gradle.plugin.datanucleus.tasks.copy_files

import com.rodrigodev.gradle.plugin.datanucleus.PluginConfiguration

class CopyPluginFilesConfiguration {

    @Delegate PluginConfiguration pluginConfiguration

    CopyPluginFilesConfiguration(PluginConfiguration pluginConfiguration) {
        this.pluginConfiguration = pluginConfiguration
    }
}