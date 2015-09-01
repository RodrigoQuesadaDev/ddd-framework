package com.aticosoft.appointments.mobile.business.infrastructure.persistence.datanucleus.plugin;

import com.rodrigodev.common.file.PathUtils;

/**
 * Created by rodrigo on 14/08/15.
 */
public class PluginDirectory {

    private String path;

    public PluginDirectory(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public String join(String filePath) {
        return PathUtils.join(path, filePath);
    }
}
