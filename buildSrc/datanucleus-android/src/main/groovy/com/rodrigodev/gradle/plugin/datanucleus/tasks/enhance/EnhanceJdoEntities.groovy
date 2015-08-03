package com.rodrigodev.gradle.plugin.datanucleus.tasks.enhance

import com.rodrigodev.gradle.plugin.datanucleus.common.VariantData
import com.rodrigodev.gradle.plugin.datanucleus.tasks.DataNucleusTask
import org.gradle.api.GradleException
import org.gradle.api.Project
import org.gradle.api.file.FileCollection
import org.gradle.api.tasks.TaskAction

class EnhanceJdoEntities extends DataNucleusTask {

    static final String TASK_NAME = 'enhanceJdoEntities'

    EnhanceJdoEntitiesConfiguration configuration;
    def variant

    static void setUp(Project project, EnhanceJdoEntitiesConfiguration configuration) {
        project.android.applicationVariants.each { variant ->
            VariantData variantData = new VariantData(variant: variant)

            def enhanceJdoEntities = project.task(namespace(TASK_NAME, variantData.suffix), type: EnhanceJdoEntities) {
                delegate.configuration = configuration
                delegate.variant = variant
            }

            variant.javaCompile.finalizedBy enhanceJdoEntities
        }
    }

    @TaskAction
    def taskAction() {
        try {
            logger.info 'Enhancing DataNucleus classes...'

            String entitiesDir = removeLastPathSeparator(configuration.entitiesDir)

            def jdoFiles = project.fileTree(variant.javaCompile.destinationDir).matching {
                include "$entitiesDir/**/*.class"
            }

            variant.sourceSets.each {
                jdoFiles = jdoFiles.plus(it.resources.sourceFiles.matching {
                    include "$entitiesDir/**/*.jdo"
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
                    pathelement(path: variant.javaCompile.destinationDir.canonicalPath.toURI().toString())
                    pathelement(path: variant.javaCompile.classpath.asPath)
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

    private static String removeLastPathSeparator(String path) {
        if (path.endsWith('/')) {
            path = path.subSequence(0, path.size() - 1)
        }
        return path
    }
}