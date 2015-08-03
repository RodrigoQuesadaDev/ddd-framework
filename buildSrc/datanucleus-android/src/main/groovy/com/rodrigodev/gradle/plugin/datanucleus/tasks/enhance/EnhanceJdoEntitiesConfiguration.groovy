import com.rodrigodev.gradle.plugin.datanucleus.PluginConfiguration

class EnhanceJdoEntitiesConfiguration {

    @Delegate PluginConfiguration pluginConfiguration

    EnhanceJdoEntitiesConfiguration(PluginConfiguration pluginConfiguration) {
        this.pluginConfiguration = pluginConfiguration
    }

    public String getEntitiesDir() {
        return extension.entitiesDir
    }

    public String getAlwaysDetachable() {
        return extension.alwaysDetachable
    }
}