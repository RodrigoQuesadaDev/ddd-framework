package com.rodrigodev.gradle.plugin.datanucleus.tasks

import org.gradle.api.DefaultTask

abstract class DataNucleusTask extends DefaultTask {

    static final String TASK_NAMESPACE = 'datanucleus'

    static String namespace(String name, String suffix = null) {
        return "${TASK_NAMESPACE}_${name}" + (suffix ? suffix.capitalize() : '')
    }
}