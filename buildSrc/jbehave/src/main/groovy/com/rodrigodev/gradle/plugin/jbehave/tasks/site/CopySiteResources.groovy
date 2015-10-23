package com.rodrigodev.gradle.plugin.jbehave.tasks.site

import com.rodrigodev.gradle.plugin.jbehave.tasks.JBehaveTask
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.tasks.Copy

class CopySiteResources extends JBehaveTask {

    static final String TASK_NAME = 'copySiteResources'

    private static class DependencyFiles {
        static final String CORE = "jbehave-core"
        static final String SITE_RESOURCES = "jbehave-site-resources"
    }

    File viewDir

    CopySiteResourcesConfiguration configuration

    public void init() {
        this.viewDir = new File(configuration.reportsDir, "view")

        this.dependsOn project.task(namespace(TASK_NAME + "_core"), type: Copy) {
            from(project.zipTree(jarFile(DependencyFiles.CORE))) {
                include "style/*"
            }
            into(viewDir)
        }

        this.dependsOn project.task(namespace(TASK_NAME + "_siteResources"), type: Copy) {
            from(project.zipTree(jarFile(DependencyFiles.SITE_RESOURCES))) {
                include "js/**/*"
                include "style/**/*"
                include "images/*"
            }
            into(viewDir)
        }

        testTask.dependsOn this
    }

    def jarFile(String jarName) {
        configuration.siteResourcesDependencies.find({ it.name.startsWith(jarName) })
    }

    protected static void setUp(Project project, CopySiteResourcesConfiguration configuration) {
        Task task = project.task(namespace(TASK_NAME), type: CopySiteResources) {
            delegate.configuration = configuration
        }

        task.init()
    }
}