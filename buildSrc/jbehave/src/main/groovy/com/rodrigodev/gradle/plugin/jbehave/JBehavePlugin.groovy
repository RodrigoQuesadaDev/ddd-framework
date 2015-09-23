package com.rodrigodev.gradle.plugin.jbehave

import com.rodrigodev.gradle.plugin.jbehave.tasks.site.CopySiteResources
import org.gradle.api.Plugin
import org.gradle.api.Project

class JBehavePlugin implements Plugin<Project> {

    private Project project

    @Override
    void apply(Project project) {
        this.project = project

        PluginConfiguration pluginConfiguration = new PluginConfiguration(project);

        project.afterEvaluate {
            CopySiteResources.setUp(project, pluginConfiguration.copySiteResourcesConfiguration)
        }
    }
}
