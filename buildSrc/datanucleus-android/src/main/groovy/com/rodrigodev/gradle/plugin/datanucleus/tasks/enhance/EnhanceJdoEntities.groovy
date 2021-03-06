package com.rodrigodev.gradle.plugin.datanucleus.tasks.enhance

import com.rodrigodev.gradle.plugin.datanucleus.tasks.DataNucleusTask
import org.gradle.api.GradleException
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.file.FileCollection
import org.gradle.api.tasks.TaskAction

class EnhanceJdoEntities extends DataNucleusTask {

    static final String TASK_NAME = 'enhanceJdoEntities'

    EnhanceJdoEntitiesConfiguration configuration
    def variant

    static void setUp(Project project, EnhanceJdoEntitiesConfiguration configuration) {
        project.android.applicationVariants.each {
            [it, it.unitTestVariant].each { variant ->
                if (variant) {
                    def variantData = variant.variantData

                    def enhanceJdoEntities = project.task(namespace(TASK_NAME, variantData.name), type: EnhanceJdoEntities) {
                        delegate.configuration = configuration
                        delegate.variant = variant
                    }

                    Task kotlinAfterJavaTask = project.tasks.findByName(kotlinTaskName(variantData))
                    if (kotlinAfterJavaTask) {
                        kotlinAfterJavaTask.finalizedBy enhanceJdoEntities
                    } else {
                        variant.javaCompile.finalizedBy enhanceJdoEntities
                    }
                }
            }
        }
    }

    static String kotlinTaskName(def variantData) {
        return "compile${variantData.name.capitalize()}KotlinAfterJava"
    }

    @TaskAction
    def taskAction() {
        try {
            logger.info "Enhancing DataNucleus classes for variant '$variant.name'..."

            def entitiesDirs = configuration.entitiesDirs.collect { removeLastPathSeparator(it) }

            def jdoFiles = project.fileTree(variant.javaCompile.destinationDir).matching {
                entitiesDirs.each {
                    include "$it/**/*.class"
                }
            }

            variant.sourceSets.each {
                jdoFiles = jdoFiles.plus(it.resources.sourceFiles.matching {
                    entitiesDirs.each {
                        include "$it/**/*.jdo"
                    }
                })
            }

            ant.taskdef(
                    name: 'enhance',
                    classpath: configuration.datanucleusDependencies.asPath,
                    classname: 'org.datanucleus.enhancer.EnhancerTask'
            )

            ant.enhance(
                    alwaysDetachable: configuration.alwaysDetachable,
                    failonerror: true,
                    verbose: true
            ) {
                classpath {
                    pathelement(path: project.files(variant.javaCompile.destinationDir).asPath)
                    pathelement(path: (variant.javaCompile.classpath - project.files(configuration.repackagedFile)).asPath)
                    pathelement(path: configuration.datanucleusDependencies.asPath)
                }
                jdoFiles.addToAntBuilder(ant, 'fileset', FileCollection.AntType.FileSet)
            }
        }
        catch (Exception e) {
            throw new GradleException('An error occurred enhancing DataNucleus classes.', e)
        }
        finally {
            logger.info 'Finished enhancing DataNucleus classes.'
        }
    }

    protected static String removeLastPathSeparator(String path) {
        if (path.endsWith('/')) {
            path = path.subSequence(0, path.size() - 1)
        }
        return path
    }
}