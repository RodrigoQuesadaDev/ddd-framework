package com.rodrigodev.gradle.plugin.jarjar.tasks.modify_dependencies

interface TaskWrapper<D> {

    D getDependencies()
    void setDependencies(D dependencies)
}