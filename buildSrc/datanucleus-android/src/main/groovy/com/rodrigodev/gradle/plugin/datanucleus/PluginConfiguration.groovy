package com.rodrigodev.gradle.plugin.datanucleus

import com.rodrigodev.gradle.plugin.datanucleus.PluginExtension
import com.rodrigodev.gradle.plugin.datanucleus.tasks.copy_files.CopyPluginFilesConfiguration
import com.rodrigodev.gradle.plugin.datanucleus.tasks.jarjar.JarJarConfiguration
import org.gradle.api.Project
import org.gradle.api.artifacts.Configuration

class PluginConfiguration {

    private static final String DATANUCLEUS = 'datanucleus'
    private static final String EXTENSION_NAME = 'datanucleus'
    static final String OUTPUT_DIR_NAME = 'datanucleus'
    private static final String OUTPUT_FILE_NAME = 'datanucleus.jar'

    private Project project
    final PluginExtension extension
    Configuration datanucleusDependencies

    final CopyPluginFilesConfiguration copyPluginFilesConfiguration
    final EnhanceJdoEntitiesConfiguration enhanceJdoEntitiesConfiguration
    final JarJarConfiguration jarJarConfiguration

    PluginConfiguration(Project project) {
        this.project = project;
        this.extension = project.extensions.create(EXTENSION_NAME, PluginExtension)
        this.datanucleusDependencies = project.configurations.create(DATANUCLEUS)
        this.copyPluginFilesConfiguration = new CopyPluginFilesConfiguration(this)
        this.enhanceJdoEntitiesConfiguration = new EnhanceJdoEntitiesConfiguration(this)
        this.jarJarConfiguration = new JarJarConfiguration(this);
    }

    File getRepackagedFile(){
        File outputDir = new File(project.buildDir, OUTPUT_DIR_NAME)
        return new File(outputDir, OUTPUT_FILE_NAME)
    }
}