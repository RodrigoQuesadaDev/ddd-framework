package com.rodrigodev.gradle.plugin.datanucleus.tasks.copy_files

import com.rodrigodev.gradle.plugin.datanucleus.tasks.DataNucleusTask
import org.gradle.api.Project
import org.gradle.api.file.FileCollection
import org.gradle.api.tasks.InputFiles
import org.gradle.api.tasks.OutputDirectory
import org.gradle.api.tasks.TaskAction

class CopyPluginFiles extends DataNucleusTask {

    private static final String TASK_NAME = 'copyPluginFiles'

    static final String PLUGINS_DIR_PATH = 'src/main/assets/plugins'
    static final String PLUGIN_FILE_PATH = 'plugin.xml'
    static final String MANIFEST_FILE_PATH = 'META-INF/MANIFEST.MF'

    public static final String PLUGIN_ID_ATTR = 'id'

    CopyPluginFilesConfiguration configuration;

    @InputFiles FileCollection jarFiles
    @OutputDirectory File pluginsDir

    public void init() {
        this.jarFiles = configuration.datanucleusDependencies
        this.pluginsDir = new File(PLUGINS_DIR_PATH)
        pluginsDir.mkdirs()
    }

    public static void setUp(Project project, CopyPluginFilesConfiguration configuration) {
        CopyPluginFiles task = project.task(namespace(TASK_NAME), type: CopyPluginFiles) {
            delegate.configuration = configuration
        }
        task.init()
        project.tasks.preBuild.dependsOn task
    }

    @TaskAction
    def taskAction() {
        jarFiles.each { dep ->
            def pluginFile = project.zipTree(dep).find { it.name == PLUGIN_FILE_PATH }
            if (pluginFile) {

                def pluginId = (new XmlParser()).parse(pluginFile).attribute(PLUGIN_ID_ATTR)
                project.copy {
                    from(project.zipTree(dep)) {
                        include PLUGIN_FILE_PATH
                        include MANIFEST_FILE_PATH
                    }
                    into(new File(pluginsDir, pluginId))
                }
            }
        }
    }
}