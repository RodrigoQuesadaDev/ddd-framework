package com.rodrigodev.gradle.plugin.jarjar.tasks.modify_dependencies

import com.rodrigodev.gradle.plugin.jarjar.tasks.RunJarJarTask
import com.rodrigodev.gradle.plugin.jarjar.tasks.repackage.RepackageConfiguration
import org.gradle.api.Project
import org.gradle.api.tasks.TaskAction

class ModifyDependencies extends RunJarJarTask {

    static final TASK_NAME = 'modifyDependencies'

    private List<TaskWrapper> removeOldDependenciesList = []
    private List<TaskWrapper> addRepackagedJarList = []
    RepackageConfiguration configuration

    static ModifyDependencies setUp(Project project, RepackageConfiguration configuration, String nameSuffix) {
        return project.task(namespace(TASK_NAME, nameSuffix), type: ModifyDependencies) {
            delegate.configuration = configuration
        }
    }

    void removeOldDependenciesAndAddRepackageJar(TaskWrapper... tasks){
        removeOldDependencies(tasks)
        addRepackagedJar(tasks)
    }

    void removeOldDependencies(TaskWrapper... tasks){
        removeOldDependenciesList.addAll(tasks)
    }

    void addRepackagedJar(TaskWrapper... tasks){
        addRepackagedJarList.addAll(tasks)
    }

    @TaskAction
    def taskAction() {
        removeOldDependenciesList.each {
            it.dependencies -= configuration.repackageDependencies
        }
        addRepackagedJarList.each {
            it.dependencies += project.files(configuration.output)
        }
    }
}