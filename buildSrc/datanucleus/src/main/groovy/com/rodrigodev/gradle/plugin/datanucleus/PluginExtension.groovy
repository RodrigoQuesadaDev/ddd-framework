package com.rodrigodev.gradle.plugin.datanucleus

class PluginExtension {

    String entitiesDir
    List<String> entitiesDirs = []
    boolean alwaysDetachable = false
    String excludedJar
    List<String> excludedJars = []
}