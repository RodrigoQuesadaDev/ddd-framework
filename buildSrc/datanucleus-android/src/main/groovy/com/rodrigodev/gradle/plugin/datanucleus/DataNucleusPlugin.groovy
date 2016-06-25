package com.rodrigodev.gradle.plugin.datanucleus

import com.rodrigodev.gradle.plugin.datanucleus.tasks.copy_files.CopyPluginFiles
import com.rodrigodev.gradle.plugin.datanucleus.tasks.enhance.EnhanceJdoEntities
import org.gradle.api.GradleException
import org.gradle.api.Plugin
import org.gradle.api.Project

class DataNucleusPlugin implements Plugin<Project> {

    private static final String ANDROID_APPLICATION_PLUGIN_ID = 'com.android.application'
    private static final String ANDROID_LIBRARY_PLUGIN_ID = 'com.android.library'

    private Project project

    @Override
    void apply(Project project) {
        this.project = project

        PluginConfiguration pluginConfiguration = new PluginConfiguration(project);

        project.afterEvaluate {

            if (!isAndroidProject()) throw new GradleException("This plugin may only be applied to Android projects.")

            CopyPluginFiles.setUp(project, pluginConfiguration.copyPluginFilesConfiguration)
            EnhanceJdoEntities.setUp(project, pluginConfiguration.enhanceJdoEntitiesConfiguration)
        }
    }

    private boolean isAndroidProject() {
        project.plugins.findPlugin(ANDROID_APPLICATION_PLUGIN_ID) || project.plugins.findPlugin(ANDROID_LIBRARY_PLUGIN_ID)
    }
}