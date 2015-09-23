package com.rodrigodev.gradle.plugin.jbehave.tasks.site

import com.rodrigodev.gradle.plugin.jbehave.PluginConfiguration
import com.rodrigodev.gradle.plugin.jbehave.PluginExtension
import org.gradle.api.Project
import org.gradle.api.artifacts.Configuration

class CopySiteResourcesConfiguration {

    private static final String SITE_RESOURCES = 'jbehaveSiteResources'

    PluginConfiguration pluginConfiguration
    Project project
    Configuration siteResourcesDependencies
    @Delegate PluginExtension extension

    CopySiteResourcesConfiguration(PluginConfiguration pluginConfiguration) {
        this.pluginConfiguration = pluginConfiguration
        this.project = pluginConfiguration.project
        this.extension = pluginConfiguration.extension
        this.siteResourcesDependencies = project.configurations.create(SITE_RESOURCES)
    }

    File getReportsDir() {
        project.file(extension.reportsDir)
    }
}