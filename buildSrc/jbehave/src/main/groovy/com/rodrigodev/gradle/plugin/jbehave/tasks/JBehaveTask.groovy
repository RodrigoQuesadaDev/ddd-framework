package com.rodrigodev.gradle.plugin.jbehave.tasks

import org.gradle.api.DefaultTask

abstract class JBehaveTask extends DefaultTask {

    static final String TASK_NAMESPACE = 'jbehave'

    static String namespace(String name, String suffix = null) {
        return "${TASK_NAMESPACE}_${name}" + (suffix ? suffix.capitalize() : '')
    }
}