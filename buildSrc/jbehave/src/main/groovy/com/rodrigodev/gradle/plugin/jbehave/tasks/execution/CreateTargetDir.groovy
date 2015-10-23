package com.rodrigodev.gradle.plugin.jbehave.tasks.execution

import com.rodrigodev.gradle.plugin.jbehave.tasks.JBehaveTask
import org.gradle.api.Project
import org.gradle.api.tasks.TaskAction

class CreateTargetDir extends JBehaveTask {

    private static final String TASK_NAME = 'createTargetDir'

    static final String TARGET_DIR_PATH = 'target'

    CreateTargetDir() {
        testTask.dependsOn this
    }

    public static void setUp(Project project) {
        CreateTargetDir task = project.task(namespace(TASK_NAME), type: CreateTargetDir, null)
    }

    @TaskAction
    def taskAction() {
        def targetDir = project.file(TARGET_DIR_PATH)
        if (!targetDir.exists()) {
            targetDir.mkdir()
        }
    }
}