package com.rodrigodev.gradle.plugin.datanucleus.tasks.jarjar

import com.rodrigodev.gradle.plugin.datanucleus.PluginConfiguration
import org.gradle.api.Project
import org.gradle.api.artifacts.Configuration

class JarJarConfiguration {

    private static final String JARJAR_PLUGIN_ID = 'com.rodrigodev.jarjar'
    private static final String JDK_CLASSES = 'jdkClasses'
    private static final String OUTPUT_FILE_NAME = 'datanucleus.jar'

    private Project project

    JarJarConfiguration(PluginConfiguration pluginConfiguration) {
        project = pluginConfiguration.project
        project.apply {
            plugin JARJAR_PLUGIN_ID
        }

        project.jarjar {
            rules {
                rule pattern: 'javax.naming.**', result: 'org.datanucleus.@0'
                rule pattern: 'javax.transaction.**', result: 'org.datanucleus.@0'
            }
//            rules = [
//                    'rule javax.naming.** org.datanucleus.@0',
//                    'rule javax.transaction.** org.datanucleus.@0'
//                    /*javax.sql.**
//                    java.lang.instrument.ClassFileTransformer
//                    java.lang.instrument.IllegalClassFormatException
//                    import java.lang.instrument.Instrumentation;
//                    java.awt.Color
//                    some methods of java.sql.Connection
//                    some methods of java.sql.closeOnCompletion
//                    java.awt.image.BufferedImage*/
//            ]
            excludes = [
                    'plugin.xml'
            ]
            output outputFile().absolutePath
        }

        Configuration jdkClasses = project.configurations.create(JDK_CLASSES)
        project.configurations.compile.extendsFrom jdkClasses
        project.configurations.repackage.extendsFrom pluginConfiguration.datanucleusDependencies
        project.configurations.repackage.extendsFrom jdkClasses
    }

    private File outputFile(){
        File outputDir = new File(project.buildDir, PluginConfiguration.OUTPUT_DIR_NAME)
        return new File(outputDir, OUTPUT_FILE_NAME)
    }
}