package com.rodrigodev.gradle.plugin.jarjar.tasks.repackage

import com.rodrigodev.gradle.plugin.jarjar.common.VariantData
import com.rodrigodev.gradle.plugin.jarjar.tasks.modify_dependencies.TaskWrapper
import org.gradle.api.Project
import org.gradle.api.Task

class AndroidRepackage extends AbstractRepackage {


    private static final String PRE_DEX_TASK_BASE_NAME = 'preDex'
    private static final String PACKAGE_TASK_BASE_NAME = 'package'

    static void setUp(Project project, RepackageConfiguration configuration) {
        variants(project).each { variant ->
            VariantData variantData = new VariantData(variant: variant)

            def (repackageTask, modifyDependenciesTask) = AbstractRepackage.setUp(
                    project,
                    configuration,
                    AndroidRepackage,
                    variant.javaCompile,
                    variant.dex,
                    variantData.suffix
            )

            TaskWrapper dexTaskWrapper
            Task preDexTask = project.tasks.findByName(preDexTaskName(variantData))
            if (preDexTask) {
                dexTaskWrapper = [
                        getDependencies: { preDexTask.inputFiles },
                        setDependencies: { d -> preDexTask.inputFiles = d }
                ] as TaskWrapper
                preDexTask.mustRunAfter repackageTask
            }
            else {
                dexTaskWrapper = [
                        getDependencies: { variant.dex.libraries },
                        setDependencies: { d -> variant.dex.libraries = d }
                ] as TaskWrapper
            }

            Task packageTask = project.tasks.findByName(packageTaskName(variantData))
            TaskWrapper packageTaskWrapper = [
                    getDependencies: { packageTask.packagedJars },
                    setDependencies: { d -> packageTask.packagedJars = d }
            ] as TaskWrapper

            modifyDependenciesTask.removeOldDependenciesAndAddRepackageJar(dexTaskWrapper, packageTaskWrapper)
        }
    }

    private static String preDexTaskName(VariantData variantData) {
        return PRE_DEX_TASK_BASE_NAME + variantData.suffix
    }

    private static String packageTaskName(VariantData variantData) {
        return PACKAGE_TASK_BASE_NAME + variantData.suffix
    }

    private static def variants(Project project) {
        String variantsString;
        if (project.plugins.findPlugin("com.android.application")) {
            variantsString = "applicationVariants"
        }
        else {
            variantsString = "libraryVariants"
        }
        return project.android[variantsString]
    }
}
