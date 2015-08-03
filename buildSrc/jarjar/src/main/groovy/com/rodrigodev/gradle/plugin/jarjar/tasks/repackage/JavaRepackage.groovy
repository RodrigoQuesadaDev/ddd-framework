package com.rodrigodev.gradle.plugin.jarjar.tasks.repackage

import org.gradle.api.Project

import static AbstractRepackage.TASK_NAME_BASE

class JavaRepackage extends AbstractRepackage {

    static void setUp(Project project, RepackageConfiguration configuration) {
        AbstractRepackage.setupExecution(
                project,
                configuration,
                JavaRepackage,
                project.tasks.compileJava,
                project.tasks.jar
        )
    }
}
