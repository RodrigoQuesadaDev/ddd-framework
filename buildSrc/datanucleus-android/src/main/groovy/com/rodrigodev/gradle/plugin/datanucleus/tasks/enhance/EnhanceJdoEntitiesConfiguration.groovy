import com.rodrigodev.gradle.plugin.datanucleus.PluginConfiguration

class EnhanceJdoEntitiesConfiguration {

    @Delegate PluginConfiguration pluginConfiguration

    EnhanceJdoEntitiesConfiguration(PluginConfiguration pluginConfiguration) {
        this.pluginConfiguration = pluginConfiguration
    }

    public String getEntitiesDir() {
        return extension.entitiesDir
    }

    public List<String> getEntitiesDirs() {
        def entitiesDirs = extension.entitiesDirs
        if (extension.entitiesDir) entitiesDirs += configuration.entitiesDir
        return entitiesDirs
    }

    public String getAlwaysDetachable() {
        return extension.alwaysDetachable
    }
}