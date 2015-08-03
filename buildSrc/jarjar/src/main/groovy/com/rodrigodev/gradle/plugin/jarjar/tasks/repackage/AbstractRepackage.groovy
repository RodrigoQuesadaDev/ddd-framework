package com.rodrigodev.gradle.plugin.jarjar.tasks.repackage

import com.rodrigodev.gradle.plugin.jarjar.tasks.RunJarJarTask
import com.rodrigodev.gradle.plugin.jarjar.tasks.modify_dependencies.ModifyDependencies
import com.rodrigodev.gradle.plugin.jarjar.tasks.modify_dependencies.TaskWrapper
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.file.FileCollection
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.compile.JavaCompile

abstract class AbstractRepackage extends RunJarJarTask {

    static final String TASK_NAME = 'repackage'

    @InputFiles FileCollection jarjarDependencies
    @InputFiles FileCollection inputJars
    @Input Set rules
    @Input Set<String> excludes
    @OutputFile File output
    boolean verbose

    RepackageConfiguration configuration

    public void init() {
        this.jarjarDependencies = configuration.jarjarDependencies
        this.inputJars = configuration.repackageDependencies
        this.rules = configuration.rules
        this.excludes = configuration.excludes
        this.output = project.file(configuration.output)
        this.verbose = configuration.verboseRepackaging
    }

    protected static def setUp(
            Project project,
            RepackageConfiguration configuration,
            Class repackageClass,
            JavaCompile compileTask,
            Task taskDependentOnJarJar,
            String nameSuffix
    ) {
        AbstractRepackage repackageTask = project.task(namespace(TASK_NAME, nameSuffix), type: repackageClass) {
            delegate.configuration = configuration
        }
        repackageTask.init()
        repackageTask.mustRunAfter compileTask
        taskDependentOnJarJar.dependsOn repackageTask

        ModifyDependencies modifyDependenciesTask = ModifyDependencies.setUp(project, configuration, nameSuffix)
        TaskWrapper compileTaskWrapper = [
                getDependencies: { compileTask.classpath },
                setDependencies: { d -> compileTask.classpath = d }
        ] as TaskWrapper
        modifyDependenciesTask.removeOldDependenciesAndAddRepackageJar(compileTaskWrapper)

        repackageTask.dependsOn modifyDependenciesTask

        return [repackageTask, modifyDependenciesTask]
    }

    @TaskAction
    def taskAction() {

        ant.taskdef(
                name: 'jarjar',
                classpath: jarjarDependencies.asPath,
                classname: 'com.tonicsystems.jarjar.JarJarTask'
        )

        ant.jarjar(
                jarfile: output.absolutePath,
                verbose: verbose
        ) {
            inputJars.each { file ->
                zipfileset(src: file, excludes: excludes.join(','))
            }
            rules.each { rule ->
                delegate.rule rule
            }
        }
    }
}
