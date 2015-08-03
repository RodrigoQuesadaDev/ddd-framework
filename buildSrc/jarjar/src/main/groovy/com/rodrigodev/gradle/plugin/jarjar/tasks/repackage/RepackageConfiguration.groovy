package com.rodrigodev.gradle.plugin.jarjar.tasks.repackage

import com.rodrigodev.gradle.plugin.jarjar.PluginConfiguration
import com.rodrigodev.gradle.plugin.jarjar.PluginExtension
import org.gradle.api.Project
import org.gradle.api.artifacts.Configuration

class RepackageConfiguration {

    private static final String JARJAR = 'jarjar'
    private static final String REPACKAGE = 'repackage'

    PluginConfiguration pluginConfiguration
    Project project
    Configuration jarjarDependencies
    Configuration repackageDependencies
    @Delegate PluginExtension extension

    RepackageConfiguration(PluginConfiguration pluginConfiguration) {
        this.pluginConfiguration = pluginConfiguration
        this.project = pluginConfiguration.project
        this.extension = pluginConfiguration.extension

        this.jarjarDependencies = project.configurations.create(JARJAR)
        project.configurations.compile.extendsFrom jarjarDependencies

        this.repackageDependencies = project.configurations.create(REPACKAGE)
    }
}