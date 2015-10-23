package com.rodrigodev.gradle.plugin.jbehave.tasks

import org.gradle.api.DefaultTask
import org.gradle.api.ProjectConfigurationException
import org.gradle.api.Task

abstract class JBehaveTask extends DefaultTask {

    static final String TASK_NAMESPACE = 'jbehave'

    private static final String JAVA_PLUGIN_ID = 'java'
    private static final String ANDROID_APPLICATION_PLUGIN_ID = 'com.android.application'
    private static final String ANDROID_LIBRARY_PLUGIN_ID = 'com.android.library'

    def Task testTask

    JBehaveTask() {
        if (isJavaProject()) {
            testTask = project.tasks.testClasses
        } else if (isAndroidProject()) {
            testTask = project.tasks.preDebugUnitTestBuild
        } else {
            throw new ProjectConfigurationException('No Java or Android plugin configured!', null)
        }
    }

    static String namespace(String name, String suffix = null) {
        return "${TASK_NAMESPACE}_${name}" + (suffix ? suffix.capitalize() : '')
    }

    private boolean isJavaProject() {
        project.plugins.findPlugin(JAVA_PLUGIN_ID)
    }

    private boolean isAndroidProject() {
        project.plugins.findPlugin(ANDROID_APPLICATION_PLUGIN_ID) || project.plugins.findPlugin(ANDROID_LIBRARY_PLUGIN_ID)
    }
}