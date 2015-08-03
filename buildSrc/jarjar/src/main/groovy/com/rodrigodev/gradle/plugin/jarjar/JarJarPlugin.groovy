package com.rodrigodev.gradle.plugin.jarjar

import com.rodrigodev.gradle.plugin.jarjar.tasks.repackage.AndroidRepackage
import com.rodrigodev.gradle.plugin.jarjar.tasks.repackage.JavaRepackage
import org.gradle.api.GradleException
import org.gradle.api.Plugin
import org.gradle.api.Project

class JarJarPlugin implements Plugin<Project> {

    private static final String JAVA_PLUGIN_ID = 'java'
    private static final String ANDROID_APPLICATION_PLUGIN_ID = 'com.android.application'
    private static final String ANDROID_LIBRARY_PLUGIN_ID = 'com.android.library'

    private Project project

    @Override
    void apply(Project project) {
        this.project = project

        PluginConfiguration pluginConfiguration = new PluginConfiguration(project);

        project.afterEvaluate {
            def setUp;
            if (isJavaProject()) {
                setUp = JavaRepackage.&setUp
            }
            else if (isAndroidProject()) {
                setUp = AndroidRepackage.&setUp
            }
            else {
                throw new GradleException("This plugin may only be applied to Java or Android projects.")
            }
            setUp(project, pluginConfiguration.repackageConfiguration)
        }
    }

    private boolean isJavaProject() {
        project.plugins.findPlugin(JAVA_PLUGIN_ID)
    }

    private boolean isAndroidProject() {
        project.plugins.findPlugin(ANDROID_APPLICATION_PLUGIN_ID) || project.plugins.findPlugin(ANDROID_LIBRARY_PLUGIN_ID)
    }
}
