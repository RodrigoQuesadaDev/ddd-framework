package com.rodrigodev.gradle.plugin.jbehave

import org.gradle.api.Project

class PluginExtension {

    private Project project;

    String reportsDir = 'build/jbehave'

    PluginExtension(Project project) {
        this.project = project
    }
}
