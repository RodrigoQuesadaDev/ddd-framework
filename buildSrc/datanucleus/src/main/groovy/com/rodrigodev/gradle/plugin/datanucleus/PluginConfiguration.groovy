package com.rodrigodev.gradle.plugin.datanucleus

import com.rodrigodev.gradle.plugin.datanucleus.PluginExtension
import org.gradle.api.Project
import org.gradle.api.artifacts.Configuration

class PluginConfiguration {

    private static final String DATANUCLEUS = 'datanucleus'
    private static final String EXTENSION_NAME = 'datanucleus'

    private Project project
    final PluginExtension extension
    Configuration datanucleusDependencies

    final EnhanceJdoEntitiesConfiguration enhanceJdoEntitiesConfiguration

    PluginConfiguration(Project project) {
        this.project = project;
        this.extension = project.extensions.create(EXTENSION_NAME, PluginExtension)
        this.datanucleusDependencies = project.configurations.create(DATANUCLEUS)
        this.enhanceJdoEntitiesConfiguration = new EnhanceJdoEntitiesConfiguration(this)
    }
}