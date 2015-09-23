package com.rodrigodev.gradle.plugin.jbehave

import com.rodrigodev.gradle.plugin.jbehave.PluginExtension
import com.rodrigodev.gradle.plugin.jbehave.tasks.site.CopySiteResourcesConfiguration
import org.gradle.api.Project

class PluginConfiguration {

    private static final String EXTENSION_NAME = 'jbehave'

    private Project project
    final PluginExtension extension

    final CopySiteResourcesConfiguration copySiteResourcesConfiguration

    PluginConfiguration(Project project) {
        this.project = project;
        this.extension = project.extensions.create(EXTENSION_NAME, PluginExtension, project)
        this.copySiteResourcesConfiguration = new CopySiteResourcesConfiguration(this)
    }
}